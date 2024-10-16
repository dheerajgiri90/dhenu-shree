package com.dhenu.app.util.enums

enum class IntentKeys(val value: String) {

    USER("USER"),
    LOGIN_TYPE("LOGIN_TYPE"),
    MOBILE_NUMBER("MOBILE_NUMBER"),
    OTP("OTP"),
    EMAIL_ID("EMAIL_ID"),
    COME_FROM("COME_FROM"),
    CUSTOMER_DATA("CUSTOMER_DATA"),
    VILLAGE_DATA("VILLAGE_DATA"),
    ITEM_DATA("ITEM_DATA"),
    MORTGAGE_DATA("MORTGAGE_DATA"),
    BUSINESS_DATA("MORTGAGE_DATA"),
    EXCHANGE_DATA("EXCHANGE_DATA"),
    FAQ("FAQ"),
    TERM_CONDITION("TERM_CONDITION"),
    ABOUT_US("ABOUT_US"),
    PRIVACY_POLICY("PRIVACY_POLICY"),

    BADGE_COUNT("BADGE_COUNT");

    fun getKey(): String {
        return value
    }


}