package com.dhenu.app.ui.exchange.exchangelist

import com.dhenu.app.data.ListData
import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.databinding.DialogAddExchangeBinding
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.ui.exchange.response.AddExchangeResponse
import com.dhenu.app.ui.exchange.response.ExchangeListResponse
import com.dhenu.app.util.NetworkResponseCallback
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExchangeListViewModel : BaseViewModel<ExchangeListNavigator>() {

    var businessmanData: ListData? = null

    fun villageListAPI(searchKey: String) {

        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["businessManid"] = businessmanData?.Id.toString()

        navigator!!.showProgress()


        disposable.add(
            ExchangeListResponse().doNetworkRequest(
                requestMap, AppPreference,
                object : NetworkResponseCallback<ExchangeListResponse> {

                    override fun onResponse(data: ExchangeListResponse) {
                        navigator!!.hideProgress()
                        navigator!!.exchangeListResponse(data)
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

    fun checkValidation(viewDataBinding: DialogAddExchangeBinding): Boolean {

        if (viewDataBinding.editItemAmount.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया राशि दर्ज करें")
            viewDataBinding.editItemAmount.requestFocus()
            return false
        }
        if (viewDataBinding.editInterestRate.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया ब्याज दर दर्ज करें")
            viewDataBinding.editInterestRate.requestFocus()
            return false
        }
        if (viewDataBinding.editThokNumber.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया थोक नंबर दर्ज करें")
            viewDataBinding.editThokNumber.requestFocus()
            return false
        }

        return true
    }

    private var isUpdate = false
    fun addVillageApi(viewDataBinding: DialogAddExchangeBinding?,exchangeDate:String) {

        navigator!!.showProgress()
        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["BusinessManId"] = businessmanData?.Id.toString()
        requestMap["Amount"] = viewDataBinding?.editItemAmount?.text.toString()
        requestMap["InterestRate"] = viewDataBinding?.editInterestRate?.text.toString()
        requestMap["ExchangeDate"] = exchangeDate
        requestMap["ManualId"] = viewDataBinding?.editThokNumber?.text.toString()

        disposable.add(
            AddExchangeResponse().doNetworkRequest(requestMap, isUpdate,
                object : NetworkResponseCallback<AddExchangeResponse> {

                    override fun onResponse(data: AddExchangeResponse) {
                        navigator!!.hideProgress()
                        navigator!!.addExchangeResponse(data)
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

}