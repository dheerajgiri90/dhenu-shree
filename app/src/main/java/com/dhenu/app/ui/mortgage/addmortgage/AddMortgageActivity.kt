package com.dhenu.app.ui.mortgage.addmortgage

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityAddMortgageBinding
import com.dhenu.app.databinding.DialogMortgageSuccessBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.customer.customerlist.CustomerListActivity
import com.dhenu.app.ui.items.ItemsListActivity
import com.dhenu.app.ui.mortgage.response.AddMortgageResponse
import com.dhenu.app.ui.village.VillageListActivity
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.enums.IntentKeys
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

class AddMortgageActivity : BaseActivity<ActivityAddMortgageBinding, AddMortgageViewModel>(),
    AddMortgageNavigator {

    override val bindingVariable: Int
        get() = BR.addMortgageVM

    override val layoutId: Int
        get() = R.layout.activity_add_mortgage

    override val viewModel = AddMortgageViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)
        viewDataBinding!!.textTodayDay.text = "तारीख: $formattedDate"

        DataBinding.onSingleClick(viewDataBinding!!.textSave) {
            hideKeyboard()
            if (checkIfInternetOnDialog(tryAgainClick = {
                    if (viewModel.checkValidation(viewDataBinding!!)) {
                        viewModel.addMortgageApi(viewDataBinding!!)
                    }
                })) if (viewModel.checkValidation(viewDataBinding!!)) {

                viewModel.addMortgageApi(viewDataBinding!!)
            }
        }
        DataBinding.onSingleClick(viewDataBinding!!.textSelectVillage) {

            val intent = Intent(this, VillageListActivity::class.java)
            intent.putExtra(IntentKeys.COME_FROM.getKey(), "SELECT")
            selectVillageLauncher.launch(intent)

        }
        DataBinding.onSingleClick(viewDataBinding!!.textSelectCustomer) {
            if (viewModel.villageData == null) {
                showValidationError("सबसे पहले गांव का नाम चुनें")
            } else {
                val intent = Intent(this, CustomerListActivity::class.java)
                intent.putExtra(IntentKeys.VILLAGE_DATA.getKey(), viewModel.villageData)
                intent.putExtra(IntentKeys.COME_FROM.getKey(), "SELECT")
                selectCustomerLauncher.launch(intent)
            }
        }
        DataBinding.onSingleClick(viewDataBinding!!.textSelectItem) {

            val intent = Intent(this, ItemsListActivity::class.java)
            intent.putExtra(IntentKeys.COME_FROM.getKey(), "SELECT")
            selectItemLauncher.launch(intent)

        }

    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "गिरवी रखना"

    }

    private val selectVillageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.villageData = data!!.getParcelableExtra(IntentKeys.VILLAGE_DATA.getKey())
                viewDataBinding!!.textSelectVillage.setText(viewModel.villageData!!.Name)
            }
        }

    private val selectCustomerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.customerData =
                    data!!.getParcelableExtra(IntentKeys.CUSTOMER_DATA.getKey())
                viewDataBinding!!.textSelectCustomer.text = viewModel.customerData!!.Name
            }
        }

    private val selectItemLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.itemData = data!!.getParcelableExtra(IntentKeys.ITEM_DATA.getKey())
                viewDataBinding!!.textSelectItem.setText(viewModel.itemData!!.Name)
            }
        }

    override fun addMortgageResponse(response: AddMortgageResponse) {
        showSuccessDialog(response)
    }

    private fun showSuccessDialog(data: AddMortgageResponse?) {

        val alertDialog = Dialog(this)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(alertDialog.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding = DialogMortgageSuccessBinding.inflate(layoutInflater)
        alertDialog.setContentView(binding.root)
        alertDialog.setCancelable(false)
        val window = alertDialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val margin = 150
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        window?.setLayout(
            screenWidth - 2 * margin, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)

        if (data != null) {
            binding.textTokenNumber.text = "टोकन नंबर: ${data.tokenNumber}"
        }

        binding.imageDialogClose.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.textClose.setOnClickListener {
            finish()
        }

        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }


}