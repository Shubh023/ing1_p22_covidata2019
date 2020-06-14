package fr.epita.android.covidata2019.models

/*
    "Country": "France",
    "CountryCode": "FR",
    "Province": "Martinique",
    "City": "",
    "CityCode": "",
    "Lat": "14.64",
    "Lon": "-61.02",
    "Cases": 0,
    "Status": "deaths",
    "Date": "2020-01-23T00:00:00Z"
 */

data class CovidData (var Country : String,
                      var CountryCode : String,
                      var Province : String,
                      var City : String,
                      var CityCode: String,
                      var Lat: String,
                      var Lon: String,
                      var Cases : Int,
                      var Status : String,
                      var Date: String)