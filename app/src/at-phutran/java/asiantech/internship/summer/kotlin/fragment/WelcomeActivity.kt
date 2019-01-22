package asiantech.internship.summer.kotlin.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.activity_welcome.*

@SuppressLint("Registered")
class WelcomeActivity: AppCompatActivity() {
    private var mValueEmail = ""
    private var mValuePassword = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        mValueEmail = intent.getStringExtra(getString(R.string.valueEmail))
        mValuePassword = intent.getStringExtra(getString(R.string.valuePassword))
        tvEmail.text = mValueEmail
        tvPassword.text = mValuePassword
    }
}
