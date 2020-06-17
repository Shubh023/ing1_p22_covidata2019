package fr.epita.android.covidata2019.mystery

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import fr.epita.android.covidata2019.R
import kotlinx.android.synthetic.main.activity_mystery.*
import java.lang.Runnable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.round


class MysteryActivity : AppCompatActivity() {

    var score : Int = 0
    var maxScore : Int = 0
    var ImageArray = ArrayList<ImageView>()
    val random = Random()



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mystery)

        score = 0

        ImageArray = arrayListOf(Case00, Case01, Case02, Case10, Case11, Case12, Case20, Case21, Case22)

        maxScore = intent.getIntExtra("Max", 0);
        if (maxScore != 0)
            gameMaxScore.text = "max score : $maxScore"


        ScoreText.isEnabled = false

        object : CountDownTimer(15000, 600) {

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                TimerText.text = "Time's Off"
                for (image in ImageArray) {
                    image.visibility = View.INVISIBLE
                }
                if (score > maxScore) {
                    maxScore = score
                    gameMaxScore.text =  "max score : $maxScore"
                }
                ScoreText.text = "Score : $score ! Press to play"
                ScoreText.isEnabled = true
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                TimerText.text = "Time: " + (millisUntilFinished / 1000).toString()
                for (image in ImageArray) {
                    image.visibility = View.INVISIBLE
                }

                val index = random.nextInt(8 - 0)
                ImageArray[index].visibility = View.VISIBLE
            }

        }.start()


        ScoreText.setOnClickListener {
            startActivity(Intent(this, MysteryActivity::class.java).putExtra("Max", maxScore))
            finish()
        }
    }



    @SuppressLint("SetTextI18n")
    fun IncreaseScore(view: View) {
        score++
        ScoreText.text = "Score: $score"

    }
}