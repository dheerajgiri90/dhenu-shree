package com.dhenu.app.ui.exchange.exchangedetail

import com.dhenu.app.ui.exchange.response.AddExchangeItemResponse
import com.dhenu.app.ui.exchange.response.CloseExchangeResponse
import com.dhenu.app.ui.exchange.response.ExchangeItemListResponse
import com.dhenu.app.util.CommonNavigator

interface ExchangeDetailNavigator : CommonNavigator {

    fun exchangeItemsListResponse(response: ExchangeItemListResponse)

    fun addExchangeItemResponse(response: AddExchangeItemResponse)

    fun closeExchangeItemResponse(response: CloseExchangeResponse)
}