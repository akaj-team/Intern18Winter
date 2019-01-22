package asiantech.internship.summer.kotlin.retrofit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.Image
import kotlinx.android.synthetic.`at-dinhtruong`.activity_rest_api.*
import android.provider.Settings
import android.support.v7.app.AlertDialog
import asiantech.internship.summer.restapi.SOService


class RetrofitActivity : AppCompatActivity(), View.OnClickListener {
    private val REQUEST_IMAGE_CAPTURE = 200
    private val REQUEST_SELECT_PICTURE = 201
    private val REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 123
    private val REQUEST_CODE_ASK_PERMISSIONS_GALLERY = 124
    private val CHECK_DO_NOT_ASK_AGAIN = "dontAskAgain"
    private val CHECK_CAMERA = "checkCamera"
    private val CHECK_GALLERY = "checkGallery"
    private val mPage = 1
    private val mPerPage = 20
    private val ACCESS_TOKEN = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476"
    private val BASE_URL = "https://api.gyazo.com/api/"
    private val UPLOAD_URL = "https://upload.gyazo.com/api/upload"
    private var mService: SOService? = null
    lateinit var recyclerView: RecyclerView
    lateinit var mListImage: ArrayList<Image>
    lateinit var imageAdapter: ImageAdapter
    lateinit var viewManager: GridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_api)
        initRecyclerView()
        addListener()
    }

    private fun initRecyclerView() {
        mListImage = ArrayList()
        viewManager = GridLayoutManager(this, 2)
        imageAdapter = ImageAdapter(mListImage)
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
        val sharedPreferences = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, Context.MODE_PRIVATE)
        val isCheckGallery: Boolean
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    val isCheckCamera = sharedPreferences.getBoolean(CHECK_CAMERA, false)
                    isCheckGallery = sharedPreferences.getBoolean(CHECK_GALLERY, false)
                    var showRationale = false
                    var showRationaleWrite = false
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                        showRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    val editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, Context.MODE_PRIVATE).edit()
                    if (!showRationale && !showRationaleWrite) {
                        if (isCheckCamera && isCheckGallery) {
                            onPermissionDialog()
                        }
                        editor.putBoolean(CHECK_CAMERA, true)
                        editor.putBoolean(CHECK_GALLERY, true)
                    } else if (!showRationale) {
                        editor.putBoolean(CHECK_CAMERA, true)
                    } else if (!showRationaleWrite) {
                        editor.putBoolean(CHECK_GALLERY, true)
                    }
                    editor.apply()
                }
            }
            REQUEST_CODE_ASK_PERMISSIONS_GALLERY -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    isCheckGallery = sharedPreferences.getBoolean(CHECK_GALLERY, false)
                    var showRationaleWrite = false
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        showRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    if (!showRationaleWrite) {
                        if (isCheckGallery) {
                            onPermissionDialog()
                        }
                        val editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, Context.MODE_PRIVATE).edit()
                        editor.putBoolean(CHECK_GALLERY, true)
                        editor.apply()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun onPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.accept)
        builder.setMessage(R.string.youHaveDeniedPermission)
        builder.setCancelable(false)
        builder.setNegativeButton(R.string.cancle, { dialogInterface, i -> Toast.makeText(this, getString(R.string.denied), Toast.LENGTH_SHORT).show() })
        builder.setPositiveButton(getString(R.string.setting)) { dialogInterface, i ->
            val editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, Context.MODE_PRIVATE).edit()
            editor.putBoolean(CHECK_CAMERA, false)
            editor.putBoolean(CHECK_GALLERY, false)
            editor.apply()
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

}
