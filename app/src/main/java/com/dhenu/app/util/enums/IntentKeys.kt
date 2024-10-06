package com.dhenu.app.util.enums

enum class IntentKeys(val value: String) {

    USER("USER"),
    LOGIN_TYPE("LOGIN_TYPE"),
    MOBILE_NUMBER("MOBILE_NUMBER"),
    OTP("OTP"),
    EMAIL_ID("EMAIL_ID"),
    COME_FROM("COME_FROM"),
    COUNTRY_CODE("COUNTRY_CODE"),
    SELECTED_PAGE("SELECTED_PAGE"),
    FAQ("FAQ"),
    TERM_CONDITION("TERM_CONDITION"),
    ABOUT_US("ABOUT_US"),
    PRIVACY_POLICY("PRIVACY_POLICY"),

    BADGE_COUNT("BADGE_COUNT");

    fun getKey(): String {
        return value
    }


}