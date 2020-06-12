package fr.epita.android.covidata2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.components.Description
import kotlinx.android.synthetic.main.activity_graph.*

class GraphActivity : AppCompatActivity() {

    var selectedData : String = "Confirmed"
    var selectedCountry : String = "France"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundbtn_selected)



        graphConfirmedBtn.setOnClickListener {
            selectedData = "Confirmed"
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphDeathsBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphRecoveredBtn.setBackgroundResource(R.drawable.backgroundlist)

        }

        graphDeathsBtn.setOnClickListener {
            selectedData = "Deaths"
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphRecoveredBtn.setBackgroundResource(R.drawable.backgroundlist)
        }

        graphRecoveredBtn.setOnClickListener {
            selectedData = "Recovered"
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphDeathsBtn.setBackgroundResource(R.drawable.backgroundlist)
        }
    }
}