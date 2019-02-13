package asiantech.internship.summer.menu

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.eventlistener.EventListenerSignUp
import asiantech.internship.summer.kotlin.fragment.LoginActivity
import asiantech.internship.summer.kotlin.retrofit.RetrofitActivity
import kotlinx.android.synthetic.`at-phutran`.activity_list_kotlin.*

class ListActivityKotlin : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnExercise00 -> {
                val intent = Intent(this@ListActivityKotlin, EventListenerSignUp::class.java)
                startActivity(intent)
            }
            R.id.btnExercise11 -> {
                val intent = Intent(this@ListActivityKotlin, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise22 -> {
                val intent = Intent(this@ListActivityKotlin, RetrofitActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kotlin)
        btnExercise00.setOnClickListener(this)
        btnExercise11.setOnClickListener(this)
        btnExercise22.setOnClickListener(this)
    }
}
