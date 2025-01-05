package com.dhenu.app.ui.mortgage.mortgagedetail

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
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
    var currentStatus = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        if (intent != null && intent.hasExtra(IntentKeys.MORTGAGE_DATA.getKey())) {
            viewModel.mortgageData = intent.getParcelableExtra(IntentKeys.MORTGAGE_DATA.getKey())
        }
        if (intent != null && intent.hasExtra(IntentKeys.CUSTOMER_DATA.getKey())) {
            viewModel.customerData = intent.getParcelableExtra("CUSTOMER_DATA")
        }
        if (viewModel.customerData != null) {
            viewDataBinding!!.toolbar.toolBarHeading.text =
                viewModel.customerData?.Name.toString() + " " + viewModel.customerData?.VillageName.toString()
        }

        if (viewModel.mortgageData != null) {

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

//            viewDataBinding!!.textMobileNumber.text =
//                "मोबाइल नंबर: " + viewModel.mortgageData?.MobileNo
            viewDataBinding!!.textTotalAmount.text =
                "टोटल अमाउंट: ₹ ${viewModel.mortgageData!!.TotalAmount}"

            val interestAmount =
                viewModel.mortgageData?.TotalAmount?.minus(viewModel.mortgageData?.Amount!!)
            viewDataBinding!!.textInterestAmount.text =
                interestAmount.toString()

            var showStatus = ""
            if (viewModel.mortgageData?.IsExchanged == true) {
                currentStatus = "Trade"
                showStatus = "व्यापारी को दिया"
                viewDataBinding!!.textCloseMortgage.visibility = View.GONE
                viewDataBinding!!.layoutExchangeDetail.visibility = View.VISIBLE
            } else if (viewModel.mortgageData?.IsClosed == true) {
                currentStatus = "Closed"
                showStatus = "समाप्त"
                viewDataBinding!!.textCloseMortgage.visibility = View.GONE
            } else {
                currentStatus = "Open"
                showStatus = "लॉकर में रखा"
            }
            viewDataBinding!!.textMortgageStatus.text = "वर्तमान स्थिति: $showStatus"
            viewDataBinding!!.textTokenNumber.text =
                "टोकन नंबर: " + viewModel.mortgageData?.Id.toString()

            viewDataBinding!!.textBusinessmanName.text =
                "व्यापारी का नाम: " + viewModel.mortgageData?.BusinessmanName

            viewDataBinding!!.textExchangeId.text =
                "थोक नंबर: " + viewModel.mortgageData?.ManualId.toString()

        }

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        viewModel.endDateServer = dateFormat.format(currentDate)

        val currentDateFormated =
            CommonUtils.getDateInFormat("dd MMM yyyy HH:mm", "dd MMM yyyy", viewModel.endDateServer)
        viewDataBinding!!.textTotalDays.text = CommonUtils.calculateDateDifferenceLegacy(
            viewModel.mortgageData?.MortgageDate,
            currentDateFormated
        )
        viewDataBinding!!.textTodayDate.text = currentDateFormated

        DataBinding.onSingleClick(viewDataBinding!!.textTodayDate) {

            if (currentStatus != "Closed") {
                val datePicker =
                    MaterialDatePicker.Builder.datePicker().setTitleText("Select a date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Preselect today's date
                        .build()

                datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

                datePicker.addOnPositiveButtonClickListener { selection ->
                    val simpleDateFormat =
                        SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                    val selectedDate = simpleDateFormat.format(Date(selection))
                    viewModel.endDateServer = selectedDate

                    val currentDateFormated = CommonUtils.getDateInFormat(
                        "dd MMM yyyy HH:mm",
                        "dd MMM yyyy",
                        viewModel.endDateServer
                    )
                    viewDataBinding!!.textTotalDays.text =
                        CommonUtils.calculateDateDifferenceLegacy(
                            viewModel.mortgageData?.MortgageDate,
                            currentDateFormated
                        )

                    viewDataBinding!!.textTodayDate.text = currentDateFormated

                    if (checkIfInternetOnDialog(tryAgainClick = {
                            viewModel.updateMortgageApi(false)
                        })) {
                        viewModel.updateMortgageApi(false)
                    }
                }
            } else {
                showValidationError("यह टोकन क्लोज हो चुका है")
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

            if (currentStatus != "Closed") {
                showUpdateInterestRate();
            } else {
                showValidationError("यह टोकन क्लोज हो चुका है")
            }

        }

    }

    override fun updateMortgage(response: UpdateMortgageResponse, isClose: Boolean) {
        if (isClose) {

            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()

        } else {

            val interestAmount = response.totalAmount.toDouble().minus(viewModel.mortgageData?.Amount!!)
            viewDataBinding!!.textInterestAmount.text = interestAmount.toString()

            val currentDateFormated =
                CommonUtils.getDateInFormat("dd MMM yyyy HH:mm", "dd MMM yyyy", viewModel.endDateServer)
            viewDataBinding!!.textTotalDays.text = CommonUtils.calculateDateDifferenceLegacy(
                viewModel.mortgageData?.MortgageDate,
                currentDateFormated
            )

            viewDataBinding!!.textTotalAmount.text = "Total Amount ₹ " + response.totalAmount
        }
    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        //viewDataBinding!!.toolbar.toolBarHeading.text = "गिरवी छुड़वाना"

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