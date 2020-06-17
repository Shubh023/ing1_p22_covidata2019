package fr.epita.android.covidata2019

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.epita.android.covidata2019.mystery.MysteryActivity
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {
    var easterEggCounter : Int = 6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainDataBtn.setOnClickListener {
            startActivity(Intent(MainActivity@this, DataActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        mainGraphBtn.setOnClickListener {
            startActivity(Intent(MainActivity@this, GraphActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        // pour tester en attendant
        mainMysteryBtn.setOnClickListener {
            startActivity(Intent(MainActivity@this, MysteryActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        //easter egg
        logoEpita.setOnClickListener {
            easterEggCounter--;
            if (easterEggCounter == 0)
                Toast.makeText(this, "Well Done ! Welcome", Toast.LENGTH_SHORT).show()
            else if (easterEggCounter < 4)
                Toast.makeText(this, "Remaining ${easterEggCounter.toString()} clicks", Toast.LENGTH_SHORT).show()
            if (easterEggCounter <= 0)
            {
                // to change to misteryActivity
                startActivity(Intent(this, DataActivity::class.java))
                easterEggCounter = 6
            }
        }
    }
}