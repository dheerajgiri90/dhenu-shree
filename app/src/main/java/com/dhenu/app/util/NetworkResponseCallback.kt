package com.dhenu.app.util

interface NetworkResponseCallback<T> {

    fun onResponse(`object`: T)

    fun onFailure(message: String)

    fun onServerError(error: String)

    fun onSessionExpire(error: String)

    fun onAppVersionUpdate(msg: String)

    fun onErrorMessage (errorMessage :String){}

}
