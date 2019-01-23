package asiantech.internship.summer.kotlin.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.activity_login.*

@SuppressLint("Registered")
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frContent, LoginFragment())
        fragmentTransaction.commit()
        unShowButtonBack()
        backFragment()
    }

    fun onButton() {
        showButtonBack()
    }

    fun setTextForToolBar(title: String) {
        tvToolBar.text = title
    }

    private fun unShowButtonBack() {
        btnBack.visibility = View.GONE
    }

    private fun showButtonBack() {
        btnBack.visibility = View.VISIBLE
    }

    private fun backFragment() {
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentById(R.id.frContent)
        if (fragment is LoginFragment) {
            tvToolBar.text = getString(R.string.login)
            unShowButtonBack()
        } else {
            tvToolBar.text = getString(R.string.signUp)
            showButtonBack()
        }
    }
}
