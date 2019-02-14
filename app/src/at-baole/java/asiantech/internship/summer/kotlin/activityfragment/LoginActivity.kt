package asiantech.internship.summer.kotlin.activityfragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-baole`.kotlin_activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity_login)
        initFragment()
        backFragment()
    }

    private fun initFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.llLogin, LoginFragment())
        transaction.commit()
    }

    fun onButton() {
        btnBack.visibility = View.VISIBLE
    }

    fun setTextToolbar(title: Int) {
        tvToolBar.setText(title)
    }

    private fun backFragment() {
        btnBack.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentById(R.id.llLogin)
        if (fragment is LoginFragment) {
            setTextToolbar(R.string.logIn)
            btnBack.visibility = View.VISIBLE
        } else {
            setTextToolbar(R.string.backToScreen)
            btnBack.visibility = View.VISIBLE
        }
    }
}
