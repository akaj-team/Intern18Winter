package asiantech.internship.summer.kotlin.eventlistener

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-baole`.kotlin_activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val minLength: Int = 6
    private val emailPattern: String = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private val passwordPattern: String = "(?=.*?[a-z])(?=.*?[0-9]).{6,}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity_sign_up)

        edtUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkUsernameLength()
                isValidInformation()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkPasswordLengthAndFormat()
                isValidInformation()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkEmailFormat()
                isValidInformation()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        rbMale.setOnClickListener { isValidInformation() }
        rbFemale.setOnClickListener { isValidInformation() }
    }

    private fun checkUsernameLength() {
        if (edtUsername.text.length < minLength) {
            edtUsername.error = getString(R.string.username6Letters)
            edtUsername.requestFocus()
        } else {
            edtUsername.error = null
        }
    }

    private fun checkPasswordLengthAndFormat() {
        if (edtPassword.text.length < minLength) {
            edtPassword.error = getString(R.string.password6Letters)
            edtPassword.requestFocus()
        } else if (!validatePassword()) {
            edtPassword.error = getString(R.string.password1char1num)
            edtPassword.requestFocus()
        } else {
            edtPassword.error = null
        }
    }

    private fun checkEmailFormat() {
        if (!validateEmail()) {
            edtEmail.error = getString(R.string.invalidEmail)
            edtEmail.requestFocus()
        } else {
            edtEmail.error = null
        }
    }

    private fun validatePassword(): Boolean {
        val passwordPattern = Regex(pattern = passwordPattern)
        return passwordPattern.containsMatchIn(edtPassword.text)
    }

    private fun validateEmail(): Boolean {
        val emailPattern = Regex(pattern = emailPattern)
        return emailPattern.containsMatchIn(edtEmail.text)
    }

    private fun isValidInformation() {
        if (edtUsername.text.length >= minLength && edtPassword.text.length >= minLength
                && validatePassword() && validateEmail()
                && (rbMale.isChecked || rbFemale.isChecked)) {
            btnApply.isEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                btnApply.setBackgroundColor(resources.getColor(R.color.colorYellow, theme))
            }
            onClickApplyButton()
        } else {
            btnApply.isEnabled = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                btnApply.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark, theme))
            }
        }
    }

    private fun onClickApplyButton() {
        btnApply.setOnClickListener {
            Toast.makeText(applicationContext, R.string.signUpSuccessfully, Toast.LENGTH_LONG).show()
        }
    }
}
