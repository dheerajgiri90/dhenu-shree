package com.dhenu.app.ui.base

import android.app.Dialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dhenu.app.BuildConfig
import com.dhenu.app.R
import com.dhenu.app.util.ClearPreference
import com.dhenu.app.util.CommonNavigator
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.DialogConstant
import com.dhenu.app.util.ProgressBarApp
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    CommonNavigator {

    var viewDataBinding: T? = null
    private var mViewModel: V? = null
    private var permission: Array<String>? = null
    private var pDialog: ProgressDialog? = null

    private val disposable = CompositeDisposable()
    var broadcastReceiver: BroadcastReceiver? = null
    var alertDialog: Dialog? = null

    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    private val UPDATE_INTERVAL = 20 * 1000.toLong()
    val REQUEST_CODE_FOR_PERMISSION_SETTING = 1111
    val REQUEST_CODE_FOR_PERMISSION_SETTING2 = 2222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permission = null
        performDataBinding()
        viewDataBinding!!.lifecycleOwner = this

    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun checkIfInternetOnDialog(tryAgainClick: () -> Unit?): Boolean {
        return if (CommonUtils.isInternetOn(this)) {
            true
        } else {
            DialogConstant.noInternetDialog(this, tryAgainClick = {
                tryAgainClick.invoke()
            })
            false
        }
    }


    fun moveToApplicationSettingForLocation() {
        DialogConstant.showOkCancelAlertDialog(resources.getString(R.string.enable_location),
            resources.getString(R.string.allow_location_permission_setting),
            this, object : DialogConstant.OnOkCancelListener {
                override fun onClickOk() {

                    startActivityForResult(
                        Intent(
                            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                        ), REQUEST_CODE_FOR_PERMISSION_SETTING
                    )

                }

                override fun onClickCancel() {

                }
            })
    }

    fun moveToApplicationSettingForStorage() {

        DialogConstant.showOkCancelAlertDialog(resources.getString(R.string.dialog_alert_heading),
            resources.getString(R.string.allow_permission_setting),
            this, object : DialogConstant.OnOkCancelListener {
                override fun onClickOk() {
                    startActivityForResult(
                        Intent(
                            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                        ), REQUEST_CODE_FOR_PERMISSION_SETTING2
                    )
                }

                override fun onClickCancel() {

                }

            })
    }


    fun setStatusBarColor(color: Int) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//  set status text dark
        window.statusBarColor =
            ContextCompat.getColor(this, color)// set status background white
    }


    fun hideStatusBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
    }

    fun showStatusBarWithCustomColor(white: Int) {
        // if you remove this line status bar icons show in white color, otherwise in black
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = white
    }


//    private fun getToken() {
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//            // Get new FCM registration token
//            val token = task.result
//            if (token != null) {
//                AppPreference.addValue(PreferenceKeys.DEVICE_ID, token)
//                Logger.e(" Token:", token)
//            }
//
//        })
//    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()

    }

    override fun showProgress() {
        if (pDialog != null) {
            pDialog!!.dismiss()
        }
        pDialog = ProgressBarApp.init(this@BaseActivity, false, false)
        pDialog!!.show()
    }

    override fun hideProgress() {
        if (pDialog != null) pDialog!!.dismiss()
        if (pDialog != null) pDialog!!.cancel()
    }

    override fun showNetworkError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading),
            error,
            this@BaseActivity,
            null
        )
    }

    override fun showServerError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading),
            error,
            this,
            object : DialogConstant.OnConfirmedListener {
                override fun onConfirmed() {


                }
            }
        )
    }

    override fun showAppUpdateError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading),
            error,
            this,
            object : DialogConstant.OnConfirmedListener {
                override fun onConfirmed() {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (anfe: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                }
            }
        )
    }

    override fun showSessionExpireAlert(error: String) {
        DialogConstant.showAlertDialogSessionExpire(getStringResource(R.string.dialog_alert_heading),
            getString(R.string.unauthorized_access),
            this, object : DialogConstant.OnConfirmedListener {
                override fun onConfirmed() {

                    ClearPreference.clearDataLogout(this@BaseActivity)
                }
            })
    }

    override fun getStringResource(id: Int): String {
        return resources.getString(id)
    }

    override fun getIntegerResource(id: Int): Int {
        return resources.getInteger(id)
    }

    override fun showValidationError(message: String) {
        DialogConstant.showAlertDialogNew(this, message, message)
    }

    override fun onBackClick() {
        onBackPressed()
    }

    fun createDrawableFromView(context: Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay
            .getMetrics(displayMetrics)
        view.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(
            0, 0, displayMetrics.widthPixels,
            displayMetrics.heightPixels
        )
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    companion object {
        val REQUEST_CHECK_SETTINGS = 0x1
    }

}

