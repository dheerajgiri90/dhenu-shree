package com.dhenu.app.data

import com.dhenu.app.MyApplication
import com.dhenu.app.R
import com.dhenu.app.util.NetworkResponseCallback
import com.dhenu.app.util.ServerResponseHandler
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import org.json.JSONObject

object Parser {

    fun parseErrorResponse(
        throwable: Throwable,
        networkResponseCallback: NetworkResponseCallback<*>
    ) {
        if (throwable is HttpException) {
            try {
                val response = throwable.response()

                if (response != null) {
                    when {
                        response.code() == 401 -> {
                            networkResponseCallback.onSessionExpire(
                                ServerResponseHandler.checkJsonErrorBody(
                                    JSONObject(response.errorBody()!!.string())
                                )
                            )
                        }

                        response.code() == 403 -> {
                            networkResponseCallback.onAppVersionUpdate(
                                ServerResponseHandler.checkJsonErrorBody(
                                    JSONObject(response.errorBody()!!.string())
                                )
                            )
                        }

                        response.code() == 500 -> {
                            networkResponseCallback.onServerError(
                                MyApplication.instance!!.getString(
                                    R.string.http_500_error
                                )
                            )
                        }

                        else -> {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            println(">>>>>>>>> ${jsonObject}")
                            networkResponseCallback.onServerError(
                                ServerResponseHandler.checkJsonErrorBody(
                                    jsonObject
                                )
                            )

                            /*  if (ServerResponseHandler.checkJsonSuccessBody(jsonObject).equals("true", ignoreCase = true)) {
                                            networkResponseCallback.navigateToOtp(ServerResponseHandler.checkJsonErrorBody(jsonObject))
                                        } else {
                                            networkResponseCallback.onServerError(ServerResponseHandler.checkJsonErrorBody(jsonObject))
                                        }*/
                        }
                    }
                }

            } catch (e: Exception) {
                println(">>>>>> $e")
                networkResponseCallback.onServerError(MyApplication.instance!!.getString(R.string.http_some_other_error))
            }

        } else {
            println(">>>>>>>> $throwable")
            networkResponseCallback.onServerError(MyApplication.instance!!.getString(R.string.http_some_other_error))
        }
    }

}
