package com.dhenu.app.ui.entry

import android.content.Context
import android.os.Bundle
import android.view.View
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.FragmentEntryBinding
import com.dhenu.app.ui.base.BaseFragment
import com.dhenu.app.ui.businessman.BusinessManActivity
import com.dhenu.app.ui.customer.customerlist.CustomerListActivity
import com.dhenu.app.ui.items.ItemsListActivity
import com.dhenu.app.ui.village.VillageListActivity
import com.dhenu.app.util.ActivityNavigator
import com.dhenu.app.util.DataBinding

class EntryFragment : BaseFragment<FragmentEntryBinding, EntryViewModel>(), EntryNavigator {

    override val bindingVariable: Int
        get() = BR.entryViewModel

    override val layoutId: Int
        get() = R.layout.fragment_entry

    override val viewModel = EntryViewModel()

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

        DataBinding.onSingleClick(viewDataBinding!!.textAddVillage) {
            ActivityNavigator.startActivity(requireActivity(), VillageListActivity::class.java)
        }

        DataBinding.onSingleClick(viewDataBinding!!.textAddCustomer) {
            ActivityNavigator.startActivity(requireActivity(), CustomerListActivity::class.java)
        }

        DataBinding.onSingleClick(viewDataBinding!!.textAddBusinessman) {
            ActivityNavigator.startActivity(requireActivity(), BusinessManActivity::class.java)
        }

        DataBinding.onSingleClick(viewDataBinding!!.textAddItems) {
            ActivityNavigator.startActivity(requireActivity(), ItemsListActivity::class.java)
        }

    }

}