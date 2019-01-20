package asiantech.internship.summer.kotlin.eventlistener

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.activity_sign_up.*
import java.util.regex.Pattern

@Suppress("NAME_SHADOWING")
class EventListenerSignUp : AppCompatActivity() {
    private val mEmailPattern = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(]?)$"
    private val mUserPattern = "^.{6,18}$"
    private val mPassPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$"
    var mIsCheckUser: Boolean = false
    var mIsCheckPass: Boolean = false
    var mIsCheckEmail: Boolean = false
    private var mIsCheckButton: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        unShowButtonApply()
        checkUserSignUp()
        checkPassSignUp()
        checkEmailSignUp()
    }

    private fun checkUserSignUp() {
        edtUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (isValidCheckSignUp(edtUser.text.toString(), mUserPattern)) {
                    mIsCheckUser = true
                    checkInputFullInformation()
                } else {
                    mIsCheckUser = false
                    unShowButtonApply()
                    edtUser.error = getString(R.string.errorInput)
                }
            }
        })
    }

    private fun checkEmailSignUp() {
        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (isValidCheckSignUp(edtEmail.text.toString(), mEmailPattern)) {
                    mIsCheckEmail = true
                    checkInputFullInformation()
                } else {
                    mIsCheckEmail = false
                    unShowButtonApply()
                    edtEmail.error = getString(R.string.errorInputEmail)
                }
            }
        })
    }

    private fun isValidCheckSignUp(string: String, pattern: String): Boolean {
        val pattern = Pattern.compile(pattern)
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }

    private fun checkPassSignUp() {
        edtPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (isValidCheckSignUp(edtPass.text.toString(), mPassPattern)) {
                    mIsCheckPass = true
                    checkInputFullInformation()
                } else {
                    unShowButtonApply()
                    mIsCheckPass = false
                    edtPass.error = getString(R.string.errorInputPassword)
                }
            }
        })
    }

    private fun checkInputFullInformation() {
        if (mIsCheckUser && mIsCheckPass && mIsCheckEmail && mIsCheckButton) {
            showButtonApply()
        }
    }

    private fun unShowButtonApply() {
        btnCheck.visibility = View.GONE
    }

    private fun showButtonApply() {
        btnCheck.visibility = View.VISIBLE
    }

    fun onRadioButtonClicked(view: View) {
        mIsCheckButton = true
        checkInputFullInformation()
    }
}