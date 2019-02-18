@file:Suppress("DEPRECATION")

package asiantech.internship.summer.kotlin.retrofit

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.Image
import asiantech.internship.summer.utils.RealPathUtil
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.`at-dinhtruong`.activity_rest_api.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class RetrofitActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 200
        const val REQUEST_SELECT_PICTURE = 201
        const val REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 123
        const val REQUEST_CODE_ASK_PERMISSIONS_GALLERY = 124
        const val BASE_URL = "https://api.gyazo.com/api/"
        const val UPLOAD_URL = "https://upload.gyazo.com/api/upload"
        const val ACCESS_TOKEN = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476"
    }

    private val page = 1
    private val perPage = 20
    private var service: SOService? = null
    lateinit var recyclerView: RecyclerView
    lateinit var listImage: ArrayList<Image>
    lateinit var imageAdapter: ImageAdapter
    private lateinit var viewManager: GridLayoutManager
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_api)
        setUpApi()
        initRecyclerView()
        addListener()
    }

    private fun initRecyclerView() {
        listImage = ArrayList()
        viewManager = GridLayoutManager(this, 2)
        imageAdapter = ImageAdapter(listImage)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewItem).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = imageAdapter
            addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.HORIZONTAL))
            addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        }
        loadImages()
    }

    private fun loadImages() {
        onProgressbarDialog()
        service?.getImages(ACCESS_TOKEN, page, perPage)?.enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>?) {
                for (objImage in response?.body() as List) {
                    if (!objImage.image_id.isEmpty()) {
                        listImage.add(objImage)
                    }
                }
                imageAdapter.notifyDataSetChanged()
                mProgressDialog?.dismiss()
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                mProgressDialog?.dismiss()
            }
        })
    }

    private fun addListener() {
        btnCamera.setOnClickListener(this)
        btnGallery.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnCamera -> {
                if (!checkAndRequestCameraPermission()) {
                    Toast.makeText(this, R.string.accept, Toast.LENGTH_SHORT).show()
                } else {
                    openCamera()
                }
            }
            R.id.btnGallery -> {
                if (!checkAndRequestGalleryPermission()) {
                    Toast.makeText(this, R.string.accept, Toast.LENGTH_SHORT).show()
                } else {
                    openGallery()
                }
            }
            else -> {
                Toast.makeText(this, R.string.accept, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCamera() {
        if (checkAndRequestCameraPermission()) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun openGallery() {
        if (checkAndRequestGalleryPermission()) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, getString(R.string.selectPicture)), REQUEST_SELECT_PICTURE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_PICTURE -> {
                    data?.let { onSelectFromGalleryResult(it) }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    data?.let { onCaptureImageResult(it) }
                }
                else -> {
                    Toast.makeText(applicationContext, getString(R.string.nothingToDo), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        val selectedImageURI = data.data
        uploadImage(selectedImageURI)
    }

    private fun onCaptureImageResult(data: Intent) {
        val getExtrasImage = data.extras
        val imageBitmap: Bitmap?
        imageBitmap = getExtrasImage.get(getString(R.string.data)) as Bitmap
        uploadImage(getImageUri(applicationContext, imageBitmap))
    }

    private fun uploadImage(imageUri: Uri) {
        onProgressbarDialog()
        val file = File(Objects.requireNonNull(RealPathUtil.getRealPath(applicationContext, imageUri)))
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val image = MultipartBody.Part.createFormData("imagedata", file.name, requestBody)
        val token = RequestBody.create(MediaType.parse("text/plain"), ACCESS_TOKEN)
        service?.uploadImage(UPLOAD_URL, token, image)?.enqueue(object : Callback<Image> {
            override fun onFailure(call: Call<Image>, t: Throwable) {
                mProgressDialog?.dismiss()
            }

            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                if (response.isSuccessful) {
                    response.body()?.let { listImage.add(0, it) }
                    imageAdapter.notifyItemInserted(0)
                }
                mProgressDialog?.dismiss()
            }

        })
    }

    private fun getImageUri(context: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, inImage, getString(R.string.title), null)
        return Uri.parse(path)
    }

    private fun checkAndRequestCameraPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), REQUEST_CODE_ASK_PERMISSIONS_CAMERA)
            return false
        }
        return true
    }

    private fun checkAndRequestGalleryPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_ASK_PERMISSIONS_GALLERY)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    var showRationale = false
                    var showRationaleWrite = false
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                        showRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    if (!showRationale && !showRationaleWrite) {
                        onPermissionDialog()
                    }
                }
            }
            REQUEST_CODE_ASK_PERMISSIONS_GALLERY -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    var showRationaleWrite = false
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        showRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    if (!showRationaleWrite) {
                        onPermissionDialog()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun setUpApi() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val gson = GsonBuilder().setLenient().create()
        val getImagesRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
        service = getImagesRetrofit.create<SOService>(SOService::class.java)
    }

    private fun onPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.acceptPermission))
        builder.setMessage(R.string.youHaveDeniedPermission)
        builder.setCancelable(false)
        builder.setNegativeButton(R.string.cancle) { _, _ -> Toast.makeText(this, getString(R.string.denied), Toast.LENGTH_SHORT).show() }
        builder.setPositiveButton(getString(R.string.setting)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", application.packageName, null)
            intent.data = uri
            applicationContext.startActivity(intent)
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun onProgressbarDialog() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog?.setMessage(getString(R.string.loading))
        mProgressDialog?.show()
    }
}
