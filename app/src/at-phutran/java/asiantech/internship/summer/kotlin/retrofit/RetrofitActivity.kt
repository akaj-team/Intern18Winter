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
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.activity_retrofit.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class RetrofitActivity : AppCompatActivity() {
    private val accessToken = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476"
    private val urlUpload = "https://upload.gyazo.com/api/upload"
    private val onClickGallery = 0
    private val onClickCamera = 1
    private val gallery = 111
    private val camera = 222
    private val requestAskPermissionCamera = 333
    private val requestAskPermissionGallery = 444
    private lateinit var imageItems: ArrayList<Image>
    private var adapter: RetrofitAdapter? = null
    private var soService: SOSevice? = null
    private lateinit var recyclerView: RecyclerView
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        val retrofitClient = RetrofitClient()
        soService = retrofitClient.getClient()?.create(SOSevice::class.java)
        initRecyclerView()
        loadData()
        btnInsertImage.setOnClickListener {
            eventHandle()
        }
    }

    private fun eventHandle() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle(getString(R.string.action))
        val pictureDialogItems = arrayOf(getString(R.string.gallery), getString(R.string.camera))
        pictureDialog.setItems(pictureDialogItems
        ) { _, which ->
            when (which) {
                onClickGallery -> if (checkPermissionForGallery()) {
                    chooseGallery()
                }
                onClickCamera -> if (checkPermissionForCamera()) {
                    chooseCamera()
                }
            }
        }
        pictureDialog.show()
    }

    private fun chooseCamera() {
        if (checkPermissionForCamera()) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, camera)
        }
    }

    private fun chooseGallery() {
        if (checkPermissionForGallery()) {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, gallery)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val isShowRationaleWrite: Boolean
        when (requestCode) {
            requestAskPermissionCamera -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    chooseCamera()
                } else {
                    val isShowRationaleCamera = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    isShowRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    if (!isShowRationaleWrite || !isShowRationaleCamera) {
                        showSettingsAlert(getString(R.string.noteCamera))
                    }
                }
            }
            requestAskPermissionGallery -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseGallery()
                } else {
                    isShowRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    if (!isShowRationaleWrite) {
                        showSettingsAlert(getString(R.string.noteGallery))
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun showSettingsAlert(message: String) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.optionChoose))
        alertDialog.setMessage(getString(R.string.noteAccess) + " " + message)
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel)
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.setting)
        ) { dialog, _ ->
            dialog.dismiss()
            startInstalledAppDetailsActivity(this@RetrofitActivity)
        }
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == gallery) {
                openGallery(data)
            } else {
                openCamera(data)
            }
        }
    }

    private fun openGallery(intent: Intent) {
        val selectedImageURI = intent.data
        if (selectedImageURI != null) {
            uploadImage(selectedImageURI)
        }
    }

    private fun openCamera(data: Intent) {
        val bundle = data.extras
        var imageBitmap: Bitmap? = null
        if (bundle != null) {
            imageBitmap = bundle.get(getString(R.string.data)) as Bitmap
        }
        if (imageBitmap != null) {
            uploadImage(getImageUri(applicationContext, imageBitmap))
        }
    }

    private fun startInstalledAppDetailsActivity(context: Activity?) {
        if (context == null) {
            return
        }
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + context.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        context.startActivity(intent)
    }

    private fun checkPermissionForCamera(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), requestAskPermissionCamera)
            return false
        }
        return true
    }

    private fun checkPermissionForGallery(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestAskPermissionGallery)
            return false
        }
        return true
    }

    private fun loadData() {
        showProgressbarDialog()
        soService?.getImages(accessToken, 1, 20)?.enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.isSuccessful) {
                    assert(response.body() != null)
                    for (image in response.body()!!) {
                        if (!image.image_id.isEmpty()) {
                            imageItems.add(image)
                        }
                    }
                    adapter?.notifyDataSetChanged()
                    progressDialog?.dismiss()
                }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(applicationContext, R.string.error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        imageItems = ArrayList()
        val layoutManager = GridLayoutManager(this, 2)
        recyclerViewContent.layoutManager = layoutManager
        adapter = RetrofitAdapter(imageItems)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewContent).apply {
            adapter = adapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        }
    }

    private fun showProgressbarDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog?.setMessage(getString(R.string.loading))
        progressDialog?.show()
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, getString(R.string.titles), null)
        return Uri.parse(path)
    }

    private fun uploadImage(imageUri: Uri) {
        showProgressbarDialog()
        val realPathUtil = RealPathUtil()
        val file = File(Objects.requireNonNull<String>(realPathUtil.getRealPathFromUriAPI11to18(applicationContext, imageUri)))
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val image = MultipartBody.Part.createFormData("imagedata", file.name, requestBody)
        val token = RequestBody.create(MediaType.parse("text/plain"), accessToken)
        soService?.uploadImage(urlUpload, token, image)?.enqueue(object : Callback<Image> {
            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                if (response.isSuccessful) {
                    imageItems.add(0, response.body()!!)
                    adapter?.notifyDataSetChanged()
                    Toast.makeText(applicationContext, R.string.successful, Toast.LENGTH_SHORT).show()
                    progressDialog?.dismiss()
                }
            }

            override fun onFailure(call: Call<Image>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(applicationContext, R.string.error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
