package com.dhenu.app.ui.mortgage.mortgagedetail

import com.dhenu.app.ui.mortgage.response.UpdateMortgageResponse
import com.dhenu.app.util.CommonNavigator

interface MortgageDetailNavigator : CommonNavigator {

    fun updateMortgage(response: UpdateMortgageResponse, isClose: Boolean)

}