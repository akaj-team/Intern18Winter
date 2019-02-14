package asiantech.internship.summer.kotlin.activityfragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-baole`.kotlin_fragment_login.*
import kotlinx.android.synthetic.`at-baole`.kotlin_fragment_login.view.*

class LoginFragment : Fragment() {
    private val MIN_LENGTH: Int = 6
    private val EMAIL_KEY: String = "Email"
    private val PASSWORD_KEY: String = "Password"
    private val PASSWORD_PATTERN: String = "^[A-Za-z0-9]{6,}$"
    private val EMAIL_PATTERN: String = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val loginView: View = inflater.inflate(R.layout.kotlin_fragment_login, container, false)
        onClickLogin(loginView)
        onClickSignUp(loginView)
        return loginView
    }

    private fun onClickLogin(view: View) {
        view.btnLogIn.setOnClickListener { loginValiation() }
    }

    private fun onClickSignUp(view: View) {
        view.btnSignUp.setOnClickListener { initSignUpFragment() }
    }

    private fun initSignUpFragment() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.llLogin, SignUpFragment())
        fragmentTransaction?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_in_right, R.anim.slide_out_right)
        if (activity is LoginActivity) {
            (activity as LoginActivity).setTextToolbar(R.string.capitalSignUp)
            (activity as LoginActivity).onButton()
        }
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun sendIntent() {
        val intent = Intent(activity, InformationActivity::class.java)
        val email: String = edtEmail.text.toString()
        val password: String = edtPassword.text.toString()
        intent.putExtra(EMAIL_KEY, email)
        intent.putExtra(PASSWORD_KEY, password)
        startActivity(intent)
    }

    private fun loginValiation() {
        if (edtPassword.text.length > MIN_LENGTH
                && validateEmail()
                && validatePassword()) {
            sendIntent()
            Toast.makeText(activity, R.string.logInSuccessfully, Toast.LENGTH_LONG).show()
        } else if (!validateEmail()) {
            Toast.makeText(activity, R.string.invalidEmail, Toast.LENGTH_LONG).show()
        } else if (edtPassword.text.length <= MIN_LENGTH) {
            Toast.makeText(activity, R.string.password7Letters, Toast.LENGTH_LONG).show()
        } else if (!validatePassword()) {
            Toast.makeText(activity, R.string.passwordOnlyCharAndNum, Toast.LENGTH_LONG).show()
        }
    }

    private fun validateEmail(): Boolean {
        val emailPattern = Regex(pattern = EMAIL_PATTERN)
        return emailPattern.containsMatchIn(edtEmail.text)
    }

    private fun validatePassword(): Boolean {
        val passwordPattern = Regex(pattern = PASSWORD_PATTERN)
        return passwordPattern.containsMatchIn(edtPassword.text)
    }
}
