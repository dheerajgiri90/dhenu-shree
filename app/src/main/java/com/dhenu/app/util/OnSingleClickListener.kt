package com.dhenu.app.util

import android.os.SystemClock
import android.view.View

open class OnSingleClickListener(val onClicked: () -> Unit) : View.OnClickListener {

    private var mLastClickTime: Long = 0

    override fun onClick(v: View?) {
        val currentClickTime: Long = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime

        // mLastClickTime = currentClickTime
        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return
        } else {
            mLastClickTime = currentClickTime
            onClicked.invoke()
            return
        }
    }

    companion object {
        private const val MIN_CLICK_INTERVAL: Long = 1000
    }
}