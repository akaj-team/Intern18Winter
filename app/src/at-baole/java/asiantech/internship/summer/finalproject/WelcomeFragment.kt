package asiantech.internship.summer.finalproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-baole`.final_fragment_welcome.*

class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val welcomeView: View = inflater.inflate(R.layout.final_fragment_welcome, container, false)
        data()
        return welcomeView
    }

    private fun data() {
        val usernameKey = "username"
        val string: String? = arguments?.getString(usernameKey).toString()
        tvWelcomeFinal?.text = string
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }
}
