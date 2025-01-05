package com.dhenu.app.ui.customer.addcustomer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.data.ListData
import com.dhenu.app.databinding.ActivityAddCustomerBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.customer.response.AddCustomerResponse
import com.dhenu.app.ui.village.VillageListActivity
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.enums.IntentKeys

class AddCustomerActivity : BaseActivity<ActivityAddCustomerBinding, AddCustomerViewModel>(),
    AddCustomerNavigator {

    override val bindingVariable: Int
        get() = BR.addCustomerVM

    override val layoutId: Int
        get() = R.layout.activity_add_customer

    override val viewModel = AddCustomerViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()


        if (intent != null && intent.hasExtra(IntentKeys.CUSTOMER_DATA.getKey())) {
            viewModel.customerData = intent.getParcelableExtra("CUSTOMER_DATA")
        }

        if (viewModel.customerData != null) {

            viewDataBinding!!.editName.setText(viewModel.customerData!!.Name)
            viewDataBinding!!.editMobileNumber.setText(viewModel.customerData!!.MobileNo)
            viewDataBinding!!.textVillageName.setText(viewModel.customerData!!.VillageName)
            viewDataBinding!!.textSave.text = "अपडेट"
            viewModel.villageData =
                ListData(viewModel.customerData!!.VillageName, viewModel.customerData!!.VillageId)
        }

        DataBinding.onSingleClick(viewDataBinding!!.textSave) {
            hideKeyboard()
            if (checkIfInternetOnDialog(tryAgainClick = {
                    if (viewModel.checkValidation(viewDataBinding!!)) {
                        viewModel.addVillageApi(viewDataBinding!!)
                    }
                })) if (viewModel.checkValidation(viewDataBinding!!)) {

                viewModel.addVillageApi(viewDataBinding!!)
            }
        }
        DataBinding.onSingleClick(viewDataBinding!!.textVillageName) {

            val intent = Intent(this, VillageListActivity::class.java)
            intent.putExtra(IntentKeys.COME_FROM.getKey(), "SELECT")
            selectVillageLauncher.launch(intent)

        }

    }

    private val selectVillageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.villageData = data!!.getParcelableExtra(IntentKeys.VILLAGE_DATA.getKey())
                viewDataBinding!!.textVillageName.setText(viewModel.villageData!!.Name)
            }
        }

    override fun addCustomerResponse(response: AddCustomerResponse) {

        CommonUtils.showMessage(response.message, this)
        val resultIntent = Intent()
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "कस्टमर जोड़ें"

    }


}