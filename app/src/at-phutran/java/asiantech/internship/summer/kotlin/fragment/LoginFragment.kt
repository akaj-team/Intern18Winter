package asiantech.internship.summer.kotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.fragment_login.*
import kotlinx.android.synthetic.`at-phutran`.fragment_login.view.*
import java.util.regex.Pattern

@Suppress("NAME_SHADOWING")
class LoginFragment: Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnLogin -> {
               /* if (isValidCheckLogin(edtInputEmail.text.toString(), mEmailPattern) && isValidCheckLogin((edtInputPass.text.toString()), mPassPattern)){
                    gotoWelcomeActivity()
                } else {
                    gotoWelcomeActivity()
                }*/
                gotoWelcomeActivity()
            }
            R.id.btnSignUp -> Log.i("adf", "b")
        }
    }

    private fun gotoWelcomeActivity() {
//        val intent = Intent(this:activity, WelcomeActivity::class.java)
        /*intent.putExtra(getString(R.string.valueEmail), edtInputEmail.text.toString())
        intent.putExtra(getString(R.string.valuePassword), edtInputPass.text.toString())*/
//        startActivity(intent)
    }

    private val mEmailPattern = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(]?)$"
    private val mPassPattern = "^[a-zA-Z0-9]{7,}$"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        handleEvent(view)
        return view
    }

    private fun handleEvent(view:View) {
        view.btnLogin.setOnClickListener(this)
        view.btnSignUp.setOnClickListener(this)
    }

    private fun isValidCheckLogin(string: String, pattern: String): Boolean {
        val pattern = Pattern.compile(pattern)
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }
}