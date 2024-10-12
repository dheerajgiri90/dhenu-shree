package com.dhenu.app.ui.items

import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.databinding.DialogAddVillageBinding
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.ui.items.response.AddItemsResponse
import com.dhenu.app.ui.items.response.ItemsListResponse
import com.dhenu.app.util.NetworkResponseCallback

class ItemsListViewModel : BaseViewModel<ItemsListNavigator>() {

    fun villageListAPI(searchKey: String) {

        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["name"] = searchKey

        if (searchKey.isEmpty()) {
            navigator!!.showProgress()
        }

        disposable.add(
            ItemsListResponse().doNetworkRequest(
                requestMap, AppPreference,
                object : NetworkResponseCallback<ItemsListResponse> {

                    override fun onResponse(data: ItemsListResponse) {
                        navigator!!.hideProgress()
                        navigator!!.itemListResponse(data)
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


    fun checkValidation(viewDataBinding: DialogAddVillageBinding): Boolean {
        if (viewDataBinding.editName.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया नाम दर्ज करें")
            viewDataBinding.editName.requestFocus()
            return false
        }

        return true
    }

    private var isUpdate = false
    fun addVillageApi(viewDataBinding: DialogAddVillageBinding?, villageId: String?) {

        navigator!!.showProgress()
        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["Name"] = viewDataBinding?.editName?.text.toString()
        if (villageId != null) {
            requestMap["Id"] = villageId
            isUpdate = true
        } else {
            isUpdate = false
        }

        disposable.add(
            AddItemsResponse().doNetworkRequest(requestMap, isUpdate,
                object : NetworkResponseCallback<AddItemsResponse> {

                    override fun onResponse(data: AddItemsResponse) {
                        navigator!!.hideProgress()
                        navigator!!.addItemsResponse(data)

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