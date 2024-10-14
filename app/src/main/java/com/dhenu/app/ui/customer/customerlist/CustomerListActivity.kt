package com.dhenu.app.ui.customer.customerlist

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityCustomerListBinding
import com.dhenu.app.databinding.DialogAddVillageBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.customer.adapter.CustomerListAdapter
import com.dhenu.app.ui.customer.addcustomer.AddCustomerActivity
import com.dhenu.app.ui.customer.response.CustomerListResponse
import com.dhenu.app.ui.customer.response.CustomerListResponse.CustomerData
import com.dhenu.app.ui.mortgage.getmortgage.MortgageListActivity
import com.dhenu.app.ui.village.VillageListActivity
import com.dhenu.app.ui.village.response.AddVillageResponse
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.enums.IntentKeys
import java.util.Objects

class CustomerListActivity : BaseActivity<ActivityCustomerListBinding, CustomerListViewModel>(),
    CustomerListNavigator {

    override val bindingVariable: Int
        get() = BR.commonListVM

    override val layoutId: Int
        get() = R.layout.activity_customer_list

    override val viewModel = CustomerListViewModel()

    var mAdapter: CustomerListAdapter? = null
    var arrayList: ArrayList<CustomerData> = ArrayList()

    internal var delay: Long = 600 // 1 seconds after user stops typing
    internal var last_text_edit: Long = 0
    internal var handler = Handler()
    var searchKeyOffer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        if (intent != null && intent.hasExtra(IntentKeys.VILLAGE_DATA.getKey())) {
            viewModel.villageData = intent.getParcelableExtra(IntentKeys.VILLAGE_DATA.getKey())
        }

        DataBinding.onSingleClick(viewDataBinding!!.textAddVillage) {

            val intent = Intent(this, AddCustomerActivity::class.java)
            //intent.putExtra(IntentKeys.CUSTOMER_DATA.getKey(),null)
            addCustomerLauncher.launch(intent)

        }
        DataBinding.onSingleClick(viewDataBinding!!.textSelectVillage) {

            val intent = Intent(this, VillageListActivity::class.java)
            selectVillageLauncher.launch(intent)

        }

        val input_finish_checker = Runnable {
            if (System.currentTimeMillis() > last_text_edit + delay - 200) {
//                if (searchKeyOffer.length > 0 || searchKeyOffer.isEmpty()) {
                if (checkIfInternetOnDialog(tryAgainClick = {
                        viewModel.customerListAPI(searchKeyOffer)
                    })) {
                    viewModel.customerListAPI(searchKeyOffer)
                }
//                }
            }
        }

        viewDataBinding!!.searchField.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                searchKeyOffer = s.toString()
                last_text_edit = System.currentTimeMillis()
                handler.postDelayed(input_finish_checker, delay)
                if (searchKeyOffer.isEmpty()) {
                    viewDataBinding!!.imageCrossIcon.visibility = View.GONE
                } else {
                    viewDataBinding!!.imageCrossIcon.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                handler.removeCallbacks(input_finish_checker)
            }

        })
        viewDataBinding!!.imageCrossIcon.setOnClickListener {
            searchKeyOffer = ""
            viewDataBinding!!.searchField.setText("")
        }

        if (checkIfInternetOnDialog(tryAgainClick = {
                viewModel.customerListAPI(searchKeyOffer)
            })) {
            viewModel.customerListAPI(searchKeyOffer)
        }
    }

    private val selectVillageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.villageData = data!!.getParcelableExtra(IntentKeys.VILLAGE_DATA.getKey())
                viewDataBinding!!.textSelectVillage.setText(viewModel.villageData!!.Name)

                if (checkIfInternetOnDialog(tryAgainClick = {
                        viewModel.customerListAPI(searchKeyOffer)
                    })) {
                    viewModel.customerListAPI(searchKeyOffer)
                }

            }
        }

    val addCustomerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                if (checkIfInternetOnDialog(tryAgainClick = {
                        viewModel.customerListAPI(searchKeyOffer)
                    })) {
                    viewModel.customerListAPI(searchKeyOffer)
                }
            }
        }

    override fun villageListResponse(response: CustomerListResponse) {
        arrayList.clear()
        arrayList.addAll(response.data)
        mAdapter!!.notifyDataSetChanged()

    }

    override fun addVillageResponse(response: AddVillageResponse) {

        CommonUtils.showMessage(response.message, this)
        if (checkIfInternetOnDialog(tryAgainClick = {
                viewModel.customerListAPI(searchKeyOffer)
            })) {
            viewModel.customerListAPI(searchKeyOffer)
        }

    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "ग्राहक के नाम"
        viewDataBinding!!.recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = CustomerListAdapter(this, arrayList, onItemClick = { index ->

            val resultIntent = Intent(this, MortgageListActivity::class.java)
            resultIntent.putExtra(IntentKeys.CUSTOMER_DATA.getKey(), arrayList.get(index))
            startActivity(resultIntent)

        }, onEditClick = { index ->
            val intent = Intent(this, AddCustomerActivity::class.java)
            intent.putExtra(IntentKeys.CUSTOMER_DATA.getKey(), arrayList[index])
            addCustomerLauncher.launch(intent)
        })
        viewDataBinding!!.recyclerView.setHasFixedSize(true)
        viewDataBinding!!.recyclerView.adapter = mAdapter

    }

    private fun showAddDialog(data: CustomerData?) {

        val alertDialog = Dialog(this)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(alertDialog.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding = DialogAddVillageBinding.inflate(layoutInflater)
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
            binding.editName.setText(data.Name)
        }

        binding.imageDialogClose.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.textSave.setOnClickListener {

            if (checkIfInternetOnDialog(tryAgainClick = {
                    if (viewModel.checkValidation(binding)) {
                        alertDialog.dismiss()
                        viewModel.addVillageApi(binding, data?.Id.toString())
                    }
                })) if (viewModel.checkValidation(binding)) {
                alertDialog.dismiss()
                viewModel.addVillageApi(binding, data?.Id.toString())
            }
        }

        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

}