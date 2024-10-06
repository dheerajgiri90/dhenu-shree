package com.dhenu.app.ui.signup

import com.dhenu.app.ui.signup.response.SignUpResponse
import com.dhenu.app.util.CommonNavigator

interface SignupNavigator : CommonNavigator {
    fun onSignupClick()
    fun onSigninClick()
    fun signupSuccess(data: SignUpResponse)
}