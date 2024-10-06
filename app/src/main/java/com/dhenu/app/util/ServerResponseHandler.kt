package com.dhenu.app.util

import androidx.annotation.StringRes
import com.dhenu.app.MyApplication
import com.dhenu.app.R

import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader

object ServerResponseHandler {

    fun getString(@StringRes stringId: Int): String {
        return MyApplication.instance!!.getString(stringId)
    }

    fun getHttpCodeError(statusCode: Int): String {

        var error = "Error"

        when (statusCode) {

            400 -> error = getString(R.string.https_400_error)
            401 -> error = getString(R.string.http_401_error)
            403 -> error = getString(R.string.http_403_error)
            404 -> error = getString(R.string.http_404_error)
            500 -> error = getString(R.string.http_500_error)
        }
        return error
    }

    fun checkJsonErrorBody(jobject: JSONObject): String {
        try {
            if (jobject.has("error")) {
                val error = jobject.get("error")
                return getJsonErrorBody(error, jobject)
            } else {
                return jobject.optString("message")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun checkJsonSuccessBody(jobject: JSONObject): String {
        try {
            if (jobject.has("success")) {
                return jobject.optString("success")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }


    fun getResponseBody(response: Response<ResponseBody>): String? {

        var reader: BufferedReader? = null
        var output: String? = null
        try {

            if (response.body() != null) {
                reader = BufferedReader(InputStreamReader(response.body()!!.byteStream()))
            } else if (response.errorBody() != null) {
                reader = BufferedReader(InputStreamReader(response.errorBody()!!.byteStream()))
            }
            output = reader!!.readLine()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return output
    }

    fun getJsonErrorBody(error: Any, jobject: JSONObject): String {
        try {

            if (error is JSONObject) {
                var message = ""
                var name = ""
                var field = ""
                if (error.has("field"))
                    field = error.optString("field")

                if (error.has("message")) {
                    val msgObj = error.get("message")
                    if (msgObj is JSONObject) {
                        name = msgObj.optString("name")
                        message = msgObj.optString("message")
                    } else {
                        message = error.optString("message")
                    }
                } else {
                    try {
                        message = jobject.optString("message")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                //                return field + "\n" + name + "\n" + message;
                return message
            } else if (error is JSONArray) {

                try {
                    return error.getJSONObject(0).optString("message")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        } catch (je: Exception) {
            je.printStackTrace()
        }

        return ""
    }
}
