package com.dhenu.app.ui.exchange.exchangedetail

import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.ui.exchange.response.AddExchangeItemResponse
import com.dhenu.app.ui.exchange.response.CloseExchangeResponse
import com.dhenu.app.ui.exchange.response.ExchangeItemListResponse
import com.dhenu.app.ui.exchange.response.ExchangeListResponse.ExchangeData
import com.dhenu.app.ui.mortgage.response.MortgageListResponse.MortgageData
import com.dhenu.app.util.NetworkResponseCallback

class ExchangeDetailViewModel : BaseViewModel<ExchangeDetailNavigator>() {

    var exchangeData: ExchangeData? = null
    var mortgageData: MortgageData? = null

    fun getMortgageList(searchKey: String) {

        navigator!!.showProgress()

        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["ExchangeId"] = exchangeData?.ExchangeId.toString()

        disposable.add(
            ExchangeItemListResponse().doNetworkRequest(requestMap,
                AppPreference,
                object : NetworkResponseCallback<ExchangeItemListResponse> {

                    override fun onResponse(data: ExchangeItemListResponse) {
                        navigator!!.hideProgress()
                        navigator!!.exchangeItemsListResponse(data)
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


    fun addExchangeItem() {

        navigator!!.showProgress()

        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["ExchangeId"] = exchangeData?.ExchangeId.toString()
        requestMap["MortgageId"] = mortgageData?.Id.toString()

        disposable.add(
            AddExchangeItemResponse().doNetworkRequest(requestMap,
                AppPreference,
                object : NetworkResponseCallback<AddExchangeItemResponse> {

                    override fun onResponse(data: AddExchangeItemResponse) {
                        navigator!!.hideProgress()
                        navigator!!.addExchangeItemResponse(data)
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

    fun closeExchangeItem(exchangeItemId: String) {

        navigator!!.showProgress()

        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["ExchangeItemId"] = exchangeItemId

        disposable.add(
            CloseExchangeResponse().doNetworkRequest(requestMap,
                AppPreference,
                object : NetworkResponseCallback<CloseExchangeResponse> {

                    override fun onResponse(data: CloseExchangeResponse) {
                        navigator!!.hideProgress()
                        navigator!!.closeExchangeItemResponse(data)
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