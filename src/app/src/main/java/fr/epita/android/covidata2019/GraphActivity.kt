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

    var selectedData : String = "Confirmed"
    var selectedCountry : String = "France"
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
        barRecycler.adapter = BarAdapter(bars)

        var baseURL = "https://api.covid19api.com/"
        //val baseURL = "https://jsonplaceholder.typicode.com/"

        var jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        var service: WSInterface = retrofit.create(WSInterface::class.java)

        fun convertToBars(covidlist : List<CovidData>?): ArrayList<Bar> {
            var list = ArrayList<CovidData>()
            var barlist = ArrayList<Bar>()
            var datelist = ArrayList<String>()
            var casesList = ArrayList<Int>()
            if (covidlist != null) {
                for (i in covidlist) {
                    list.add(i)
                }
            }

            if (list != null) {
                for (i in list) {
                    var list2 = list
                    var date = i.Date
                    list2 = list2.filter { it.Date.startsWith(it.Date.subSequence(0,10)) == i.Date.startsWith(i.Date.subSequence(0,10)) } as ArrayList<CovidData>
                    var CasesCounter = 0
                    for (j in list2) {
                        CasesCounter += j.Cases
                    }
                    if (!datelist.contains(i.Date))
                    {
                        datelist.add(i.Date.subSequence(0,10).toString())
                        casesList.add(CasesCounter)
                    }

                }
            }
            for (d in datelist.indices)
            {
                barlist.add(Bar(datelist.elementAt(d),casesList.elementAt(d)))
            }

            return barlist
        }

        fun convertCovidDataToBars(covidlist : List<CovidData>?): ArrayList<Bar> {
            var list = ArrayList<Bar>()
            var barlist = ArrayList<Pair<String,Int>>()
            if (covidlist != null) {
                for (c in covidlist) {
                    barlist.add(Pair<String, Int>(c.Date.subSequence(0,10).toString(), c.Cases))
                }
            }
            for (b in barlist)
            {
                list.add(Bar(b.first,b.second))
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

        var wsTodoCallBack: Callback<List<ToDoObject>> = object : Callback<List<ToDoObject>> {
            override fun onFailure(call: Call<List<ToDoObject>>, t: Throwable) {
                Log.w("TAG", "WebService call failed")
                throw t
            }

            override fun onResponse(
                call: Call<List<ToDoObject>>,
                response: Response<List<ToDoObject>>
            ) {
                Log.w("TAG", "WebService Succeded")
            }
        }

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