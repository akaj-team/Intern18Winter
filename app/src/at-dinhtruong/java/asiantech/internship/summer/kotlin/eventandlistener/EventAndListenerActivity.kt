package asiantech.internship.summer.kotlin.eventandlistener

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioButton
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-dinhtruong`.activity_event_and_listener.*
import kotlinx.android.synthetic.`at-dinhtruong`.fragment_share_preference.*
import java.util.regex.Pattern

class EventAndListenerActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private val EMAIL_PATTERN =
            "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$"
    private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$"
    private val USERNAME_PATTERN = "^.{6,18}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_and_listener)
        initViews()
    }

    private fun initViews() {
        edtUsername.addTextChangedListener(loginTextWatcher)
        edtPassword.addTextChangedListener(loginTextWatcher)
        edtEmail.addTextChangedListener(loginTextWatcher)
        rbFemale.setOnCheckedChangeListener(this)
        rbMale.setOnCheckedChangeListener(this)
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            checkUserPassEmail()
        }
    }

    private fun isValidUsername(userName: String): Boolean {
        val patternUsername = Pattern.compile(USERNAME_PATTERN)
        val matcherUsername = patternUsername.matcher(userName)
        return matcherUsername.matches()
    }

    private fun isValidPassword(passWord: String): Boolean {
        val patternPassword = Pattern.compile(PASSWORD_PATTERN)
        val matchePassword = patternPassword.matcher(passWord)
        return matchePassword.matches()
    }

    private fun isValidEmail(email: String): Boolean {
        val patternEmail = Pattern.compile(EMAIL_PATTERN)
        val matcherEmail = patternEmail.matcher(email)
        return matcherEmail.matches()
    }

    private fun checkUserPassEmail() {
        val userNameInput = edtUserName.text.toString().trim()
        val passWordInput = edtPassword.text.toString().trim()
        val emailInput = edtEmail.text.toString().trim()
        if (isValidUsername(userNameInput) && isValidPassword(passWordInput) && isValidEmail(emailInput) && (rbMale.isChecked || rbFemale.isChecked)) {
            imgApply.visibility = View.VISIBLE
        } else if (!isValidUsername(userNameInput)) {
            imgApply.visibility = View.GONE
            edtUsername.error = getString(R.string.userFormatWrong)
        } else if (!isValidPassword(passWordInput)) {
            imgApply.visibility = View.GONE
            edtPassword.error = getString(R.string.passWordFormatWrong)
        } else if (!isValidEmail(emailInput)) {
            imgApply.visibility = View.GONE
            edtEmail.error = getString(R.string.emailAddressFormatWrong)
        } else {
            imgApply.visibility = View.GONE
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        checkUserPassEmail()
    }
}
