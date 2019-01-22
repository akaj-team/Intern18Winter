package asiantech.internship.summer.kotlin.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.fragment_login.view.*
import kotlinx.android.synthetic.`at-phutran`.fragment_signup.*

@Suppress("NAME_SHADOWING")
class SignUpFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        view.btnSignUp.setOnClickListener {
            eventCheckSignUp()
        }
        return view
    }

    private fun eventCheckSignUp() {
        val loginFragment = LoginFragment()
        if (loginFragment.isValidCheckLogin(edtInputEmail.text.toString(), loginFragment.mEmailPattern) && loginFragment.isValidCheckLogin(edtInputPass.text.toString(), loginFragment.mPassPattern) && edtInputPass.text.toString() == edtConfirmPass.text.toString()) {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frContent, loginFragment)
            Toast.makeText(activity, R.string.success, Toast.LENGTH_SHORT).show()
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        } else {
            Toast.makeText(activity, R.string.checkInputSignUp, Toast.LENGTH_SHORT).show()
        }
    }
}
