package fr.epita.android.covidata2019.mystery

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import fr.epita.android.covidata2019.R
import kotlinx.android.synthetic.main.activity_mystery.*
import java.lang.Runnable
import java.util.*
import kotlin.collections.ArrayList


class MysteryActivity : AppCompatActivity() {

    var score : Int = 0
    var ImageArray = ArrayList<ImageView>()
    var Handler : Handler = Handler()
    var Runnable : Runnable = Runnable {  }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mystery)

        score = 0

        ImageArray = arrayListOf(Case00, Case01, Case02, Case10, Case11, Case12, Case20, Case21, Case22)

        HideImage()

        object : CountDownTimer(10000, 1000) {

            override fun onFinish() {
                TimerText.text = "Time's Off"
                Handler.removeCallbacks(Runnable)
                for (image in ImageArray) {
                    image.visibility = View.INVISIBLE
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                TimerText.text = "Time: " + millisUntilFinished / 100
            }

        }.start()
    }

    fun HideImage() {

            Runnable = object : Runnable {
                override fun run() {

                    for (image in ImageArray) {
                        image.visibility = View.INVISIBLE
                    }

                    val random = Random()
                    val index = random.nextInt(8 - 0)
                    ImageArray[index].visibility = View.VISIBLE

                    Handler.postDelayed(Runnable, 1000)
                }
            }
        Handler.post(Runnable)

    }

    fun IncreaseScore(view: View) {

        score++

        ScoreText.text = "Score: " + score

    }
}