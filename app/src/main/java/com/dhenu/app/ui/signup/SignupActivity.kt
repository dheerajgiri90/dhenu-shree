package com.dhenu.app.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivitySignupBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.login.LoginActivity
import com.dhenu.app.ui.signup.response.SignUpResponse
import com.dhenu.app.util.applyClickShrink
import com.dhenu.app.util.enums.IntentKeys

class SignupActivity : BaseActivity<ActivitySignupBinding, SignupViewModel>(), SignupNavigator {

    var selectCode: String? = "1"

    var mCurrentLatitude: Double = 0.0
    var mCurrentLongitude: Double = 0.0

    override val bindingVariable: Int
        get() = BR.signupViewModel

    override val layoutId: Int
        get() = R.layout.activity_signup

    override val viewModel = SignupViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this@SignupActivity, R.color.transparent)
        viewModel.navigator = this

        init()
    }

    override fun init() {

        viewDataBinding!!.signUpBtnTxt.applyClickShrink()

        viewDataBinding!!.spinnerLogin.text = "+" + selectCode.toString()
        viewDataBinding!!.spinnerLogin.setOnClickListener {

//            val intent = Intent(this, CountryStateCityListActivity::class.java)
//            intent.putExtra(AppConstants.REDIRECTED_FROM, "phoneCode")
//            intent.putExtra("phoneCode", selectCode?.toInt())
//            startActivityForResult(intent, 400)
        }

    }

    override fun onSignupClick() {

        if (checkIfInternetOnDialog(tryAgainClick = {
                if (viewModel.checkSignUpValidation(viewDataBinding!!, selectCode)) {
                    viewModel.signUpAPI(viewDataBinding, mCurrentLatitude, mCurrentLongitude)
                }
            }))
            if (viewModel.checkSignUpValidation(viewDataBinding!!, selectCode)) {
                viewModel.signUpAPI(viewDataBinding, mCurrentLatitude, mCurrentLongitude)
            }
    }

    override fun onSigninClick() {
        if (intent.getStringExtra(IntentKeys.COME_FROM.getKey()).equals("LAUNCH")) {

            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
            supportFinishAfterTransition()

        } else {
            supportFinishAfterTransition()
        }

    }

    override fun signupSuccess(data: SignUpResponse) {

//        val intent = Intent(this@SignupActivity, VerificationCodeActivity::class.java)
//        intent.putExtra(IntentKeys.COUNTRY_CODE.getKey(), selectCode.toString())
//        intent.putExtra(
//            IntentKeys.MOBILE_NUMBER.getKey(),

    }

}