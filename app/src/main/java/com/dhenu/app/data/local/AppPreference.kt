package com.dhenu.app.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.dhenu.app.util.AppConstants.EMPTY
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object AppPreference {

    private const val prefName = "app_pref"
    var sharedPreferences: SharedPreferences? = null

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC

    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    fun getInstance(context: Context) {
        sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            context.deleteSharedPreferences("secret_shared_prefs")
            throw e // Optionally rethrow or handle it appropriately
        }
    }

    fun addValue(preferencesKey: PreferenceKeys, value: String) {
        val editor = sharedPreferences!!.edit()
        editor.putString(preferencesKey.toString(), value)
        editor.apply()
    }

    fun addBoolean(preferencesKey: PreferenceKeys, value: Boolean) {
        val editor = sharedPreferences!!.edit()
        editor.putBoolean(preferencesKey.toString(), value)
        editor.apply()
    }

    fun addInt(preferencesKey: PreferenceKeys, value: Int) {
        val editor = sharedPreferences!!.edit()
        editor.putInt(preferencesKey.toString(), value)
        editor.apply()
    }

    fun getValue(key: PreferenceKeys): String? {
        return sharedPreferences!!.getString(key.toString(), EMPTY)
    }

    fun getInt(key: PreferenceKeys): Int {
        return sharedPreferences!!.getInt(key.toString(), 0)
    }

    fun getBoolean(key: PreferenceKeys): Boolean {
        return sharedPreferences!!.getBoolean(key.toString(), false)
    }

    fun clearSharedPreference() {
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }


    open fun putByGSON(preferencesKey: PreferenceKeys?, `object`: Any?) {
        val editor = sharedPreferences!!.edit()
        val gson = Gson()
        val json = gson.toJson(`object`)
        editor.putString(preferencesKey.toString(), json)
        editor.commit()
        editor.apply()

    }


    fun getArrayList(key: PreferenceKeys?): ArrayList<Any>? {
        val gson = Gson()
        val json: String? = sharedPreferences!!.getString(key.toString(), null)
        val type = object : TypeToken<ArrayList<Any?>?>() {}.type
        return gson.fromJson<ArrayList<Any>>(json, type)
    }

    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        sharedPreferences!!.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = sharedPreferences!!.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type “T” is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

}
