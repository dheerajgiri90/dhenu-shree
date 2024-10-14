package com.dhenu.app.ui.customer.customerlist

import com.dhenu.app.ui.customer.response.CustomerListResponse
import com.dhenu.app.ui.village.response.AddVillageResponse
import com.dhenu.app.ui.village.response.VillageListResponse
import com.dhenu.app.util.CommonNavigator

interface CustomerListNavigator : CommonNavigator {

    fun villageListResponse(response: CustomerListResponse)
//
    fun addVillageResponse(response: AddVillageResponse)
}