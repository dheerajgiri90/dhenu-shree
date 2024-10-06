package com.dhenu.app.data
import com.dhenu.app.util.NetworkResponseCallback
import com.dhenu.app.util.ServerResponseHandler
import com.dhenu.app.MyApplication
import com.dhenu.app.R

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

object Parser {

    @Throws(Exception::class)
    fun parse(response: Response<ResponseBody>?, parseCallBack: ParseCallBack) {
        if (response != null && response.isSuccessful && response.code() == 200) {
            val output = ServerResponseHandler.getResponseBody(response)
            val `object` = JSONObject(output!!)
            if (`object`.optString("success").equals("true", ignoreCase = true)) {
                parseCallBack.onSuccess()
            } else if (`object`.optString("success").equals("false", ignoreCase = true)) {
                val temp = `object`.getJSONObject("error")
                parseCallBack.onServerError(temp.optString("message"))
            }
        } else if (response != null) {
            if (response.code() == 400 || response.code() == 500 || response.code() == 403 || response.code() == 404 || response.code() == 401) {
                if (response.code() == 401) {
                    val output = ServerResponseHandler.getResponseBody(response)
                    val `object` = JSONObject(output!!)
                    val errorObject = `object`.getJSONObject("error")
                    parseCallBack.onSessionExpire(errorObject.optString("message"))
                } else {
                    parseCallBack.onServerError(response.message())
                }
            } else {
                val responseStr = ServerResponseHandler.getResponseBody(response)
                val jsonObject = JSONObject(responseStr!!)
                parseCallBack.onServerError(ServerResponseHandler.checkJsonErrorBody(jsonObject))
            }
        }
    }

    fun parseErrorResponse(throwable: Throwable, networkResponseCallback: NetworkResponseCallback<*>) {
        if (throwable is HttpException) {
            try {
                val response = throwable.response()
                if (response != null) {
                    when {
                        response.code() == 401 -> {
                            networkResponseCallback.onSessionExpire(ServerResponseHandler.checkJsonErrorBody(JSONObject(response.errorBody()!!.string())))
                        }

                        response.code() == 403 -> {
                            networkResponseCallback.onAppVersionUpdate(ServerResponseHandler.checkJsonErrorBody(JSONObject(response.errorBody()!!.string())))
                        }

                        response.code() == 500 -> {
                            networkResponseCallback.onServerError(MyApplication.instance!!.getString(R.string.http_500_error))
                        }

                        else -> {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            networkResponseCallback.onServerError(ServerResponseHandler.checkJsonErrorBody(jsonObject))

                            /*  if (ServerResponseHandler.checkJsonSuccessBody(jsonObject).equals("true", ignoreCase = true)) {
                                            networkResponseCallback.navigateToOtp(ServerResponseHandler.checkJsonErrorBody(jsonObject))
                                        } else {
                                            networkResponseCallback.onServerError(ServerResponseHandler.checkJsonErrorBody(jsonObject))
                                        }*/
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                networkResponseCallback.onServerError(MyApplication.instance!!.getString(R.string.http_some_other_error))
            }

        } else {
            networkResponseCallback.onServerError(MyApplication.instance!!.getString(R.string.http_some_other_error))
        }
    }

    interface ParseCallBack {
        fun onSuccess()

        fun onSessionExpire(msg: String)

        fun onServerError(msg: String)

        fun onAppVersionUpdate(msg: String)
    }
}
