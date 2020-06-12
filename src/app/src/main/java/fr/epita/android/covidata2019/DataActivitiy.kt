package fr.epita.android.covidata2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_data.*

class DataActivitiy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        dataConfirmedWorld.text = "0"
        dataDeathsWorld.text = "0"
        dataRecoveredWorld.text = "0"

    }
}