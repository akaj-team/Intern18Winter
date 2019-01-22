@file:Suppress("DEPRECATION")

package asiantech.internship.summer.kotlin.retrofit

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RetrofitActivity : AppCompatActivity() {
    private val accessToken = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476"
    private lateinit var mImageItems: ArrayList<Image>
    private var mAdapter: RetrofitAdapter? = null
    private var mService: SOSevice? = null
    private lateinit var recyclerView: RecyclerView
    private var mProgressDialog: ProgressDialog? = null
    private val onClickGallery = 0
    private val onClickCamera = 1
    private val gallery = 111
    private val camera = 222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        val retrofitClient = RetrofitClient()
        mService = retrofitClient.getClient()?.create(SOSevice::class.java)
        initRecyclerView()
        loadData()
        btnInsertImage.setOnClickListener{
            eventHandle()
        }
    }

    private fun eventHandle() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle(getString(R.string.action))
        val pictureDialogItems = arrayOf(getString(R.string.gallery), getString(R.string.camera))
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                /*gallery -> if (checkPermissionForGallery()) {
                    chooseGallery()
                }
                camera -> if (checkPermissionForCamera()) {
                    chooseCamera()
                }*/
            }
        }
        pictureDialog.show()
    }

    /*private fun checkPermissionForGallery(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_ASK_PERMISSIONS_GALLERY)
            return false
        }
        return true
    }*/

    private fun loadData() {
        showProgressbarDialog()
        mService?.getImages(accessToken, 1, 20)?.enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.isSuccessful) {
                    assert(response.body() != null)
                    for (image in response.body()!!) {
                        if (!image.image_id.isEmpty()) {
                            mImageItems.add(image)
                        }
                    }
                    mAdapter?.notifyDataSetChanged()
                    mProgressDialog?.dismiss()
                    Log.i("aaaa", "success")
                }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                mProgressDialog?.dismiss()
                Toast.makeText(applicationContext, R.string.error, Toast.LENGTH_SHORT).show()
                Log.i("aaaa", "error")
            }
        })
    }

    private fun initRecyclerView() {
        mImageItems = ArrayList()
        val layoutManager = GridLayoutManager(this, 2)
        recyclerViewContent.layoutManager = layoutManager
        mAdapter = RetrofitAdapter(mImageItems)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewContent).apply {
            adapter = mAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        }
    }

    private fun showProgressbarDialog() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog?.setMessage(getString(R.string.loading))
        mProgressDialog?.show()
    }
}
