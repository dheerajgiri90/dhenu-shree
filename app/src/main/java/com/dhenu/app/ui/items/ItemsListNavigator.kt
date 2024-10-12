package com.dhenu.app.ui.items

import com.dhenu.app.ui.items.response.AddItemsResponse
import com.dhenu.app.ui.items.response.ItemsListResponse
import com.dhenu.app.util.CommonNavigator

interface ItemsListNavigator : CommonNavigator {

    fun itemListResponse(response: ItemsListResponse)

    fun addItemsResponse(response: AddItemsResponse)
}