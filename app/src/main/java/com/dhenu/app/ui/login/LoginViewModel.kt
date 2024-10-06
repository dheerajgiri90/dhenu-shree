package com.dhenu.app.ui.login

import com.dhenu.app.R
import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.data.local.PreferenceKeys
import com.dhenu.app.databinding.ActivityLoginBinding
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.ui.login.response.LoginResponse
import com.dhenu.app.util.NetworkResponseCallback
import com.google.gson.Gson

class LoginViewModel : BaseViewModel<LoginNavigator>() {

    var isEmail: Boolean = false
    var selectCode: String? = null

    fun usedInstead() {
        navigator!!.usedInstead()
    }

    fun onClickSignup() {
        navigator!!.onClickSignup()
    }

    fun onclickForgotPassword() {
        navigator!!.onclickForgotPassword()
    }


    /**
     * This method is used to apply validation on fields.
     */
    fun checkEmailPassword(viewDataBinding: ActivityLoginBinding): Boolean {
        isEmail = true
        if (viewDataBinding.emailEt.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("Please enter user name")
            viewDataBinding.emailEt.requestFocus()
            //viewDataBinding!!.emailLayout.error=navigator!!.getStringResource(R.string.please_enter_email)
            return false
        }
//        if (!CommonUtils.isEmailValid(viewDataBinding.emailEt.text!!.trim().toString())) {
//            navigator!!.showValidationError(navigator!!.getStringResource(R.string.enter_correct_email))
//            viewDataBinding.emailEt.requestFocus()
//            // viewDataBinding!!.emailLayout.error=navigator!!.getStringResource(R.string.enter_correct_email)
//            return false
//        }
        if (viewDataBinding.passwordEt.text!!.trim().isEmpty()) {
            // viewDataBinding!!.emailLayout.error=null
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.please_enter_password))
            viewDataBinding.passwordEt.requestFocus()
            //  viewDataBinding!!.passwordEtLayout.error=navigator!!.getStringResource(R.string.please_enter_password)
            return false
        }
//        if ((viewDataBinding.passwordEt.text!!.toString()
//                .trim().length < 6) || (viewDataBinding.passwordEt.text!!.toString()
//                .trim().length > 12)
//        ) {
//            navigator!!.showValidationError(navigator!!.getStringResource(R.string.password_length))
//            viewDataBinding.passwordEt.requestFocus()
//            // viewDataBinding!!.passwordEtLayout.error=navigator!!.getStringResource(R.string.password_length)
//            return false
//        }
//        if (!CommonUtils.checkPassword(viewDataBinding.passwordEt.text!!.trim().toString())) {
//            navigator!!.showValidationError(navigator!!.getStringResource(R.string.validate_password))
//            viewDataBinding.passwordEt.requestFocus()
//            // viewDataBinding!!.passwordEtLayout.error=navigator!!.getStringResource(R.string.validate_password)
//            return false
//        }

        return true
    }

    fun loginApi(viewDataBinding: ActivityLoginBinding?, count: Int) {
        navigator!!.showProgress()

        disposable.add(
            LoginResponse().doNetworkRequest(
                requestParam(viewDataBinding, count), AppPreference,
                object : NetworkResponseCallback<LoginResponse> {

                    override fun onResponse(data: LoginResponse) {
                        navigator!!.hideProgress()
                        println(">>>>>>>> $data")
                        // if (data.isSuccess) {

                        val userString = Gson().toJson(data.authToken)
                        AppPreference.addValue(PreferenceKeys.USER_DATA, userString)
                        AppPreference.addValue(
                            PreferenceKeys.ACCESS_TOKEN,
                            data.authToken
                        )
                        navigator!!.loginSuccess(data)


//                        } else {
//                            navigator!!.hideProgress()
//                        }
                    }

                    override fun onFailure(message: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(message)
                    }

                    override fun onServerError(error: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(error)
                    }

                    override fun onErrorMessage(errorMessage: String) {
                        navigator!!.hideProgress()
                        super.onErrorMessage(errorMessage)
                        navigator!!.showNetworkError(errorMessage, false)
                    }

                    override fun onSessionExpire(error: String) {
                        navigator!!.hideProgress()
                        navigator!!.showSessionExpireAlert(error)
                    }

                    override fun onAppVersionUpdate(msg: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(msg)
                    }

                })
        )
    }

    private fun requestParam(
        viewDataBinding: ActivityLoginBinding?,
        count: Int
    ): HashMap<String, Any> {

        val requestMap: HashMap<String, Any> = HashMap()

        requestMap["UserName"] = viewDataBinding?.emailEt?.text.toString()
        requestMap["Password"] = viewDataBinding?.passwordEt?.text.toString()
//        requestMap["device_id"] = AppPreference.getValue(PreferenceKeys.DEVICE_ID).toString()
//        requestMap["device_type"] = AppConstants.DEVICE_TYPE
//        requestMap["certification_type"] = AppConstants.CERTIFICATE_TYPE
        return requestMap
    }


}