package fr.epita.android.covidata2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_calendar.*


class CalendarActivity : AppCompatActivity() {
    var country : String = "FRANCE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        calendarConfirmed.text = "0"
        calendarDeaths.text = "0"
        calendarRecovered.text = "0"
        calendarCountry.text = country;
        calendarDate.setDate(System.currentTimeMillis(), true, true)
    }
}