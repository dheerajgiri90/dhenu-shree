package com.dhenu.app.ui.base

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.dhenu.app.BuildConfig
import com.dhenu.app.R
import com.dhenu.app.util.CommonNavigator
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.DialogConstant
import com.dhenu.app.util.Logger
import io.reactivex.disposables.CompositeDisposable


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(),
    CommonNavigator {

    val REQUEST_CODE_FOR_PERMISSION_SETTING = 1110
    private var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    private val disposable = CompositeDisposable()
    var viewDataBinding: T? = null
    private var mViewModel: V? = null

    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            //activity!!.onFragmentAttached()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding!!.lifecycleOwner = this
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }


    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    override fun showProgress() {
        baseActivity!!.showProgress()
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()

    }

    fun showStatusBarWithCustomColor(white: Int) {
        baseActivity?.showStatusBarWithCustomColor(white)
    }


    override fun hideProgress() {
        baseActivity?.hideProgress()
    }

    override fun showNetworkError(error: String) {
        if (context != null) {
            DialogConstant.showAlertDialog(
                getStringResource(R.string.dialog_alert_heading), error, requireActivity(),
                null
            )
        }
    }

    override fun showServerError(error: String) {
        DialogConstant.showAlertDialog(
            getStringResource(R.string.dialog_alert_heading), error, requireActivity(),
            null
        )
    }

    override fun showAppUpdateError(error: String) {
        if (this.baseActivity != null || isAdded) {
            val appPackageName: String =
                requireActivity().packageName
            DialogConstant.showAlertDialog(
                getStringResource(R.string.dialog_alert_heading), error, requireActivity(),
                object : DialogConstant.OnConfirmedListener {
                    override fun onConfirmed() {

                        try {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                                )
                            )
                        } catch (anfe: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                                )
                            )
                        }
                    }
                }
            )
        } else {
            Logger.e("error showAppUpdateError", ">>>>>>>>>>>>>>")
        }
    }

    override fun showSessionExpireAlert(error: String) {
        baseActivity!!.showSessionExpireAlert(error)
    }


    fun checkIfInternetOnDialog(tryAgainClick: () -> Unit?): Boolean {
        return if (baseActivity != null) {
            if (CommonUtils.isInternetOn(baseActivity!!)) {
                true
            } else {
                DialogConstant.noInternetDialog(baseActivity!!, tryAgainClick = {
                    tryAgainClick.invoke()
                })
                false
            }
        } else {
            true
        }
    }

    override fun showValidationError(message: String) {
        DialogConstant.showAlertDialogNew(requireActivity(), message, message)
    }

    override fun getStringResource(id: Int): String {
        return baseActivity!!.resources.getString(id)
    }

    override fun getIntegerResource(id: Int): Int {
        return resources.getInteger(id)
    }

    override fun onBackClick() {

    }

    fun moveToApplicationSettingForLocation() {

        DialogConstant.showOkCancelAlertDialog(resources.getString(R.string.enable_location),
            resources.getString(R.string.allow_location_permission_setting),
            requireContext(),
            object : DialogConstant.OnOkCancelListener {
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
}
