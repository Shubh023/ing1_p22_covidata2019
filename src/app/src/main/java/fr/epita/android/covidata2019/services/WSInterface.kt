package fr.epita.android.covidata2019.services

import fr.epita.android.covidata2019.models.CountryCalendar
import fr.epita.android.covidata2019.models.CovidData
import fr.epita.android.covidata2019.models.WorldData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WSInterface {

    @GET("country/{countryName}/status/confirmed")
    fun getConfirmedList(@Path(value="countryName", encoded=true) countryName : String) : Call<List<CovidData>>

    @GET("country/{countryName}/status/recovered")
    fun getRecoveredList(@Path(value="countryName", encoded=true) countryName : String) : Call<List<CovidData>>

    @GET("country/{countryName}/status/deaths")
    fun getDeathsList(@Path(value="countryName", encoded=true) countryName : String) : Call<List<CovidData>>

    @GET("countries")
    fun getCountriesList() : Call<List<CovidData>>

    @GET("world/total")
    fun getWorldData() : Call<WorldData>

    @GET("country/{countryName}")
    fun getCountryDate(@Path(value="countryName", encoded=true) countryName : String,
                       @Query("from") fromDate : String,
                       @Query("to") toDate : String) : Call<List<CountryCalendar>>
}