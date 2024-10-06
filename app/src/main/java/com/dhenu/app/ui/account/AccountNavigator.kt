package com.dhenu.app.ui.account

import com.dhenu.app.util.CommonNavigator

interface AccountNavigator : CommonNavigator {

    fun navigateToSignIn(message: String)
}