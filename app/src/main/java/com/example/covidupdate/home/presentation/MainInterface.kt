package com.example.covidupdate.home.presentation

import com.example.covidupdate.home.modelView.CovidModelView

interface MainInterface {

    fun onDataSet(covidModelView: CovidModelView?)

    fun showLoading()

    fun hideLoading()

    fun onError(string: String)
}