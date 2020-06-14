package fr.epita.android.covidata2019.services

import fr.epita.android.covidata2019.CovidData
import retrofit2.Call
import retrofit2.http.GET


// from /country/$CountryName/
interface WSInterface {

    @GET("status/confirmed")
    fun getConfirmedList() : Call<List<CovidData>>

    @GET("status/recovered")
    fun getRecoveredList() : Call<List<CovidData>>

    @GET("status/deaths")
    fun getDeathsList() : Call<List<CovidData>>
}