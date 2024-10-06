package com.dhenu.app.ui.login

import com.dhenu.app.ui.login.response.LoginResponse
import com.dhenu.app.util.CommonNavigator

interface LoginNavigator : CommonNavigator {

    fun usedInstead()
    fun onClickSignup()
    fun onclickForgotPassword()
    fun loginSuccess(data: LoginResponse)

}