package com.dhenu.app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.dhenu.app.R
import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.data.local.PreferenceKeys
import com.dhenu.app.ui.login.LoginActivity

object ClearPreference {
    fun clearDataLogout(ctx: Context) {
        clearingValues(ctx)
    }

    private fun clearingValues(ctx: Context) {

        try {
            AppPreference.clearSharedPreference()
            AppPreference.addValue(PreferenceKeys.GET_STARTED, "selected")
            val intent = Intent(ctx, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            ctx.startActivity(intent)
            (ctx as Activity).overridePendingTransition(
                R.anim.right_slide_in,
                R.anim.right_slide_out
            )
        } catch (w: Exception) {
            w.printStackTrace()
        }

    }

}
