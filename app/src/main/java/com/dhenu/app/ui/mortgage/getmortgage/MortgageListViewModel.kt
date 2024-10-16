package com.dhenu.app.ui.mortgage.getmortgage

import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.ui.customer.response.CustomerListResponse.CustomerData
import com.dhenu.app.ui.mortgage.response.MortgageListResponse
import com.dhenu.app.util.NetworkResponseCallback

class MortgageListViewModel : BaseViewModel<MortgageListNavigator>() {

    var customerData: CustomerData? = null
    var comeFrom: String? = ""

    fun getMortgageList(searchKey: String) {

        navigator!!.showProgress()
        val requestMap: HashMap<String, Any> = HashMap()

        if (comeFrom.equals("SELECT")) {
            requestMap["name"] = ""
            requestMap["villageid"] = ""
            requestMap["isclosed"] = false
            requestMap["isexchanged"] = false
            requestMap["mortgageid"] = ""
            requestMap["customerid"] = ""
        } else {
            requestMap["name"] = ""
            requestMap["villageid"] = customerData?.VillageId.toString()
            requestMap["isclosed"] = ""
            requestMap["isexchanged"] = ""
            requestMap["mortgageid"] = ""
            requestMap["customerid"] = customerData?.Id.toString()
        }
        disposable.add(
            MortgageListResponse().doNetworkRequest(requestMap,
                AppPreference,
                object : NetworkResponseCallback<MortgageListResponse> {

                    override fun onResponse(data: MortgageListResponse) {
                        navigator!!.hideProgress()
                        navigator!!.mortgageListResponse(data)
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