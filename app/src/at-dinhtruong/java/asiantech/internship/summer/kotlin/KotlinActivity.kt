package asiantech.internship.summer.kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.eventandlistener.EventAndListenerActivity
import asiantech.internship.summer.kotlin.recyclerview.RecyclerViewActivity
import kotlinx.android.synthetic.`at-dinhtruong`.activity_kotlin.*

class KotlinActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        initView()
    }

    private fun initView() {
        btnEventAndListenerKotlin.setOnClickListener(this)
        btnRecyclerViewKotlin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnEventAndListenerKotlin -> {
                val intent = Intent(this,EventAndListenerActivity::class.java)
                startActivity(intent)
            }
            R.id.btnRecyclerViewKotlin -> {
                val intent = Intent(this, RecyclerViewActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
