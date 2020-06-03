package com.example.covidupdate.home.presentation

import com.example.covidupdate.home.data.CovidAPIService
import com.example.covidupdate.home.modelView.CovidModelView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainPresenter(private var mainInterface: MainInterface?) {

    private val covidAPIService: CovidDataInterface = CovidAPIService()

    fun registerObserver() {
        covidAPIService.registerObserver(getObserver())
    }

    fun unregisterObserver() {
        covidAPIService.unregisterObserver(getObserver())
    }

    fun getData() {
        mainInterface?.showLoading()
        covidAPIService.getDataSummary()
    }

    private fun getObserver(): Observer<CovidModelView> {
        return object : Observer<CovidModelView> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: CovidModelView) {
                mainInterface?.onDataSet(t)
                mainInterface?.hideLoading()
            }

            override fun onError(e: Throwable) {
                mainInterface?.hideLoading()
                mainInterface?.onError(e.message.orEmpty())
            }

        }
    }

    fun releaseReference(){
        mainInterface = null
        covidAPIService.clearDisposable()
    }

}