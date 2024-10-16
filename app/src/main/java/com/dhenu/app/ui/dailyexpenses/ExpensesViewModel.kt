package com.dhenu.app.ui.dailyexpenses

import com.dhenu.app.data.ListData
import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.databinding.DialogAddExpensesBinding
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.ui.dailyexpenses.response.AddExpensesResponse
import com.dhenu.app.ui.dailyexpenses.response.ExpensesListResponse
import com.dhenu.app.util.NetworkResponseCallback
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpensesViewModel : BaseViewModel<ExpensesListNavigator>() {

    var businessmanData: ListData? = null

    fun villageListAPI(date: String) {

        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["date"] = date

        navigator!!.showProgress()

        disposable.add(
            ExpensesListResponse().doNetworkRequest(
                requestMap, AppPreference,
                object : NetworkResponseCallback<ExpensesListResponse> {

                    override fun onResponse(data: ExpensesListResponse) {
                        navigator!!.hideProgress()
                        navigator!!.expensesListResponse(data)
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

    fun checkValidation(viewDataBinding: DialogAddExpensesBinding): Boolean {

        if (viewDataBinding.editItemAmount.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया राशि दर्ज करें")
            viewDataBinding.editItemAmount.requestFocus()
            return false
        }
        if (viewDataBinding.editItemDescription.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया विवरण दर्ज करें")
            viewDataBinding.editItemDescription.requestFocus()
            return false
        }

        return true
    }

    private var isUpdate = false
    fun addVillageApi(viewDataBinding: DialogAddExpensesBinding?, expenseType: String) {

        navigator!!.showProgress()

        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["Type"] = expenseType
        requestMap["Amount"] = viewDataBinding?.editItemAmount?.text.toString()
        requestMap["Description"] = viewDataBinding?.editItemDescription?.text.toString()

        disposable.add(
            AddExpensesResponse().doNetworkRequest(requestMap, isUpdate,
                object : NetworkResponseCallback<AddExpensesResponse> {

                    override fun onResponse(data: AddExpensesResponse) {
                        navigator!!.hideProgress()
                        navigator!!.addExpensesResponse(data)
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