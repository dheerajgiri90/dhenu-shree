package com.dhenu.app.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.ceil

object CommonUtils {

    private var myLocale: Locale? = null

    val READ_WRITE_EXTERNAL_STORAGE_AND_CAMERA =
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

    var ACCESS_LOCATION = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private var internetCallback: InternetCallback? = null
    private val dialog: Dialog? = null
    var mp: MediaPlayer? = null
    private lateinit var mAudioManager: AudioManager
    private var media_current_volume = 0
    private var media_max_volume = 0

    val currentTimeZone: String
        get() = TimeZone.getDefault().id.toString()

    fun validateEmail(strEmail: String): Boolean {

        val pattern: Pattern
        val emailPattern =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(emailPattern)
        val matcher: Matcher = pattern.matcher(strEmail)
        return matcher.matches()

    }

    fun getSpannedText(text: String): Spanned? {
        return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    }

    fun isEmailValid(strEmail: String): Boolean {

        val pattern: Pattern
        val emailPattern =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(emailPattern)
        val matcher: Matcher = pattern.matcher(strEmail)
        return matcher.matches()


    }

    fun isPhoneNumberValid(phoneNumber: String?): Boolean {

        if (phoneNumber == null) {
            return false
        } else {
            if (!Pattern.matches("[a-zA-Z]+", phoneNumber)) {
                return phoneNumber.length in 6..15
            }
        }
        return false
    }

    fun showMessage(message: String, context: Context) {

        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val toastView = toast.view
            toastView!!.background.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
            val toastMessage = toastView.findViewById<TextView>(android.R.id.message)
            toastMessage.setTextColor(Color.WHITE)
            toastMessage.gravity = Gravity.CENTER
        }
        toast.show()

    }

    fun makeFirstLetterUpperCase(string: String): String {
        if (string.isNotEmpty()) {
            return string.substring(0, 1).uppercase(Locale.getDefault()) + string.substring(1)
                .lowercase(Locale.getDefault())
        }
        return ""
    }

    val osVersion: Int
        get() = android.os.Build.VERSION.SDK_INT


    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else capitalize(manufacturer) + " " + model
        }

    fun setInernetCallback(internetCallback1: InternetCallback) {
        internetCallback = internetCallback1
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    @SuppressLint("all")
    fun getDeviceType(): String {
        return "android"
    }

    @SuppressLint("all")
    fun getCertificationType(): String {
        return "development"
    }

    fun wordFirstCap(str: String): String {
        var capStr = ""
        try {
            val words = str.trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val ret = StringBuilder()
            for (i in words.indices) {
                if (words[i].trim { it <= ' ' }.length > 0) {
                    Log.e("words[i].trim", "" + words[i].trim { it <= ' ' }[0])
                    ret.append(Character.toUpperCase(words[i].trim { it <= ' ' }[0]))
                    ret.append(words[i].trim { it <= ' ' }.substring(1))
                    if (i < words.size - 1) {
                        ret.append(' ')
                    }
                }
            }
            capStr = ret.toString()

        } catch (e: Exception) {
            e.printStackTrace()
            capStr = ""
        }

        return capStr
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        val manager = context.assets
        val `is` = manager.open(jsonFileName)

        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, Charsets.UTF_8)
    }

    fun isValidEmail(target: String?): Boolean {
        return if (target == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }


    fun checkPassword(password: String): Boolean {
        val PASSWORD_PATTERN = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")
        return try {
            PASSWORD_PATTERN.matcher(password).matches()
        } catch (exception: NullPointerException) {
            false
        }
    }

    fun checkName(name: String): Boolean {
        val regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")
        if (regex.matcher(name).find()) {
            Log.e(javaClass.name, "SPECIAL CHARS FOUND")
            return true
        }
        return false
    }

    fun calculatePageLimit(totalCount: Int, limit: Int): Double {
        try {
            val page = totalCount.toDouble() / limit
            return ceil(page)
        } catch (w: Exception) {
            w.printStackTrace()
        }
        return 0.0
    }

    fun checkMobile(mobile: String): Boolean {
        val MOBILE_NUMBER_PATTERN =
            Pattern.compile("^[0-9]{9,14}$")
        return try {
            MOBILE_NUMBER_PATTERN.matcher(mobile).matches()
        } catch (exception: NullPointerException) {
            false
        }
    }

    fun getPixelValue(dimenId: Int, context: Context): Int {
        val resources = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dimenId.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    fun getImageUri(inImage: Bitmap, context: Context): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }


    fun isInternetOn(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val networkInfo = connectivity.activeNetworkInfo
            return (networkInfo != null && networkInfo.isAvailable
                    && networkInfo.isConnected)
        }
        return false
    }


    fun getRealPathFromURI(activity: Activity, contentURI: Uri): String? {

        var result: String? = null
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            if (cursor.moveToFirst()) {
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(idx)
            }
            cursor.close()
        }
        return result
    }

    fun getFileImages(bmap: Bitmap, name: String): File {

        val image_file = File(Environment.getExternalStorageDirectory(), "$name.jpeg")
        val outStream: FileOutputStream
        try {

            outStream = FileOutputStream(image_file)
            val isComp = bmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return image_file
    }


    fun getDateInFormat(dateInput: String, dateOutput: String, _date: String): String {
        try {
            @SuppressLint("SimpleDateFormat") val inputFormat = SimpleDateFormat(dateInput)
            @SuppressLint("SimpleDateFormat") val outputFormat = SimpleDateFormat(dateOutput)
            val date = inputFormat.parse(_date)
            return outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }


    fun setUnderLineText(contentText: AppCompatTextView) {
        val content = SpannableString(contentText.text.toString())
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        contentText.text = content
    }


    fun startActivityAndKillAll(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        //(context as Activity).overridePendingTransition(R.anim.slide_for_in, R.anim.slide_for_out)
    }

    fun startActivityWithBundle(context: Context, cls: Class<*>, bn: Bundle) {
        val intent = Intent(context, cls)
        intent.putExtras(bn)
        context.startActivity(intent)
        // (context as Activity).overridePendingTransition(R.anim.slide_for_in, R.anim.slide_for_out)
    }

    fun isValidPhoneNumber(phoneNumber: String, countryCode: String): Boolean {
        if (isStringNullOrBlank(phoneNumber)) {
            return false
        }

        return true
    }


    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }

    /*** method for string validation ***/
    fun isStringNullOrBlank(str: String?): Boolean {

        try {
            if (str == null) {
                return true
            } else if (str.lowercase(Locale.getDefault()) == "null" || str == "" || str.isEmpty() || str.lowercase(
                    Locale.getDefault()
                )
                    .equals(
                        "null",
                        ignoreCase = true
                    )
            ) {
                return true
            }
        } catch (e: Exception) {
            Logger.e(AppConstants.LOG_CAT, e.message!!)
        }
        return false
    }

    fun showBadgeCount(str: String?): Boolean {
        try {
            if (str == null) {
                return true
            } else if (str.lowercase(Locale.getDefault()) == "null" || str == "" || str.isEmpty() || str.lowercase(
                    Locale.getDefault()
                )
                    .equals(
                        "null",
                        ignoreCase = true
                    )
            ) {
                return true
            } else if (str.toInt() <= 0) {
                return true
            }
        } catch (e: Exception) {
            Logger.e(AppConstants.LOG_CAT, e.message!!)
        }
        return false
    }

    fun toTitleCase(str: String?): String? {

        if (str == null) {
            return null
        }
        var space = true
        val builder: StringBuilder = StringBuilder(str)
        val len: Int = builder.length

        for (index in 0 until len) {
            val c: Char = builder[index]
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(index, Character.toTitleCase(c))
                    space = false
                }
            } else if (Character.isWhitespace(c)) {
                space = true
            } else {
                builder.setCharAt(index, Character.toLowerCase(c))
            }
        }
        return builder.toString()
    }

    fun getValueInPercentageFormate(value: String?): String {
        return "${value!!.substring(0, 2)}% Off"
    }


    fun firstLetterCapital(str: String): String {
        return if (!isStringNullOrBlank(str)) {
            str.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        } else {
            ""
        }

    }

    fun getTodayDate(): String {
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy")
        return df.format(c)
    }


    fun checkLocationPermission(mContext: Context): Boolean {
        val permissionCheck_fine = ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val permissionCheck_coarse = ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (permissionCheck_fine != PackageManager.PERMISSION_GRANTED || permissionCheck_coarse != PackageManager.PERMISSION_GRANTED) {

            return false
        }
        return true
    }


    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

}

