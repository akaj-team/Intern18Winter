package asiantech.internship.summer.kotlin.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import asiantech.internship.summer.R

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity_timeline)
        initView()
    }

    private fun initView(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.timelineFragment, TimelineFragment())
        fragmentTransaction.commit()
    }
}
