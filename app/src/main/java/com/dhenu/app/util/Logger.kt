package com.dhenu.app.util

import android.util.Log

object Logger {

    private val YES_ENABLED = true
    private val NOT_ENABLED = false
    private val LOG_STATUS = YES_ENABLED
    private val LOG_PREFIX = " :-> "

    fun e(prefixP: String, textToLog: String) {
        try {
            val prefix = prefixP + LOG_PREFIX
            if (LOG_STATUS == YES_ENABLED) {
                Log.e(AppConstants.LOG_CAT, prefix + textToLog)
            }
        } catch (e: Exception) {
            Log.e(AppConstants.LOG_CAT, AppConstants.EXCEPTION_MSG + e.toString())
        }

    }

    fun v(prefixP: String, textToLog: String) {
        try {
            val prefix = prefixP + LOG_PREFIX
            if (LOG_STATUS == YES_ENABLED) {
                Log.v(AppConstants.LOG_CAT, prefix + textToLog)
            }
        } catch (e: Exception) {
            Log.v(AppConstants.LOG_CAT, AppConstants.EXCEPTION_MSG + e.toString())
        }

    }


    fun d(prefixP: String, textToLog: String) {
        try {
            val prefix = prefixP + LOG_PREFIX
            if (LOG_STATUS == YES_ENABLED) {
                Log.d(AppConstants.LOG_CAT, prefix + textToLog)
            }
        } catch (e: Exception) {
            Log.d(AppConstants.LOG_CAT, AppConstants.EXCEPTION_MSG + e.toString())
        }

    }


    fun i(prefixP: String, textToLog: String) {
        try {
            val prefix = prefixP + LOG_PREFIX
            if (LOG_STATUS == YES_ENABLED) {
                Log.e(AppConstants.LOG_CAT, prefix + textToLog)
            }
        } catch (e: Exception) {
            Log.i(AppConstants.LOG_CAT, AppConstants.EXCEPTION_MSG + e.toString())
        }

    }


    fun w(prefixP: String, textToLog: String) {

        try {

            val prefix = prefixP + LOG_PREFIX
            if (LOG_STATUS == YES_ENABLED) {
                Log.w(AppConstants.LOG_CAT, prefix + textToLog)
            }

        } catch (e: Exception) {

            Log.w(AppConstants.LOG_CAT, AppConstants.EXCEPTION_MSG + e.toString())

        }

    }
}
