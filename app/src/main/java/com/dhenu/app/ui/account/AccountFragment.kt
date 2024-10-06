package com.dhenu.app.ui.account

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.core.app.NotificationManagerCompat
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.FragmentAccountBinding
import com.dhenu.app.ui.base.BaseFragment
import com.dhenu.app.util.ClearPreference
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.DialogConstant

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>(), AccountNavigator {

    override val bindingVariable: Int
        get() = BR.accountViewModel

    override val layoutId: Int
        get() = R.layout.fragment_account

    override val viewModel = AccountViewModel()

    private var mContext: Context? = null

    override fun onAttach(@NonNull context: Context) {
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

        DataBinding.onSingleClick(viewDataBinding!!.textViewLogout) {
            DialogConstant.showLogoutAlertDialog(resources.getString(R.string.logout),
                resources.getString(R.string.want_to_logout),
                requireActivity(),
                object : DialogConstant.OnOkCancelListener {
                    override fun onClickOk() {

                        NotificationManagerCompat.from(requireContext()).cancelAll()
                        ClearPreference.clearDataLogout(mContext!!)
//                        if (checkIfInternetOnDialog(tryAgainClick = {
//                                viewModel.logoutApi()
//                            }))
//                            viewModel.logoutApi()
                    }

                    override fun onClickCancel() {
                    }
                })
        }

    }

    override fun navigateToSignIn(message: String) {
        NotificationManagerCompat.from(requireContext()).cancelAll()
        ClearPreference.clearDataLogout(mContext!!)
    }

}