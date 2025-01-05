package com.dhenu.app.ui.exchange.searchexchange

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivitySearchExchangeBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.exchange.exchangedetail.ExchangeDetailActivity
import com.dhenu.app.ui.exchange.response.ExchangeListResponse.ExchangeData
import com.dhenu.app.ui.mortgage.response.MortgageListResponse
import com.dhenu.app.ui.mortgage.response.MortgageListResponse.MortgageData
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.enums.IntentKeys

class SearchExchangeActivity :
    BaseActivity<ActivitySearchExchangeBinding, SearchExchangeViewModel>(),
    SearchExchangeNavigator {

    override val bindingVariable: Int
        get() = BR.searchExchangeVM

    override val layoutId: Int
        get() = R.layout.activity_search_exchange

    override val viewModel = SearchExchangeViewModel()
    var mortgageData: MortgageData? = null
    var exchangeData: ExchangeData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        DataBinding.onSingleClick(viewDataBinding!!.textSearchMortgage) {
            hideKeyboard()
            if (checkIfInternetOnDialog(tryAgainClick = {
                    if (viewModel.checkValidation(viewDataBinding!!)) {
                        viewModel.getMortgageData(viewDataBinding!!)
                    }
                })) if (viewModel.checkValidation(viewDataBinding!!)) {

                viewModel.getMortgageData(viewDataBinding!!)
            }
        }

        DataBinding.onSingleClick(viewDataBinding!!.textFindExchange) {
            val resultIntent = Intent(this, ExchangeDetailActivity::class.java)
            resultIntent.putExtra(IntentKeys.EXCHANGE_DATA.getKey(), exchangeData)
            startActivity(resultIntent)
            finish()
        }

    }

    override fun searchExchangeResponse(response: MortgageListResponse) {

        viewDataBinding!!.textFindExchange.visibility = View.GONE
        viewDataBinding!!.layoutMortgageData.visibility = View.GONE

        //CommonUtils.showMessage(response.message, this)
        if (response.data.size > 0) {
            mortgageData = response.data[0]

            if (mortgageData!!.IsExchanged) {

                exchangeData = ExchangeData(0.0, mortgageData?.BusinessmanName ?: "",
                    "", mortgageData?.ExchangeId ?: 0,
                    0.0,mortgageData?.ManualId?:"")

                viewDataBinding!!.textFindExchange.visibility = View.VISIBLE
                viewDataBinding!!.layoutMortgageData.visibility = View.VISIBLE

                viewDataBinding!!.textBusinessman.text =
                    "व्यापारी का नाम: " + mortgageData?.BusinessmanName

                viewDataBinding!!.textItemName.text = "आइटम: " + mortgageData?.ItemName
                viewDataBinding!!.textWeight.text = "वज़न: " + mortgageData?.Weight.toString()

                viewDataBinding!!.textCustomerName.text =
                    "ग्राहक: " + mortgageData?.Name

                viewDataBinding!!.textMortgageDate.text = "दिनांक: " + mortgageData?.MortgageDate

                viewDataBinding!!.textItemAmount.text = "अमाउंट: ₹ ${mortgageData?.Amount}"
                viewDataBinding!!.textEndDate.text = "अंतिम तिथि: " + mortgageData?.EndDateDetail

                viewDataBinding!!.textVillageName.text = "गाँव: " + mortgageData?.VillageName
                viewDataBinding!!.textMobileNumber.text = "मोबाइल: " + mortgageData?.MobileNo


            } else {
                showValidationError("यह टोकन आइटम अदला-बदली के लिए उपलब्ध नहीं है")
            }
        } else {
            showValidationError("सर्च आइटम नहीं मिला कृपया टोकन आईडी जांचें और पुनः प्रयास करें")
        }
    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "कस्टमर जोड़ें"

    }


}