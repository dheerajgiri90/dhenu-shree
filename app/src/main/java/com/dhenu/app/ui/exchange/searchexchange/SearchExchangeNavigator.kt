package com.dhenu.app.ui.exchange.searchexchange

import com.dhenu.app.ui.mortgage.response.MortgageListResponse
import com.dhenu.app.util.CommonNavigator

interface SearchExchangeNavigator : CommonNavigator {

    fun searchExchangeResponse(response: MortgageListResponse)
}