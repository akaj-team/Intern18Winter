@file:Suppress("DEPRECATION")

package asiantech.internship.summer.kotlin.retrofit

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class RetrofitActivity: AppCompatActivity() {
    private val accessToken = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476"
    private var mImageItems: ArrayList<Image> = ArrayList()
    private var mAdapter: RetrofitAdapter? = null
    private var mService: SOSevice? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_retrofit)
        val retrofitClient = RetrofitClient()
        mImageItems = ArrayList()
        mService = retrofitClient.getClient()!!.create(SOSevice::class.java)
        initRecyclerView()
        loadData()
    }

    private fun loadData() {
        mService!!.getImages(accessToken, 1, 20).enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.isSuccessful) {
                    assert(response.body() != null)
                    for (image in response.body()!!) {
                        if (!image.image_id.isEmpty()) {
                            mImageItems.add(image)
                        }
                    }
                    mAdapter!!.notifyDataSetChanged()
                    Log.i("aaaa", "success")
                }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                Toast.makeText(this@RetrofitActivity, R.string.error, Toast.LENGTH_SHORT).show()
                Log.i("aaaa", "error")
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(this, 2)
        recyclerViewContent.layoutManager = layoutManager
        mAdapter = RetrofitAdapter(mImageItems)
        recyclerViewContent.adapter = mAdapter
        recyclerViewContent.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerViewContent.addItemDecoration(itemDecoration)
    }
}
