package fr.epita.android.covidata2019.services

import fr.epita.android.covidata2019.CovidData
import retrofit2.Call
import retrofit2.http.GET

interface WSInterface {

    @GET("todos")
    fun getTodoList() : Call<List<ToDoObject>>

    @GET("country/france/status/confirmed")
    fun getConfirmedList() : Call<List<CovidData>>

    @GET("country/france/status/recovered")
    fun getRecoveredList() : Call<List<CovidData>>

    @GET("country/france/status/deaths")
    fun getDeathsList() : Call<List<CovidData>>
}