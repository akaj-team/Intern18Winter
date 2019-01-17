package asiantech.internship.summer.kotlin.activityfragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-baole`.kotlin_fragment_sign_up.*
import kotlinx.android.synthetic.`at-baole`.kotlin_fragment_sign_up.view.*

class SignUpFragment : Fragment() {

    private val MIN_LENGTH: Int = 6
    private val PASSWORD_PATTERN: String = "^[A-Za-z0-9]{6,}$"
    private val EMAIL_PATTERN: String = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val signUpView = inflater.inflate(R.layout.kotlin_fragment_sign_up, container, false)
        onClickSignUp(signUpView)
        return signUpView
    }

    fun onClickSignUp(view: View) {
        view.btnSignUp.setOnClickListener { signUpValidation() }
    }

    fun signUpValidation() {
        if (edtPassword.text.length > MIN_LENGTH
                && validatePassword()
                && validateEmail()
                && edtPassword.text.equals(edtConfirmPassword.text)) {
            Toast.makeText(activity, R.string.signUpSuccessully, Toast.LENGTH_LONG).show()
        } else if (!validateEmail()) {
            Toast.makeText(activity, R.string.invalidEmail, Toast.LENGTH_LONG).show()
        } else if (edtPassword.text.length <= MIN_LENGTH) {
            Toast.makeText(activity, R.string.password7Letters, Toast.LENGTH_LONG).show()
        } else if (!validatePassword()) {
            Toast.makeText(activity, R.string.passwordOnlyCharAndNum, Toast.LENGTH_LONG).show()
        } else if (!edtPassword.text.equals(edtConfirmPassword.text)) {
            Toast.makeText(activity, R.string.confirmPasswordError, Toast.LENGTH_LONG).show()
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
