package asiantech.internship.summer.kotlin.eventlistener

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.activity_sign_up.*
import java.util.regex.Pattern

@SuppressLint("Registered")
@Suppress("NAME_SHADOWING")
class EventListenerSignUp : AppCompatActivity() {
    private val userPattern = "^.{6,18}$"
    private val passPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$"
    private var isCheckUser: Boolean = false
    private var isCheckPass: Boolean = false
    private var isCheckEmail: Boolean = false
    private var isCheckButton: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        unShowButtonApply()
        checkUserSignUp()
        checkPassSignUp()
        checkEmailSignUp()
        rgGender.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
            onRadioButtonClicked()
        }
    }

    private fun checkUserSignUp() {
        edtUser.onChange {
            if (isValidCheckSignUp(edtUser.text.toString(), userPattern)) {
                this.isCheckUser = true
                checkInputFullInformation()
            } else {
                isCheckUser = false
                unShowButtonApply()
                edtUser.error = getString(R.string.errorInput)
            }
        }
    }

    private fun checkEmailSignUp() {
        edtEmail.onChange {
            if (isValidCheckSignUp(edtEmail.text.toString(), Patterns.EMAIL_ADDRESS.toString())) {
                isCheckEmail = true
                checkInputFullInformation()
            } else {
                isCheckEmail = false
                unShowButtonApply()
                edtEmail.error = getString(R.string.errorInputEmail)
            }
        }
    }

    private fun isValidCheckSignUp(string: String, pattern: String): Boolean {
        val pattern = Pattern.compile(pattern)
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }

    private fun checkPassSignUp() {
        edtPass.onChange {
            if (isValidCheckSignUp(edtPass.text.toString(), passPattern)) {
                isCheckPass = true
                checkInputFullInformation()
            } else {
                unShowButtonApply()
                isCheckPass = false
                edtPass.error = getString(R.string.errorInputPassword)
            }
        }
    }

    private fun checkInputFullInformation() {
        if (isCheckUser && isCheckPass && isCheckEmail && isCheckButton) {
            showButtonApply()
        }
    }

    private fun unShowButtonApply() {
        btnCheck.visibility = View.GONE
    }

    private fun showButtonApply() {
        btnCheck.visibility = View.VISIBLE
    }

    private fun onRadioButtonClicked() {
        isCheckButton = true
        checkInputFullInformation()
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
