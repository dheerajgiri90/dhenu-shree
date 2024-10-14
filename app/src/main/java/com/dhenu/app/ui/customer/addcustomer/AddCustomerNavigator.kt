package com.dhenu.app.ui.customer.addcustomer

import com.dhenu.app.ui.customer.response.AddCustomerResponse
import com.dhenu.app.ui.customer.response.CustomerListResponse
import com.dhenu.app.ui.village.response.AddVillageResponse
import com.dhenu.app.ui.village.response.VillageListResponse
import com.dhenu.app.util.CommonNavigator

interface AddCustomerNavigator : CommonNavigator {

    fun addCustomerResponse(response: AddCustomerResponse)
}