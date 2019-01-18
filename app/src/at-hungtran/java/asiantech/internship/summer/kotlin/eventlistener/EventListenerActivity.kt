package asiantech.internship.summer.kotlin.eventlistener

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hungtran`.activity_login.*

class EventListenerActivity : AppCompatActivity() {
    private val mUsernamePattern = "^(?=.*[[0-9]a-zA-Z]).{6,18}$"
    private val mPasswordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$"
    private val mEmailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private val mTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            checkUsernamePasswordEmail()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtUser.addTextChangedListener(mTextWatcher)
        edtPwd.addTextChangedListener(mTextWatcher)
        edtEmail.addTextChangedListener(mTextWatcher)
        edtPwd.transformationMethod = PasswordTransformationMethod.getInstance()
        checkUsernamePasswordEmail()
    }

    private fun checkUsernamePasswordEmail() {
        if (validateUsername() && validatePasswword() && validateEmail()) {
            btnLogin.visibility = View.VISIBLE
        } else {
            btnLogin.visibility = View.GONE
        }
    }

    private fun validateUsername(): Boolean {
        val username = Regex(pattern = mUsernamePattern)
        return username.containsMatchIn(edtUser.text.toString())
    }

    private fun validatePasswword(): Boolean {
        val password = Regex(pattern = mPasswordPattern)
        return password.containsMatchIn(edtPwd.text.toString())
    }

    private fun validateEmail(): Boolean {
        val email = Regex(pattern = mEmailPattern)
        return email.containsMatchIn(edtEmail.text.toString())
    }
}
