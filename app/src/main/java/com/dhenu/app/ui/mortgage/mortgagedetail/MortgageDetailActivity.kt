package com.dhenu.app.ui.mortgage.mortgagedetail

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityMortgageDetailBinding
import com.dhenu.app.databinding.DialogUpdateIntresetBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.mortgage.response.UpdateMortgageResponse
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.enums.IntentKeys
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

class MortgageDetailActivity :
    BaseActivity<ActivityMortgageDetailBinding, MortgageDetailViewModel>(),
    MortgageDetailNavigator {

    override val bindingVariable: Int
        get() = BR.mortgageDetailVM

    override val layoutId: Int
        get() = R.layout.activity_mortgage_detail

    override val viewModel = MortgageDetailViewModel()

    var formattedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        if (intent != null && intent.hasExtra(IntentKeys.MORTGAGE_DATA.getKey())) {
            viewModel.mortgageData = intent.getParcelableExtra(IntentKeys.MORTGAGE_DATA.getKey())
        }

        if (viewModel.mortgageData != null) {
//            viewDataBinding!!.toolbar.toolBarHeading.text =
//                viewModel.mortgageData?.CustomerName.toString()

            viewModel.interestRate = viewModel.mortgageData?.InterestRate.toString()

            viewDataBinding!!.textInterestRate.text = viewModel.interestRate + "%"

            viewDataBinding!!.textItemName.text = "आइटम: " + viewModel.mortgageData?.ItemName
            viewDataBinding!!.textWeight.text = "वज़न: " + viewModel.mortgageData?.Weight.toString()

            viewDataBinding!!.textItemDescription.text =
                "विवरण: " + viewModel.mortgageData?.Description
            viewDataBinding!!.textMortgageDate.text =
                "दिनांक: " + viewModel.mortgageData?.MortgageDate

            viewDataBinding!!.textItemAmount.text = "अमाउंट: ₹ ${viewModel.mortgageData?.Amount}"
            viewDataBinding!!.textEndDate.text =
                "अंतिम तिथि: " + viewModel.mortgageData?.EndDateDetail

            viewDataBinding!!.textVillageName.text = "गाँव: " + viewModel.mortgageData?.VillageName
            viewDataBinding!!.textMobileNumber.text =
                "मोबाइल नंबर: " + viewModel.mortgageData?.MobileNo

            viewDataBinding!!.textTotalAmount.text =
                "Total Amount ₹ ${viewModel.mortgageData!!.TotalAmount}"

        }

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        viewModel.endDateServer = dateFormat.format(currentDate)

        viewDataBinding!!.textTodayDate.text =
            CommonUtils.getDateInFormat("dd MMM yyyy HH:mm", "dd MMM yyyy", viewModel.endDateServer)

        DataBinding.onSingleClick(viewDataBinding!!.textTodayDate) {

            val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select a date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Preselect today's date
                .build()

            datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                val selectedDate = simpleDateFormat.format(Date(selection))
                viewModel.endDateServer = selectedDate
                viewDataBinding!!.textTodayDate.text = CommonUtils.getDateInFormat(
                    "dd MMM yyyy HH:mm", "dd MMM yyyy", viewModel.endDateServer
                )
                if (checkIfInternetOnDialog(tryAgainClick = {
                        viewModel.updateMortgageApi(false)
                    })) {
                    viewModel.updateMortgageApi(false)
                }

            }

        }
        DataBinding.onSingleClick(viewDataBinding!!.textCloseMortgage) {

            if (checkIfInternetOnDialog(tryAgainClick = {
                    viewModel.updateMortgageApi(true)
                })) {
                viewModel.updateMortgageApi(true)
            }

        }
        DataBinding.onSingleClick(viewDataBinding!!.textInterestRate) {

            showUpdateInterestRate();
        }

    }

    override fun updateMortgage(response: UpdateMortgageResponse, isClose: Boolean) {
        if (isClose) {

            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()

        } else {
            viewDataBinding!!.textTotalAmount.text = "Total Amount ₹ " + response.totalAmount
        }
    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "गिरवी छुड़वाना"

    }

    private fun showUpdateInterestRate() {

        val alertDialog = Dialog(this)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(alertDialog.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding = DialogUpdateIntresetBinding.inflate(layoutInflater)
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

        binding.textSave.setOnClickListener {

            if (binding.editInterestRate.text!!.trim().isEmpty()) {
                showValidationError("ब्याज दर दर्ज करें")
            } else {

                viewModel.interestRate = binding.editInterestRate.text.toString()
                viewDataBinding!!.textInterestRate.text = viewModel.interestRate + "%"
                alertDialog.dismiss()

                if (checkIfInternetOnDialog(tryAgainClick = {
                        viewModel.updateMortgageApi(false)
                    })) {
                    viewModel.updateMortgageApi(false)
                }

            }

        }

        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }


}