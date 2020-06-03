package com.example.covidupdate.home.modelResponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CountryModelResponse(
    @SerializedName("Country") var country: String? = "",
    @SerializedName("NewConfirmed") var newConfirmed: Long? = 0,
    @SerializedName("TotalConfirmed") var totalConfirmed: Long? = 0,
    @SerializedName("NewDeaths") var newDeaths: Long? = 0,
    @SerializedName("TotalDeaths") var totalDeaths: Long? = 0,
    @SerializedName("NewRecovered") var newRecovered: Long? = 0,
    @SerializedName("TotalRecovered") var totalRecovered: Long? = 0,
    @SerializedName("Date") var date: String? = ""
) : Serializable
