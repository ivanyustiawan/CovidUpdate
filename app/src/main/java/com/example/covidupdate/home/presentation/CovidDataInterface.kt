package com.example.covidupdate.home.presentation

import com.example.covidupdate.home.modelView.CovidModelView
import io.reactivex.Observer

interface CovidDataInterface {

    fun registerObserver(observer: Observer<CovidModelView>)

    fun unregisterObserver(observer: Observer<CovidModelView>)

    fun clearDisposable()

    fun getDataSummary()
}