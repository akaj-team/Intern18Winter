package asiantech.internship.summer.menu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-phutran`.activity_menu.*

class MenuActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnExerciseJava -> {
                val intent = Intent(this@MenuActivity, ListActivityJava::class.java)
                startActivity(intent)
            }
            R.id.btnExerciseKotlin -> {
                val intent = Intent(this@MenuActivity, ListActivityKotlin::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        btnExerciseJava.setOnClickListener(this)
        btnExerciseKotlin.setOnClickListener(this)
    }
}
