package com.dhenu.app.ui.dailyexpenses

import com.dhenu.app.ui.dailyexpenses.response.AddExpensesResponse
import com.dhenu.app.ui.dailyexpenses.response.ExpensesListResponse
import com.dhenu.app.util.CommonNavigator


interface ExpensesListNavigator : CommonNavigator {

    fun expensesListResponse(response: ExpensesListResponse)

    fun addExpensesResponse(response: AddExpensesResponse)
}