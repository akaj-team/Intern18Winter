package asiantech.internship.summer.kotlin.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import asiantech.internship.summer.R

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frContent, LoginFragment())
        fragmentTransaction.commit()
    }
}