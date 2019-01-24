package asiantech.internship.summer.kotlin.eventandlistener

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-dinhtruong`.activity_event_and_listener.*
import java.util.regex.Pattern.compile

class EventAndListenerActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    companion object {
        const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$"
        const val USERNAME_PATTERN = "^.{6,18}$"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_and_listener)
        initViews()
    }

    private fun initViews() {
        edtUsername.onChange { checkUserPassEmail() }
        edtPassword.onChange { checkUserPassEmail() }
        edtEmail.onChange { checkUserPassEmail() }
        rbFemale.setOnCheckedChangeListener(this)
        rbMale.setOnCheckedChangeListener(this)
    }

    private fun isValidUsername(userName: String): Boolean {
        val patternUsername = compile(USERNAME_PATTERN)
        val matcherUsername = patternUsername.matcher(userName)
        return matcherUsername.matches()
    }

    private fun isValidPassword(passWord: String): Boolean {
        val patternPassword = compile(PASSWORD_PATTERN)
        val matchePassword = patternPassword.matcher(passWord)
        return matchePassword.matches()
    }

    private fun isValidEmail(email: String): Boolean {
        val matcherEmail = Patterns.EMAIL_ADDRESS.matcher(email)
        return matcherEmail.matches()
    }

    private fun checkUserPassEmail() {
        val userNameInput = edtUsername.text.toString().trim()
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

    private fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
