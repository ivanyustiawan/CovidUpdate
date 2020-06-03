package com.example.covidupdate.home.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.covidupdate.databinding.ActivityMainBinding
import com.example.covidupdate.home.modelView.CovidModelView

class MainActivity : AppCompatActivity(), MainInterface {

    private var presenter = MainPresenter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        presenter.getData()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        presenter.registerObserver()
    }

    override fun onPause() {
        super.onPause()
        presenter.unregisterObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unregisterObserver()
        presenter.releaseReference()
    }

    override fun onDataSet(covidModelView: CovidModelView?) {
        binding.linearLayoutError.visibility = View.GONE
        binding.constraintLayoutContent.visibility = View.VISIBLE

        binding.textViewLastUpdatedDate.text = covidModelView?.lastUpdatedDate.orEmpty()

        binding.textViewNewConfirmedGlobal.text =
            covidModelView?.globalModelView?.newConfirmed.orEmpty()
        binding.textViewTotalConfirmedGlobal.text =
            covidModelView?.globalModelView?.totalConfirmed.orEmpty()
        binding.textViewNewDeathsGlobal.text = covidModelView?.globalModelView?.newDeath.orEmpty()
        binding.textViewTotalDeathsGlobal.text =
            covidModelView?.globalModelView?.totalDeath.orEmpty()
        binding.textViewNewRecoveredGlobal.text =
            covidModelView?.globalModelView?.newRecovered.orEmpty()
        binding.textViewTotalRecoveredGlobal.text =
            covidModelView?.globalModelView?.totalRecovered.orEmpty()

        binding.textViewLabelCountry.text = covidModelView?.countryModelView?.country.orEmpty()
        binding.textViewNewConfirmedCountry.text =
            covidModelView?.countryModelView?.newConfirmed.orEmpty()
        binding.textViewTotalConfirmedCountry.text =
            covidModelView?.countryModelView?.totalConfirmed.orEmpty()
        binding.textViewNewDeathsCountry.text = covidModelView?.countryModelView?.newDeath.orEmpty()
        binding.textViewTotalDeathsCountry.text =
            covidModelView?.countryModelView?.totalDeath.orEmpty()
        binding.textViewNewRecoveredCountry.text =
            covidModelView?.countryModelView?.newRecovered.orEmpty()
        binding.textViewTotalRecoveredCountry.text =
            covidModelView?.countryModelView?.totalRecovered.orEmpty()
    }

    override fun showLoading() {
        binding.linearLayoutLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.linearLayoutLoading.visibility = View.GONE
    }

    override fun onError(string: String) {
        binding.constraintLayoutContent.visibility = View.GONE
        binding.linearLayoutError.visibility = View.VISIBLE
    }
}
