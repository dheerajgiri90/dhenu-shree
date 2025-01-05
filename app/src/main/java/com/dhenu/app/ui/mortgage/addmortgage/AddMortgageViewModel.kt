package com.dhenu.app.ui.mortgage.addmortgage

import com.dhenu.app.data.ListData
import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.databinding.ActivityAddMortgageBinding
import com.dhenu.app.ui.base.BaseViewModel
import com.dhenu.app.ui.customer.response.CustomerListResponse.CustomerData
import com.dhenu.app.ui.mortgage.response.AddMortgageResponse
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.NetworkResponseCallback
import java.text.SimpleDateFormat
import java.util.Locale

class AddMortgageViewModel : BaseViewModel<AddMortgageNavigator>() {

    var villageData: ListData? = null
    var customerData: CustomerData? = null
    var itemData: ListData? = null
    var mortgageDate: String = ""
    var day = ""
    var month = ""
    var year = ""

    fun checkValidation(viewDataBinding: ActivityAddMortgageBinding): Boolean {
        if (validateInputs(day, month, year)) {
            val fullDate = "$year-$month-$day"
            if (isValidDate(fullDate)) {
                mortgageDate =
                    CommonUtils.getDateInFormat("yyyy-MM-dd", "dd MMM yyyy HH:mm", fullDate)
            } else {
                navigator!!.showValidationError("Invalid date format")
                return false
            }
        } else {
            navigator!!.showValidationError("Please fill all fields correctly.")
            return false
        }

        if (villageData == null) {
            navigator!!.showValidationError("कृपया गांव का चयन करें")
            return false
        }
        if (customerData == null) {
            navigator!!.showValidationError("कृपया कस्टमर का चयन करें")
            return false
        }
        if (itemData == null) {
            navigator!!.showValidationError("कृपया आइटम का चयन करें")
            return false
        }

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
        if (viewDataBinding.editItemWeight.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया आइटम का वज़न दर्ज करें")
            viewDataBinding.editItemWeight.requestFocus()
            return false
        }
        return true
    }

    private fun validateInputs(day: String, month: String, year: String): Boolean {
        if (day.isEmpty() || month.isEmpty() || year.isEmpty()) return false
        val dayInt = day.toIntOrNull() ?: return false
        val monthInt = month.toIntOrNull() ?: return false
        val yearInt = year.toIntOrNull() ?: return false

        return dayInt in 1..31 && monthInt in 1..12 && yearInt > 0
    }

    private fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(date) != null
        } catch (e: Exception) {
            false
        }
    }


    fun addMortgageApi(viewDataBinding: ActivityAddMortgageBinding?) {

        navigator!!.showProgress()

        val requestList: MutableList<HashMap<String, Any>> = mutableListOf()
        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["CustomerId"] = customerData?.Id.toString()
        requestMap["CustomerName"] = customerData?.Name.toString()
        requestMap["VillageName"] = villageData?.Name.toString()
        requestMap["ItemName"] = itemData?.Name.toString()
        requestMap["MortgageDate"] = mortgageDate
        requestMap["Amount"] = viewDataBinding?.editItemAmount?.text.toString()
        requestMap["InterestRate"] = viewDataBinding?.editInterestRate?.text.toString()
        requestMap["Weight"] = viewDataBinding?.editItemWeight?.text.toString()
        requestMap["Description"] = viewDataBinding?.editItemDescription?.text.toString()
        requestMap["Photo"] = ""
        requestMap["EndDateDetail"] = viewDataBinding?.editEndDate?.text.toString()

        requestList.add(requestMap)

        disposable.add(
            AddMortgageResponse().doNetworkRequestNew(requestList, AppPreference,
                object : NetworkResponseCallback<AddMortgageResponse> {

                    override fun onResponse(data: AddMortgageResponse) {
                        navigator!!.hideProgress()
                        navigator!!.addMortgageResponse(data)
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