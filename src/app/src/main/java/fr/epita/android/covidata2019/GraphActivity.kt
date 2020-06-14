package fr.epita.android.covidata2019

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import fr.epita.android.covidata2019.services.ToDoObject
import fr.epita.android.covidata2019.services.WSInterface
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GraphActivity : AppCompatActivity() {

    var defaultData : String = "Confirmed"
    var defaultCountry : String = "France"
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundbtn_selected)


        val barRecycler = findViewById<RecyclerView>(R.id.BarRecyclerView)

        barRecycler.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        var bars = ArrayList<Bar>()

        for (x in 1..5)
        {
            bars.add(Bar("Failure", 0))
        }

        var baseURL = "https://api.covid19api.com/country/$defaultCountry"

        var jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        var service: WSInterface = retrofit.create(WSInterface::class.java)

        fun convertCovidDataToBars(covidlist : List<CovidData>?): ArrayList<Bar> {
            var list = ArrayList<Bar>()
            if (covidlist != null) {
                var groupby = covidlist.groupBy { it.Date.subSequence(0,10).toString() }
                for (e in groupby)
                {
                    var sum = e.value.sumBy { it.Cases }
                    list.add(Bar(e.key,sum));
                }
            }
            return list
        }

        var wsCallBack: Callback<List<CovidData>> = object : Callback<List<CovidData>> {
                override fun onResponse(
                    call: Call<List<CovidData>>,
                    response: Response<List<CovidData>>
                ) {
                    if (response.isSuccessful)
                    {
                        Log.w("TAG", "WebService call Succeded")
                        var destinationList = response.body()
                        var barlist = convertCovidDataToBars(destinationList)
                        barRecycler.adapter = BarAdapter(barlist)
                    }
                }
            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                barRecycler.adapter = BarAdapter(bars)
                Log.w("TAG", "WebService call failed")
            }
        }

        service.getConfirmedList().enqueue(wsCallBack)
        graphConfirmedBtn.setOnClickListener {
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphDeathsBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphRecoveredBtn.setBackgroundResource(R.drawable.backgroundlist)
            service.getConfirmedList().enqueue(wsCallBack)
        }

        graphDeathsBtn.setOnClickListener {
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphRecoveredBtn.setBackgroundResource(R.drawable.backgroundlist)
            service.getDeathsList().enqueue(wsCallBack)
        }
        graphRecoveredBtn.setOnClickListener {
            service.getRecoveredList().enqueue(wsCallBack)
            it.setBackgroundResource(R.drawable.backgroundbtn_selected)
            graphConfirmedBtn.setBackgroundResource(R.drawable.backgroundlist)
            graphDeathsBtn.setBackgroundResource(R.drawable.backgroundlist)
        }
    }
}