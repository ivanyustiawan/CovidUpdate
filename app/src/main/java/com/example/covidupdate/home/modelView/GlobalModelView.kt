package com.example.covidupdate.home.modelView

import java.io.Serializable

class GlobalModelView : Serializable {
    var newConfirmed :String? = ""
    var totalConfirmed :String? = ""
    var newDeath :String? = ""
    var totalDeath :String? = ""
    var newRecovered :String? = ""
    var totalRecovered :String? = ""
}