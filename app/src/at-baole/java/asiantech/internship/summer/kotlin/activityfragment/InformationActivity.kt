package asiantech.internship.summer.kotlin.activityfragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-baole`.kotlin_activity_information.*

class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity_information)

        tvEmail.text = intent.getStringExtra(getString(R.string.email))
        tvPassword.text = intent.getStringExtra(getString(R.string.password))
        backActivity()
    }

    private fun backActivity(){
        btnBack.setOnClickListener { onBackPressed() }
    }
}
