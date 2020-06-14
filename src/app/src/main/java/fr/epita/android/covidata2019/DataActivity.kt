package fr.epita.android.covidata2019

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.core.util.LogWriter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import fr.epita.android.covidata2019.adapters.CountryAdapter
import fr.epita.android.covidata2019.models.Country
import fr.epita.android.covidata2019.models.CovidData
import fr.epita.android.covidata2019.models.WorldData
import fr.epita.android.covidata2019.services.WSInterface
import kotlinx.android.synthetic.main.activity_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataActivity : AppCompatActivity(), CountryAdapter.OnCountryListener {
    val countryList = ArrayList<Country>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        dataCountryList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // Retrofit WS instantiation
        val baseURL = "https://api.covid19api.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: WSInterface = retrofit.create(WSInterface::class.java)

        // country list
        val countriesCallback = countryCallBack(dataCountryList)
        service.getCountriesList().enqueue(countriesCallback)

        // World Stats
        val WorldCallBack : Callback<WorldData> = object : Callback<WorldData> {
            override fun onFailure(call: Call<WorldData>, t: Throwable) {
                dataConfirmedWorld.text = "0"
                dataDeathsWorld.text = "0"
                dataRecoveredWorld.text = "0"
                Log.w("WORLD CALL BACK", "Web service failed")
            }

            override fun onResponse(call: Call<WorldData>, response: Response<WorldData>) {
                if (response.isSuccessful)
                {
                    val data = response.body()
                    if (data != null) {
                        dataConfirmedWorld.text = data.TotalConfirmed.toString()
                        dataDeathsWorld.text = data.TotalDeaths.toString()
                        dataRecoveredWorld.text = data.TotalRecovered.toString()
                    }
                }
            }
        }

        service.getWorldData().enqueue(WorldCallBack)


    }

    override fun onCountryClick(name: String) {
        val intent = Intent(this@DataActivity, CalendarActivity::class.java)
        intent.putExtra("Country", name)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this)
            .toBundle())
    }

    private fun countryCallBack(countryRecyclerView: RecyclerView)
            : Callback<List<CovidData>> {
        return object : Callback<List<CovidData>> {
            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                Log.w("COUNTRY CALLBACK", "Webservice call Failed")
            }

            override fun onResponse(
                call: Call<List<CovidData>>,
                response: Response<List<CovidData>>
            ) {
                if (response.isSuccessful) {
                    Log.w("COUNTRY CALLBACK", "Webservice call Succeed")
                    val destinationList = response.body()!!.sortedBy {it.Country }
                    for (elt in destinationList)
                        countryList.add(
                            Country(
                                (elt.Country)
                            )
                        )
                    countryRecyclerView.adapter =
                        CountryAdapter(
                            countryList,
                            this@DataActivity
                        )
                }
            }
        }
    }
}