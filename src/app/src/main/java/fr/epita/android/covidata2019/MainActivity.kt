package fr.epita.android.covidata2019

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainDataBtn.setOnClickListener {
            startActivity(Intent(this, DataActivitiy::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        mainGraphBtn.setOnClickListener {
            startActivity(Intent(this, GraphActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }
}