package com.dhenu.app.ui.exchange.searchexchange

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityAddCustomerBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.mortgage.response.MortgageListResponse
import com.dhenu.app.ui.village.VillageListActivity
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.enums.IntentKeys

class SearchExchangeActivity : BaseActivity<ActivityAddCustomerBinding, SearchExchangeViewModel>(),
    SearchExchangeNavigator {

    override val bindingVariable: Int
        get() = BR.addCustomerVM

    override val layoutId: Int
        get() = R.layout.activity_add_customer

    override val viewModel = SearchExchangeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        DataBinding.onSingleClick(viewDataBinding!!.textSave) {
            hideKeyboard()
            if (checkIfInternetOnDialog(tryAgainClick = {
                    if (viewModel.checkValidation(viewDataBinding!!)) {
                        viewModel.getMortgageList("")
                    }
                })) if (viewModel.checkValidation(viewDataBinding!!)) {

                viewModel.getMortgageList("")
            }
        }

    }


    override fun searchExchangeResponse(response: MortgageListResponse) {
        CommonUtils.showMessage(response.message, this)

    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "कस्टमर जोड़ें"

    }


}