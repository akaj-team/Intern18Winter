package asiantech.internship.summer.finalproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-baole`.final_login_activity.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_login_activity)

        btnLoginFinal.setOnClickListener(this)
    }

    private fun getLoginUsername() {
        val usernameKey = "username"
        val username = edtUsernameFinal.text.toString()
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra(usernameKey, username)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        btnLoginFinal.setOnClickListener { getLoginUsername() }
    }
}
