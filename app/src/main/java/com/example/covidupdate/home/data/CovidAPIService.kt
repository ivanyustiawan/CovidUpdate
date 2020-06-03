package com.example.covidupdate.home.data

import com.example.covidupdate.home.modelResponse.CountryModelResponse
import com.example.covidupdate.home.modelResponse.CovidModelResponse
import com.example.covidupdate.home.modelResponse.GlobalModelResponse
import com.example.covidupdate.home.modelView.CountryModelView
import com.example.covidupdate.home.modelView.CovidModelView
import com.example.covidupdate.home.modelView.GlobalModelView
import com.example.covidupdate.home.presentation.CovidDataInterface
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CovidAPIService : CovidDataInterface {

    private val observers: MutableSet<Observer<CovidModelView>> = mutableSetOf()
    private var baseURL: String = "https://api.covid19api.com"
    private val compositeDisposable = CompositeDisposable()

    override fun registerObserver(observer: Observer<CovidModelView>) {
        observers.add(observer)
    }

    override fun unregisterObserver(observer: Observer<CovidModelView>) {
        observers.remove(observer)
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun getDataSummary() {
        callAPISummary()
    }

    private fun callAPISummary() {
        val requestAPIInterface = Retrofit.Builder()
            .baseUrl(baseURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CovidAPIInterface::class.java)
        val disposable = requestAPIInterface.getSummary()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CovidModelResponse>() {
                override fun onComplete() {
                    compositeDisposable.remove(this)
                }

                override fun onNext(t: CovidModelResponse) {
                    notifyObserver(t)
                }

                override fun onError(e: Throwable) {
                    notifyObserverError(e)
                    compositeDisposable.remove(this)
                }

            })
        compositeDisposable.add(disposable)
    }

    private fun notifyObserverError(throwable: Throwable) {
        if (!observers.isNullOrEmpty()) {
            for (observer in observers) {
                observer.onError(throwable)
            }
        }
    }

    private fun notifyObserver(covidModelResponse: CovidModelResponse) {
        if (!observers.isNullOrEmpty()) {
            for (observer in observers) {
                observer.onNext(convertCovidModelResponseToCovidModelView(covidModelResponse))
            }
        }
    }

    private fun convertCovidModelResponseToCovidModelView(covidModelResponse: CovidModelResponse): CovidModelView {
        return CovidModelView().apply {
            this.globalModelView = covidModelResponse.global?.let {
                convertGlobalModelResponseToGlobalModelView(
                    it
                )
            }
            this.countryModelView = covidModelResponse.countries?.let {
                convertCountryModelResponseToCountryModelView(
                    it
                )
            }
            this.lastUpdatedDate = countryModelView?.lastUpdated
        }
    }

    private fun convertGlobalModelResponseToGlobalModelView(globalModelResponse: GlobalModelResponse): GlobalModelView {
        return GlobalModelView().apply {
            this.newConfirmed = globalModelResponse.newConfirmed.toString()
            this.totalConfirmed = globalModelResponse.totalConfirmed.toString()
            this.newDeath = globalModelResponse.newDeaths.toString()
            this.totalDeath = globalModelResponse.totalDeaths.toString()
            this.newRecovered = globalModelResponse.newRecovered.toString()
            this.totalRecovered = globalModelResponse.totalRecovered.toString()
        }
    }

    private fun convertCountryModelResponseToCountryModelView(listCountry: List<CountryModelResponse>): CountryModelView {
        listCountry.forEach {
            if ("Indonesia".equals(it.country.orEmpty(), ignoreCase = true)) {
                return CountryModelView().apply {
                    this.country = it.country.orEmpty()
                    this.newConfirmed = it.newConfirmed.toString()
                    this.totalConfirmed = it.totalConfirmed.toString()
                    this.newDeath = it.newDeaths.toString()
                    this.totalDeath = it.totalDeaths.toString()
                    this.newRecovered = it.newRecovered.toString()
                    this.totalRecovered = it.totalRecovered.toString()
                    this.lastUpdated = it.date.orEmpty()
                }
            }
        }
        return CountryModelView()
    }
}