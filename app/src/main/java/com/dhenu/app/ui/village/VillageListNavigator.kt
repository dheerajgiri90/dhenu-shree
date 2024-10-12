package com.dhenu.app.ui.village

import com.dhenu.app.ui.village.response.AddVillageResponse
import com.dhenu.app.ui.village.response.VillageListResponse
import com.dhenu.app.util.CommonNavigator

interface VillageListNavigator : CommonNavigator {

    fun villageListResponse(response: VillageListResponse)
//
    fun addVillageResponse(response: AddVillageResponse)
}