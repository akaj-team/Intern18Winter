package asiantech.internship.summer.kotlin.recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import asiantech.internship.summer.R

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initFragment()
    }

    private fun initFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, PagerFragment.newInstance())
        fragmentTransaction.commit()
    }
}
