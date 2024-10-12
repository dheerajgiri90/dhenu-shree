package com.dhenu.app.ui.mortgage.mortgageoption

import android.os.Bundle
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityMortgageOptionBinding
import com.dhenu.app.ui.base.BaseActivity

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

//        DataBinding.onSingleClick(viewDataBinding!!.mortgageOption) {
//            hideKeyboard()
//
//        }
    }

    override fun init() {
        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "!! श्री !!"
    }

}