package com.dhenu.app.ui.login

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityLoginBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.login.response.LoginResponse
import com.dhenu.app.ui.navigation.NavigationActivity
import com.dhenu.app.ui.signup.SignupActivity
import com.dhenu.app.util.ActivityNavigator
import com.dhenu.app.util.DataBinding

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    override val bindingVariable: Int
        get() = BR.loginViewModel

    override val layoutId: Int
        get() = R.layout.activity_login

    override val viewModel = LoginViewModel()

    private var count: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //window.statusBarColor = ContextCompat.getColor(this@LoginActivity, R.color.white)
        viewModel.navigator = this
        init()
    }

    override fun init() {

        DataBinding.onSingleClick(viewDataBinding!!.loginBtnTxt) {
            hideKeyboard()

            if (checkIfInternetOnDialog(tryAgainClick = {
                    if (viewModel.checkEmailPassword(viewDataBinding!!)) {
                        viewModel.loginApi(viewDataBinding, count)
                    }
                }))
                if (viewModel.checkEmailPassword(viewDataBinding!!)) {
                    viewModel.loginApi(viewDataBinding, count)
                }

        }

    }

    override fun usedInstead() {

    }

    override fun onClickSignup() {
        ActivityNavigator.startActivity(this@LoginActivity, SignupActivity::class.java)
    }

    override fun onclickForgotPassword() {
        ActivityNavigator.startActivity(this@LoginActivity, NavigationActivity::class.java)
    }


    override fun loginSuccess(data: LoginResponse) {

        ActivityNavigator.startActivityFinish(
            this@LoginActivity,
            NavigationActivity::class.java
        )
        finish()

//        DialogConstant.showAlertDialog(
//            getStringResource(R.string.dialog_alert_heading), data.message, this,
//            object : DialogConstant.OnConfirmedListener {
//                override fun onConfirmed() {
//                }
//
//            }
//        )

    }


}