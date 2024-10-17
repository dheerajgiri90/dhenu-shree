package com.dhenu.app.ui.mortgage.mortgageoption

import android.os.Bundle
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityMortgageOptionBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.businessman.BusinessManActivity
import com.dhenu.app.ui.customer.customerlist.CustomerListActivity
import com.dhenu.app.ui.dailyexpenses.ExpensesListActivity
import com.dhenu.app.ui.exchange.searchexchange.SearchExchangeActivity
import com.dhenu.app.ui.items.ItemsListActivity
import com.dhenu.app.ui.mortgage.addmortgage.AddMortgageActivity
import com.dhenu.app.util.ActivityNavigator
import com.dhenu.app.util.DataBinding

class MortgageOptionActivity :
    BaseActivity<ActivityMortgageOptionBinding, MortgageOptionViewModel>(),
    MortgageOptionNavigator {

    override val bindingVariable: Int
        get() = BR.mortgageOptionViewModel

    override val layoutId: Int
        get() = R.layout.activity_mortgage_option

    override val viewModel = MortgageOptionViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        DataBinding.onSingleClick(viewDataBinding!!.textAddMortgage) {
            ActivityNavigator.startActivity(this, AddMortgageActivity::class.java)
        }

        DataBinding.onSingleClick(viewDataBinding!!.textMortgageList) {
            ActivityNavigator.startActivity(this, CustomerListActivity::class.java)
        }
        DataBinding.onSingleClick(viewDataBinding!!.textDailyExpenses) {
            ActivityNavigator.startActivity(this, ExpensesListActivity::class.java)
        }
        DataBinding.onSingleClick(viewDataBinding!!.textExchangeItem) {
            ActivityNavigator.startActivity(this, SearchExchangeActivity::class.java)
        }
        DataBinding.onSingleClick(viewDataBinding!!.textAddCustomer) {
            ActivityNavigator.startActivity(this, CustomerListActivity::class.java)
        }
        DataBinding.onSingleClick(viewDataBinding!!.textAddItems) {
            ActivityNavigator.startActivity(this, ItemsListActivity::class.java)
        }
        DataBinding.onSingleClick(viewDataBinding!!.textAddBusinessman) {
            ActivityNavigator.startActivity(this, BusinessManActivity::class.java)
        }
        DataBinding.onSingleClick(viewDataBinding!!.textBalanceSheet) {
            //ActivityNavigator.startActivity(this, ItemsListActivity::class.java)
        }
    }

    override fun init() {
        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "!! श्री !!"
    }

}