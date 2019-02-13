package asiantech.internship.summer.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import asiantech.internship.summer.R
import asiantech.internship.summer.asynctask.AsyncTaskThreadHandlerActivity
import asiantech.internship.summer.canvas.CanvasActivity
import asiantech.internship.summer.drawerlayout.DrawerLayoutActivity
import asiantech.internship.summer.eventlistener.SignUpActivity
import asiantech.internship.summer.file_storage.FileStorageActivity
import asiantech.internship.summer.fragment.LoginActivity
import asiantech.internship.summer.recyclerview.RecyclerViewActivity
import asiantech.internship.summer.retrofit.RetrofitActivity
import asiantech.internship.summer.unittest.UnitTestActivity
import asiantech.internship.summer.viewandviewgroup.ViewActivity
import asiantech.internship.summer.viewpager.ViewPagerActivity
import kotlinx.android.synthetic.`at-phutran`.activity_list.*

@SuppressLint("Registered")
class ListActivityJava : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnExercise1 -> {
                val intent = Intent(this@ListActivityJava, ViewActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise2 -> {
                val intent = Intent(this@ListActivityJava, SignUpActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise3 -> {
                val intent = Intent(this@ListActivityJava, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise4 -> {
                val intent = Intent(this@ListActivityJava, RecyclerViewActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise5 -> {
                val intent = Intent(this@ListActivityJava, ViewPagerActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise8 -> {
                val intent = Intent(this@ListActivityJava, RetrofitActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise9 -> {
                val intent = Intent(this@ListActivityJava, CanvasActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise11 -> {
                val intent = Intent(this@ListActivityJava, AsyncTaskThreadHandlerActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise10 -> {
                val intent = Intent(this@ListActivityJava, UnitTestActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise7 -> {
                val intent = Intent(this@ListActivityJava, DrawerLayoutActivity::class.java)
                startActivity(intent)
            }
            R.id.btnExercise6 -> {
                val intent = Intent(this@ListActivityJava, FileStorageActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        btnExercise1.setOnClickListener(this)
        btnExercise2.setOnClickListener(this)
        btnExercise3.setOnClickListener(this)
        btnExercise4.setOnClickListener(this)
        btnExercise5.setOnClickListener(this)
        btnExercise8.setOnClickListener(this)
        btnExercise9.setOnClickListener(this)
        btnExercise11.setOnClickListener(this)
        btnExercise10.setOnClickListener(this)
        btnExercise7.setOnClickListener(this)
        btnExercise6.setOnClickListener(this)
    }
}
