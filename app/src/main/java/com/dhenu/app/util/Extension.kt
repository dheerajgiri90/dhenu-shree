package com.dhenu.app.util

import android.content.res.Resources
import android.view.View
import android.view.View.*

fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun String?.isStringNullOrBlank(): Boolean {

    try {
        if (this == null) {
            return true
        } else if (this == "null" || this == "" || this.isEmpty() || this.equals(
                "null",
                ignoreCase = true
            ) || this.equals("", ignoreCase = true)
        ) {
            return true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}

fun String?.setStringNullOrBlank(): String {

    try {
        if (this == null) {
            return ""
        } else if (this == "null" || this == "" || this.isEmpty() || this.equals(
                "null",
                ignoreCase = true
            ) || this.equals("", ignoreCase = true)
        ) {
            return ""
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this!!
}


fun View.show() {
    this.visibility = VISIBLE
}

fun View.hide() {
    this.visibility = GONE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}


fun View.disableLongClick() {
    setOnLongClickListener {
        true
    }
}
