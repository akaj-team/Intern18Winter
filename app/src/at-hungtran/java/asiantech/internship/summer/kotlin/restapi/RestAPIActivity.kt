@file:Suppress("DEPRECATION")

package asiantech.internship.summer.kotlin.restapi

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.ImageItem
import asiantech.internship.summer.restapi.APIImages
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.`at-hungtran`.activity_rest_api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RestAPIActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var apiImages : asiantech.internship.summer.kotlin.restapi.APIImages
    private var imageAdapter: ImagesAdapter? = null
    private val images = ArrayList<ImageItem>()
    private var progressDialog: ProgressDialog? = null
    private val currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_api)
        initView()
        setUpApi()
        initRecyclerView()
    }

    private fun initView() {
        btnGetImage.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        recycleViewRestful.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 2)
        recycleViewRestful.layoutManager = gridLayoutManager
        imageAdapter = ImagesAdapter(images)
        recycleViewRestful.adapter = imageAdapter
    }

    private fun setUpApi() {
        val gson = GsonBuilder().setLenient().create()
        val getImagesRetrofit = Retrofit.Builder()
                .baseUrl(asiantech.internship.summer.kotlin.restapi.APIImages.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        apiImages = getImagesRetrofit.create(asiantech.internship.summer.kotlin.restapi.APIImages::class.java)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnGetImage -> {
                getListImages()
            }
        }
    }

    private fun getListImages() {
        addProgressbarDialog()
        apiImages.getImages(APIImages.TOKEN, currentPage)
                .enqueue(object : Callback<List<ImageItem>>{
                    override fun onResponse(call: Call<List<ImageItem>>,
                                            response: Response<List<ImageItem>>) {
                        if (response.isSuccessful) {
                            val addImages = response.body()
                            if (addImages != null) {
                                images.addAll(addImages)
                                imageAdapter?.notifyDataSetChanged()
                                progressDialog?.dismiss()
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<ImageItem>>, t: Throwable) {
                        progressDialog?.dismiss()
                        Toast.makeText(this@RestAPIActivity, "Get image OnFailure", Toast.LENGTH_LONG).show()
                    }
                })
    }

    private fun addProgressbarDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setMessage("Please wait, we are doing your image files")
        progressDialog!!.show()
    }
}



