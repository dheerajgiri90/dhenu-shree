package com.dhenu.app.ui.customer.addcustomer

import com.dhenu.app.data.ListData
import com.dhenu.app.databinding.ActivityAddCustomerBinding
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.ui.customer.response.AddCustomerResponse
import com.dhenu.app.ui.customer.response.CustomerListResponse.CustomerData
import com.dhenu.app.util.NetworkResponseCallback

class AddCustomerViewModel : BaseViewModel<AddCustomerNavigator>() {

    var villageData: ListData? = null
    var customerData: CustomerData? = null


    fun checkValidation(viewDataBinding: ActivityAddCustomerBinding): Boolean {
        if (viewDataBinding.editName.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया नाम दर्ज करें")
            viewDataBinding.editName.requestFocus()
            return false
        }
        if (viewDataBinding.editMobileNumber.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया मोबाइल नंबर दर्ज करें")
            viewDataBinding.editMobileNumber.requestFocus()
            return false
        }

        if (villageData == null) {
            navigator!!.showValidationError("कृपया गांव का चयन करें")
            viewDataBinding.editName.requestFocus()
            return false
        }

        return true
    }

    private var isUpdate = false
    fun addVillageApi(viewDataBinding: ActivityAddCustomerBinding?) {

        navigator!!.showProgress()
        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["Name"] = viewDataBinding?.editName?.text.toString()
        requestMap["NameH"] = viewDataBinding?.editName?.text.toString()
        requestMap["Address"] = villageData?.Name.toString()
        requestMap["MobileNo"] = viewDataBinding?.editMobileNumber?.text.toString()
        requestMap["Photo"] = ""
        requestMap["VillageId"] = villageData?.Id.toString()

        if (customerData != null) {
            requestMap["Id"] = customerData?.Id.toString()
            isUpdate = true
        } else {
            isUpdate = false
        }

        disposable.add(
            AddCustomerResponse().doNetworkRequest(requestMap, isUpdate,
                object : NetworkResponseCallback<AddCustomerResponse> {

                    override fun onResponse(data: AddCustomerResponse) {
                        navigator!!.hideProgress()
                        navigator!!.addCustomerResponse(data)

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