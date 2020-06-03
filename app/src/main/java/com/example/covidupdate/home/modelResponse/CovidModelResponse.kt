package com.example.covidupdate.home.modelResponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CovidModelResponse(
    @SerializedName("Global") var global: GlobalModelResponse? = null,
    @SerializedName("Countries") var countries: List<CountryModelResponse>? = listOf()
) : Serializable