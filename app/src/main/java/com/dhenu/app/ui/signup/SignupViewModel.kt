package com.dhenu.app.ui.signup

import com.dhenu.app.ui.signup.response.SignUpResponse
import com.dhenu.app.R
import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.databinding.ActivitySignupBinding
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.NetworkResponseCallback

class SignupViewModel : BaseViewModel<SignupNavigator>() {
    var passwordStr: String = ""
    var confirmPasswordStr: String = ""
    private var requestParam: HashMap<String, Any>? = null
    var selectCode: String? = null

    fun onSignupClick() {
        navigator!!.onSignupClick()
    }

    fun onSigninClick() {
        navigator!!.onSigninClick()
    }

    fun checkSignUpValidation(viewDataBinding: ActivitySignupBinding, code: String?): Boolean {
        selectCode = code
        passwordStr = viewDataBinding.passwordEt.text.toString()
        confirmPasswordStr = viewDataBinding.confirmPasswordEt.text.toString()
        if (viewDataBinding.firstNameEt.text!!.trim().isEmpty()) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.please_enter_first_name))
            viewDataBinding.firstNameEt.requestFocus()
            return false
        }
        if (viewDataBinding.firstNameEt.text!!.trim().length < 2) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.fname_length))
            viewDataBinding.firstNameEt.requestFocus()
            return false
        }
        if (CommonUtils.checkName(viewDataBinding.firstNameEt.text!!.trim().toString())) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.special_char_not_allowed))
            viewDataBinding.firstNameEt.requestFocus()
            return false
        }

        if (viewDataBinding.lastNameEt.text!!.trim().isEmpty()) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.please_enter_last_name))
            viewDataBinding.firstNameEt.requestFocus()
            return false
        }
        if (viewDataBinding.lastNameEt.text!!.trim().length < 2) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.lname_length))
            viewDataBinding.firstNameEt.requestFocus()
            return false
        }
        if (CommonUtils.checkName(viewDataBinding.lastNameEt.text!!.trim().toString())) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.special_char_not_allowed))
            viewDataBinding.firstNameEt.requestFocus()
            return false
        }

        if (viewDataBinding.mobileNumberEt.text!!.trim().isEmpty()) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.please_enter_mobile_number))
            viewDataBinding.mobileNumberEt.requestFocus()
            return false
        }
        if (!CommonUtils.isPhoneNumberValid(
                viewDataBinding.mobileNumberEt.text!!.trim().toString()
            )
        ) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.mobile_number_length))
            viewDataBinding.mobileNumberEt.requestFocus()
            return false
        }
        if (viewDataBinding.emailEt.text!!.trim().isEmpty()) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.please_enter_email))
            viewDataBinding.emailEt.requestFocus()
            return false
        }
        if (!CommonUtils.isEmailValid(viewDataBinding.emailEt.text!!.trim().toString())) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.enter_correct_email))
            viewDataBinding.emailEt.requestFocus()
            return false
        }
        if (viewDataBinding.passwordEt.text!!.trim().isEmpty()) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.please_enter_password))
            viewDataBinding.passwordEt.requestFocus()
            return false
        }

        if ((viewDataBinding.passwordEt.text!!.toString().trim().length < 6)) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.password_length))
            viewDataBinding.passwordEt.requestFocus()
            return false
        }

//        if (!CommonUtils.checkPassword(viewDataBinding!!.passwordEt.text!!.trim().toString())) {
//            navigator!!.showValidationError(navigator!!.getStringResource(R.string.validate_password))
//            viewDataBinding.passwordEt.requestFocus()
//            return false
//        }

        if (viewDataBinding.confirmPasswordEt.text!!.trim().isEmpty()) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.please_enter_confirm_password))
            viewDataBinding.confirmPasswordEt.requestFocus()
            return false
        }
        if (confirmPasswordStr.trim() != passwordStr.trim()) {
            navigator!!.showValidationError(navigator!!.getStringResource(R.string.confirm_password_not_match))
            viewDataBinding.confirmPasswordEt.requestFocus()
            return false
        }
        return true
    }


    fun signUpAPI(
        viewDataBinding: ActivitySignupBinding?,
        currentLat: Double,
        currentLong: Double
    ) {

        navigator!!.showProgress()

        disposable.add(
            SignUpResponse().doNetworkRequest(
                prepareRequestHashMap(viewDataBinding, currentLat, currentLong), AppPreference,
                object : NetworkResponseCallback<SignUpResponse> {

                    override fun onResponse(data: SignUpResponse) {
                        navigator!!.hideProgress()
//                        if (data.isSuccess) {
//                            navigator!!.signupSuccess(data)
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
                        navigator!!.showAppUpdateError(msg)
                    }
                })
        )
    }


    /**
     * This method is used to send parameter on server.
     */
    private fun prepareRequestHashMap(
        viewDataBinding: ActivitySignupBinding?,
        currentLat: Double,
        currentLong: Double
    ): HashMap<String, Any> {
        requestParam = HashMap()
        requestParam!!["first_name"] = viewDataBinding?.firstNameEt?.text.toString().trim()
        requestParam!!["last_name"] = viewDataBinding?.lastNameEt?.text.toString().trim()
        //    requestParam!!["email"] = viewDataBinding?.emailEt?.text.toString().trim()
        requestParam!!["phone_code"] = "+" + selectCode.toString()
        requestParam!!["phone_number"] = viewDataBinding?.mobileNumberEt?.text.toString().trim()
        requestParam!!["password"] = viewDataBinding?.passwordEt?.text.toString().trim()
        requestParam!!["confirm_password"] =
            viewDataBinding?.confirmPasswordEt?.text.toString().trim()
        requestParam!!["latitude"] = currentLat.toString().trim()
        requestParam!!["longitude"] = currentLong.toString().trim()

        return requestParam!!
    }

}