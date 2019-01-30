package asiantech.internship.summer.kotlin.recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.recyclerview.TimelineFragment

class TimelineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        initFragment()
    }

    private fun initFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, TimelineFragment())
        fragmentTransaction.commit()
    }
}
