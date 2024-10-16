package com.dhenu.app.ui.dailyexpenses

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityDailyExpensesBinding
import com.dhenu.app.databinding.DialogAddExpensesBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.dailyexpenses.adapter.ExpensesListAdapter
import com.dhenu.app.ui.dailyexpenses.response.AddExpensesResponse
import com.dhenu.app.ui.dailyexpenses.response.ExpensesListResponse
import com.dhenu.app.ui.dailyexpenses.response.ExpensesListResponse.DailyExpenseItem
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.enums.IntentKeys
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

class ExpensesListActivity : BaseActivity<ActivityDailyExpensesBinding, ExpensesViewModel>(),
    ExpensesListNavigator {

    override val bindingVariable: Int
        get() = BR.expensesListVM

    override val layoutId: Int
        get() = R.layout.activity_daily_expenses

    override val viewModel = ExpensesViewModel()

    var mCreditAdapter: ExpensesListAdapter? = null
    var mDebitAdapter: ExpensesListAdapter? = null

    var arrayListCredit: ArrayList<DailyExpenseItem> = ArrayList()
    var arrayListDebit: ArrayList<DailyExpenseItem> = ArrayList()
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        if (intent != null && intent.hasExtra(IntentKeys.BUSINESS_DATA.getKey())) {
            viewModel.businessmanData = intent.getParcelableExtra(IntentKeys.BUSINESS_DATA.getKey())
        }

        if (viewModel.businessmanData != null) {
            viewDataBinding!!.toolbar.toolBarHeading.text =
                viewModel.businessmanData?.Name.toString()
        }

        DataBinding.onSingleClick(viewDataBinding!!.textAddCredit) {

            showAddDialog("C")
        }

        DataBinding.onSingleClick(viewDataBinding!!.textAddDebit) {
            showAddDialog("D")
        }
        DataBinding.onSingleClick(viewDataBinding!!.textSelectedDate) {
            showMaterialDatePicker()
        }

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        selectedDate = dateFormat.format(currentDate)
        viewDataBinding!!.textSelectedDate.text = "तारीख: $selectedDate"

        if (checkIfInternetOnDialog(tryAgainClick = {
                viewModel.villageListAPI(selectedDate)
            })) {
            viewModel.villageListAPI(selectedDate)
        }

    }

    private fun showMaterialDatePicker() {
        // Create a MaterialDatePicker instance
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Preselect today's date
            .build()

        datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->
            // Convert the selection (Long) to a formatted date string
            val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            selectedDate = simpleDateFormat.format(Date(selection))

            viewDataBinding!!.textSelectedDate.text = selectedDate
            if (checkIfInternetOnDialog(tryAgainClick = {
                    viewModel.villageListAPI(selectedDate)
                })) {
                viewModel.villageListAPI(selectedDate)
            }

        }
    }

    override fun expensesListResponse(response: ExpensesListResponse) {

        arrayListCredit.clear()
        arrayListDebit.clear()

        for (i in 0 until response.data.size) {
            if (response.data.get(i).Type == "C")
                arrayListCredit.add(response.data.get(i))
            else
                arrayListDebit.add(response.data.get(i))
        }

        mCreditAdapter!!.notifyDataSetChanged()
        mDebitAdapter!!.notifyDataSetChanged()
        viewDataBinding!!.textTotalBalance.text = "बैलेंस:: ₹" + response.OpeningBalance + "/-"
        viewDataBinding!!.textCreditTotal.text = "क्रेडिट: ₹" + response.CreditBalance + "/-"
        viewDataBinding!!.textDebitTotal.text = "डिबिट: ₹" + response.DebitBalance + "/-"

        viewDataBinding!!.textClosingBalance.text =
            "क्लोजिंग बैलेंस: ₹" + response.ClosingBalance + "/-"

    }

    override fun addExpensesResponse(response: AddExpensesResponse) {

        CommonUtils.showMessage(response.message, this)
        if (checkIfInternetOnDialog(tryAgainClick = {
                viewModel.villageListAPI(selectedDate)
            })) {
            viewModel.villageListAPI(selectedDate)
        }

    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "रोजनामचा"
        viewDataBinding!!.recyclerCredit.layoutManager = LinearLayoutManager(this)

        mCreditAdapter = ExpensesListAdapter(this, arrayListCredit,
            onItemClick = { index ->


            })
        viewDataBinding!!.recyclerCredit.setHasFixedSize(true)
        viewDataBinding!!.recyclerCredit.adapter = mCreditAdapter
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        viewDataBinding!!.recyclerCredit.addItemDecoration(dividerItemDecoration)

        viewDataBinding!!.recyclerDebit.layoutManager = LinearLayoutManager(this)

        mDebitAdapter = ExpensesListAdapter(this, arrayListDebit,
            onItemClick = { index ->

            })
        viewDataBinding!!.recyclerDebit.setHasFixedSize(true)
        viewDataBinding!!.recyclerDebit.adapter = mDebitAdapter
        viewDataBinding!!.recyclerDebit.addItemDecoration(dividerItemDecoration)

    }

    private fun showAddDialog(type: String) {

        val alertDialog = Dialog(this)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(alertDialog.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding = DialogAddExpensesBinding.inflate(layoutInflater)
        alertDialog.setContentView(binding.root)
        alertDialog.setCancelable(false)
        val window = alertDialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val margin = 150
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        window?.setLayout(
            screenWidth - 2 * margin,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)

        binding.imageDialogClose.setOnClickListener {
            alertDialog.dismiss()
        }

        if (type == "C") {
            binding.textExpenseType.text = "क्रेडिट"
        } else {
            binding.textExpenseType.text = "डिबिट"
        }

        binding.textSave.setOnClickListener {

            if (checkIfInternetOnDialog(tryAgainClick = {
                    if (viewModel.checkValidation(binding)) {
                        alertDialog.dismiss()
                        viewModel.addVillageApi(binding, type)
                    }
                }))
                if (viewModel.checkValidation(binding)) {
                    alertDialog.dismiss()
                    viewModel.addVillageApi(binding, type)
                }
        }

        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

}