package com.dhenu.app.ui.mortgage.addmortgage

import com.dhenu.app.ui.mortgage.response.AddMortgageResponse
import com.dhenu.app.util.CommonNavigator

interface AddMortgageNavigator : CommonNavigator {

    fun addMortgageResponse(response: AddMortgageResponse)
}