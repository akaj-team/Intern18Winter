package asiantech.internship.summer.finalproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import asiantech.internship.summer.R

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_activity_welcome)

        initWelcomeFragment()
    }

    private fun initWelcomeFragment() {
        val usernameKey = "username"
        val bundle = Bundle()
        val fragmentObject = WelcomeFragment()
        val string: String = intent.getStringExtra(usernameKey).toString()
        bundle.putString(usernameKey, string)
        fragmentObject.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.llWelcome, WelcomeFragment()).commit()
    }
}
