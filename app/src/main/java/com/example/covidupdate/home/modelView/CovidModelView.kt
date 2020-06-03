package com.example.covidupdate.home.modelView

import java.io.Serializable

class CovidModelView : Serializable {
    var lastUpdatedDate: String? = ""
    var globalModelView: GlobalModelView? = null
    var countryModelView: CountryModelView? = null
}