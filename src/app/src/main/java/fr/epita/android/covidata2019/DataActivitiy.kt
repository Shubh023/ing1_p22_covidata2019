package fr.epita.android.covidata2019

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.data_activitiy.*

class DataActivitiy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_activitiy)

        dataConfirmedWorld.text = "0"
        dataDeathsWorld.text = "0"
        dataRecoveredWorld.text = "0"

    }
}