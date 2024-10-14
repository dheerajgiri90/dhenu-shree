package com.dhenu.app.ui.mortgage.getmortgage

import com.dhenu.app.ui.mortgage.response.MortgageListResponse
import com.dhenu.app.util.CommonNavigator

interface MortgageListNavigator : CommonNavigator {

    fun mortgageListResponse(response: MortgageListResponse)
}