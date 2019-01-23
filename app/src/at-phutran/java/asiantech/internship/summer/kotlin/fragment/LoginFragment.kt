package asiantech.internship.summer.kotlin.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.fragment_login.*
import kotlinx.android.synthetic.`at-phutran`.fragment_login.view.*
import java.util.regex.Pattern

@Suppress("NAME_SHADOWING")
class LoginFragment : Fragment(), View.OnClickListener {
    val emailPattern = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(]?)$"
    val passPattern = "^[a-zA-Z0-9]{7,}$"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        handleEvent(view)
        return view
    }

    @SuppressLint("CommitTransaction")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                if (isValidCheckLogin(edtInputEmail.text.toString(), emailPattern) && isValidCheckLogin((edtInputPass.text.toString()), passPattern)) {
                    gotoWelcomeActivity()
                } else {
                    Toast.makeText(activity, R.string.checkInput, Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btnSignUp -> {
                val signUpFragment = SignUpFragment()
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.setCustomAnimations(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left, R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right)
                fragmentTransaction?.replace(R.id.frContent, signUpFragment)
                if (activity is LoginActivity) {
                    (activity as LoginActivity).setTextForToolBar(getString(R.string.signUp))
                    (activity as LoginActivity).onButton()
                }
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }
        }
    }

    private fun gotoWelcomeActivity() {
        val intent = Intent(activity, WelcomeActivity::class.java)
        intent.putExtra(getString(R.string.valueEmail), edtInputEmail.text.toString())
        intent.putExtra(getString(R.string.valuePassword), edtInputPass.text.toString())
        startActivity(intent)
    }

    private fun handleEvent(view: View) {
        view.btnLogin.setOnClickListener(this)
        view.btnSignUp.setOnClickListener(this)
    }

    fun isValidCheckLogin(string: String, pattern: String): Boolean {
        val pattern = Pattern.compile(pattern)
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }
}
