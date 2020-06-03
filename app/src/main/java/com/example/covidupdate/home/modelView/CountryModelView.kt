package com.example.covidupdate.home.modelView

import java.io.Serializable

class CountryModelView : Serializable {
    var country: String? = ""
    var newConfirmed: String? = ""
    var totalConfirmed: String? = ""
    var newDeath: String? = ""
    var totalDeath: String? = ""
    var newRecovered: String? = ""
    var totalRecovered: String? = ""
    var lastUpdated: String? = ""
}