package fr.epita.android.covidata2019

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import fr.epita.android.covidata2019.adapters.BarAdapter
import fr.epita.android.covidata2019.adapters.CountryAdapter
import fr.epita.android.covidata2019.models.Bar
import fr.epita.android.covidata2019.models.Country
import fr.epita.android.covidata2019.models.CovidData
import fr.epita.android.covidata2019.services.WSInterface
import kotlinx.android.synthetic.main.activity_graph.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GraphActivity : AppCompatActivity(), CountryAdapter.OnCountryListener {

    private var selectedCountry : String = ""
    private val bars = ArrayList<Bar>()
    private val countryList = ArrayList<Country>()
    private var lastPos : Int = 0


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundbtn_selected)

        // barGraph handling
        val barRecycler = findViewById<RecyclerView>(R.id.BarRecyclerView)
        barRecycler.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        // CountryList handling
        graphCountryList.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

        // in case of connection failure with API
        for (x in 1..5)
            bars.add(Bar("Failure", 0))

        // Retrofit WS instantiation
        val baseURL = "https://api.covid19api.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: WSInterface = retrofit.create(WSInterface::class.java)
        // Callback instantiation
        val wsCallBack: Callback<List<CovidData>> = barCallBack(barRecycler)

        val countriesCallback: Callback<List<CovidData>> = countryCallBack(graphCountryList)

        // defaultData : Confirmed
        service.getConfirmedList(selectedCountry).enqueue(wsCallBack)
        // fill Countries RecyclerView && creating listeners
        service.getCountriesList().enqueue(countriesCallback)

        // ConfirmedBtn Listener
        graphConfirmedBtn.setOnClickListener {
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphDeathsBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphRecoveredBtn.setBackgroundResource(R.drawable.backgroundlist)
            service.getConfirmedList(selectedCountry).enqueue(wsCallBack)
        }
        // DeathsBtn Listener
        graphDeathsBtn.setOnClickListener {
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphRecoveredBtn.setBackgroundResource(R.drawable.backgroundlist)
            service.getDeathsList(selectedCountry).enqueue(wsCallBack)
        }
        // RecoveredBtn Listener
        graphRecoveredBtn.setOnClickListener {
            service.getRecoveredList(selectedCountry).enqueue(wsCallBack)
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphDeathsBtn.setBackgroundResource(R.drawable.backgroundlist)
        }




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
                    onCountryClick(countryList[0].country)
                    countryRecyclerView.adapter =
                        CountryAdapter(
                            countryList,
                            this@GraphActivity
                        )
                }
            }
        }
    }



    private fun barCallBack(barRecycler: RecyclerView): Callback<List<CovidData>> {
        return object : Callback<List<CovidData>> {
            override fun onResponse(
                call: Call<List<CovidData>>,
                response: Response<List<CovidData>>
            ) {
                if (response.isSuccessful) {
                    Log.w("TAG", "WebService call Succeed")
                    val destinationList = response.body()
                    val barList = convertCovidDataToBars(destinationList)
                    barRecycler.adapter =
                        BarAdapter(barList)
                }
            }

            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                barRecycler.adapter =
                    BarAdapter(bars)
                Log.w("TAG", "WebService call failed")
            }
        }
    }

    fun convertCovidDataToBars(covidlist : List<CovidData>?): ArrayList<Bar> {
        val list = ArrayList<Bar>()
        if (covidlist != null) {
            val group = covidlist.groupBy { it.Date.subSequence(0,10).toString() }
            for (e in group)
            {
                val sum = e.value.sumBy { it.Cases }
                list.add(Bar(e.key, sum))
            }
        }
        if(list.isEmpty())
            list.add(
                Bar(
                    "No Cases Identified in $selectedCountry",
                    0
                )
            )
        return list
    }

    override fun onCountryClick(name: String) {
        // data change
        selectedCountry = name
        var msg = Toast.makeText(this, selectedCountry, Toast.LENGTH_SHORT)
        msg.setGravity(1, msg.xOffset, msg.yOffset/2)
        msg.show()
        graphConfirmedBtn.callOnClick()

    }

}