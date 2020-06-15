package fr.epita.android.covidata2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import fr.epita.android.covidata2019.models.CountryCalendar
import fr.epita.android.covidata2019.services.WSInterface
import kotlinx.android.synthetic.main.activity_calendar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CalendarActivity : AppCompatActivity() {
    var country : String = ""
    var from : String = ""
    var to : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        country = intent.getStringExtra("Country").toString()
        calendarCountry.text = country;
        calendarDate.setDate(System.currentTimeMillis(), true, true)

        calendarConfirmed.text = "SELECT"
        calendarDeaths.text = "A"
        calendarRecovered.text = "DATE"

        // Retrofit WS instantiation
        val baseURL = "https://api.covid19api.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: WSInterface = retrofit.create(WSInterface::class.java)

        val countryDataCallBack : Callback<List<CountryCalendar>> = object : Callback<List<CountryCalendar>> {
            override fun onFailure(call: Call<List<CountryCalendar>>, t: Throwable) {
                calendarConfirmed.text = "0"
                calendarDeaths.text = "0"
                calendarRecovered.text = "0"
            }

            override fun onResponse(
                call: Call<List<CountryCalendar>>,
                response: Response<List<CountryCalendar>>
            ) {
                val data = response.body()
                if (data != null && data.isNotEmpty())
                {
                    var confirmed = 0
                    var deaths = 0
                    var recovered = 0
                    for (elt in data.groupBy { it.Date.subSequence(0, 10) })
                    {
                        if (elt.key.subSequence(0, 10).equals(from.subSequence(0, 10))) {
                            confirmed = elt.value.sumBy { it.Confirmed }
                            deaths = elt.value.sumBy { it.Deaths }
                            recovered = elt.value.sumBy { it.Recovered }
                        }
                    }
                    calendarConfirmed.text = confirmed.toString()
                    calendarDeaths.text = deaths.toString()
                    calendarRecovered.text = recovered.toString()
                }
                else
                {
                    calendarConfirmed.text = "NO DATA"
                    calendarDeaths.text = "NO DATA"
                    calendarRecovered.text = "NO DATA"
                }
            }

        }

        calendarDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val dayOfMonthString = if(dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth

            from = if(month+1 >= 10) "$year-${month+1}-${dayOfMonthString}T00:00:00Z"
                       else "$year-0${month+1}-${dayOfMonthString}T00:00:00Z"

            to = if(month+1 >= 10) "$year-${month+1}-${dayOfMonthString}T23:59:59Z"
            else "$year-0${month+1}-${dayOfMonthString}T23:59:59Z"
            Log.d("tag", "onCreate: $from to $to")
            service.getCountryDate(country, from, to).enqueue(countryDataCallBack)
        }
    }
}