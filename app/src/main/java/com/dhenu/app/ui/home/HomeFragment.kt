package com.dhenu.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.FragmentHomeBinding
import com.dhenu.app.ui.base.BaseFragment
import com.dhenu.app.ui.mortgage.mortgageoption.MortgageOptionActivity
import com.dhenu.app.util.ActivityNavigator
import com.dhenu.app.util.DataBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {

    override val bindingVariable: Int
        get() = BR.homeViewModel

    override val layoutId: Int
        get() = R.layout.fragment_home

    override val viewModel = HomeViewModel()

    private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (mContext == null) mContext = context.applicationContext
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigator = this
        init()
        viewDataBinding!!.textMortgage.requestFocus()
    }

    override fun init() {

        DataBinding.onSingleClick(viewDataBinding!!.textMortgage) {
            ActivityNavigator.startActivity(requireActivity(), MortgageOptionActivity::class.java)
        }

    }

}