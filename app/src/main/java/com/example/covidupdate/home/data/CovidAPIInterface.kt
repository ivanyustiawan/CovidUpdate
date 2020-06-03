package com.example.covidupdate.home.data

import com.example.covidupdate.home.modelResponse.CovidModelResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface CovidAPIInterface {

    @GET("/summary")
    fun getSummary(): Observable<CovidModelResponse>
}