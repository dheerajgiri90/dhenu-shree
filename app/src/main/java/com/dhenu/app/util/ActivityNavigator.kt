package com.dhenu.app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dhenu.app.data.local.AppPreference

object ActivityNavigator {

    fun startActivity(mContext: Context, cls: Class<*>) {
        val mainIntent = Intent(mContext, cls)
        mContext.startActivity(mainIntent)
    }

    fun startActivity(mContext: Context, cls: Class<*>, bundle: Bundle, requestCode: Int) {
        val mainIntent = Intent(mContext, cls)
        mainIntent.putExtras(bundle)
        (mContext as AppCompatActivity).startActivityForResult(mainIntent, requestCode)
    }

    fun startActivity(mContext: Context, cls: Class<*>, requestCode: Int) {
        val mainIntent = Intent(mContext, cls)
        (mContext as AppCompatActivity).startActivityForResult(mainIntent, requestCode)
    }


    fun startActivity(mContext: Context, cls: Class<*>, bundle: Bundle) {
        val mainIntent = Intent(mContext, cls)
        mainIntent.putExtras(bundle)
        mContext.startActivity(mainIntent, bundle)
    }


    fun clearAllActivity(activity: Activity, tClass: Class<*>) {
        val intent = Intent(activity, tClass)
        /* notificationManager = activity.getSystemService(FirebaseMessagingService.NOTIFICATION_SERVICE) as NotificationManager
         notificationManager.cancelAll()*/
        AppPreference.clearSharedPreference()
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        activity.startActivity(intent)
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }


    fun startActivityWithDataClearTop(mContext: Context, cls: Class<*>, bundle: Bundle) {
        val mainIntent = Intent(mContext, cls)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        mainIntent.putExtras(bundle)
        mContext.startActivity(mainIntent)
    }

    fun startActivity(mContext: Context, fragment: Fragment, cls: Class<*>, requestCode: Int) {
        val mainIntent = Intent(mContext, cls)
        fragment.startActivityForResult(mainIntent, requestCode)
    }

    fun startActivity(
        mContext: Context,
        fragment: Fragment,
        cls: Class<*>,
        requestCode: Int,
        bundle: Bundle
    ) {
        val mainIntent = Intent(mContext, cls)
        mainIntent.putExtras(bundle)
        fragment.startActivityForResult(mainIntent, requestCode)
    }

    fun setOkResult(mContext: Context) {
        val output = Intent()
        (mContext as AppCompatActivity).setResult(AppCompatActivity.RESULT_OK, output)
    }

    fun setOkResult(mContext: Context, bn: Bundle) {
        val output = Intent()
        output.putExtras(bn)
        (mContext as AppCompatActivity).setResult(AppCompatActivity.RESULT_OK, output)
    }

    fun setOkResult(mContext: Context, resultCode: Int) {
        val output = Intent()
        (mContext as AppCompatActivity).setResult(resultCode, output)
    }

    fun startActivityFinish(mContext: Activity, cls: Class<*>) {
        val mainIntent = Intent(mContext, cls)
        mContext.startActivity(mainIntent)
        //  mContext.overridePendingTransition(R.anim.slide_for_in, R.anim.slide_for_out)

    }

}