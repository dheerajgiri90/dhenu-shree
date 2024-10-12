package com.dhenu.app.ui.businessman

import com.dhenu.app.ui.businessman.response.AddBusinessManResponse
import com.dhenu.app.ui.businessman.response.BusinessManListResponse
import com.dhenu.app.util.CommonNavigator

interface BusinessManNavigator : CommonNavigator {

    fun businessManListResponse(response: BusinessManListResponse)

    fun addBusinessManResponse(response: AddBusinessManResponse)
}