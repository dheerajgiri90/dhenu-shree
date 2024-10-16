package com.dhenu.app.ui.exchange.exchangelist

import com.dhenu.app.ui.exchange.response.AddExchangeResponse
import com.dhenu.app.ui.exchange.response.ExchangeListResponse
import com.dhenu.app.util.CommonNavigator

interface ExchangeListNavigator : CommonNavigator {

    fun exchangeListResponse(response: ExchangeListResponse)

    fun addExchangeResponse(response: AddExchangeResponse)
}