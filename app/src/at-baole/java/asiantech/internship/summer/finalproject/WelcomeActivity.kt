package asiantech.internship.summer.finalproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-baole`.final_activity_welcome.*

class WelcomeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_activity_welcome)

        val optionsAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.options, R.layout.support_simple_spinner_dropdown_item)
        optionsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerOptions.adapter = optionsAdapter
        spinnerOptions.onItemSelectedListener = this
        initWelcomeFragment()
    }

    private fun initWelcomeFragment() {
        val usernameKey = "username"
        val bundle = Bundle()
        val fragmentObject = WelcomeFragment()
        val string: String = intent.getStringExtra(usernameKey).toString()
        bundle.putString(usernameKey, string)
        fragmentObject.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.llWelcome, WelcomeFragment()).commit()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position){
            1 -> supportFragmentManager.beginTransaction().replace(R.id.llProfile, ProfileFragment()).commit()
            2 -> supportFragmentManager.beginTransaction().replace(R.id.llStatistics, StatisticsFragment()).commit()
        }
    }
}
