package com.dhenu.app.util

import android.Manifest

object AppConstants {

    val INACTIVE = "inactive"
    val LOG_CAT = "app_log"
    val STATUS: String = "status"
    val EMPTY = ""
    val ALERT = "Alert"
    val CONFIRMATION = "Confirmation"
    val DEVICE_TYPE = "android"
    val CERTIFICATE_TYPE = "dev"
    val ROLE_CUSTOMER = "customer"
    val FAQ = "Frequently Ask Questions(FAQ's)"
    val TERM_N_CONDITION = "Terms & Conditions"
    val ABOUT_US = "About the Company"
    val PRIVACY_POLICY = "Privacy Policy"

    val PHONE_NUMBER: String = "phone_number"
    val CONTRY_CODE: String = "country_code"

    val CHANNEL_ID = "AppChannelId"

    val EXCEPTION_MSG = "Some Exception while printing log :->"

    const val GOOGLE_API_KEY = ""

    val INTERNAL_SERVER_ERROR = "Internal Server Error"
    val GPS_STATUS_REQUEST_CODE = 1000
    val INPUT_DATE = "yyyy-MM-dd HH:mm:ss"
    val OUTPUT_DATE_FORMAT_WITH_YEAR_24HOUR = "dd MMM yyyy HH:mm"
    val OUTPUT_DATE_FORMAT_WITH_YEAR_12HOUR = "dd MMM yyyy hh:mm a"
    val CAMERA_REQUEST = 2
    val GALLERY_IMAGE_REQUEST = 1
    var IMAGE_DIRECTORY_NAME = "app-name"
    val MEDIA_FILE = "profile_image"

    val MEDIA_PERMISSION = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    val READ_LOCATION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val camera_permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

}
