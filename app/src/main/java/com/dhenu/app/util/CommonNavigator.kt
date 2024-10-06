package com.dhenu.app.util

interface CommonNavigator {

    fun init()

    fun showProgress()

    fun hideProgress()

    fun showServerError(error: String)

    fun showAppUpdateError(error: String)

    fun showNetworkError(error: String)

    fun showSessionExpireAlert(error: String)

    fun showValidationError(message: String)

    fun getStringResource(id: Int): String

    fun getIntegerResource(id: Int): Int

    fun setStatusBarColor() {}

    fun showNetworkError(error: String, isRedirect: Boolean) {}

    fun onBackClick()

}
