package com.dhenu.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.FragmentHomeBinding
import com.dhenu.app.ui.base.BaseFragment

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
    }

    override fun init() {

//        DataBinding.onSingleClick(viewDataBinding!!.textSearchLocation) {
//            ActivityNavigator.startActivity(requireActivity(), LoginActivity::class.java)
//        }

    }

}