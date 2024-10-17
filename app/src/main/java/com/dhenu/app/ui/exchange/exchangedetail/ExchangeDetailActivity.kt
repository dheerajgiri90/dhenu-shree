package com.dhenu.app.ui.exchange.exchangedetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityExchangeDetailBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.exchange.adapter.ExchangeItemAdapter
import com.dhenu.app.ui.exchange.response.AddExchangeItemResponse
import com.dhenu.app.ui.exchange.response.CloseExchangeResponse
import com.dhenu.app.ui.exchange.response.ExchangeItemListResponse
import com.dhenu.app.ui.exchange.response.ExchangeItemListResponse.ExchangeItem
import com.dhenu.app.ui.mortgage.getmortgage.MortgageListActivity
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.enums.IntentKeys

class ExchangeDetailActivity :
    BaseActivity<ActivityExchangeDetailBinding, ExchangeDetailViewModel>(),
    ExchangeDetailNavigator {

    override val bindingVariable: Int
        get() = BR.commonListVM

    override val layoutId: Int
        get() = R.layout.activity_exchange_detail

    override val viewModel = ExchangeDetailViewModel()

    var mAdapter: ExchangeItemAdapter? = null
    var arrayList: ArrayList<ExchangeItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

        init()
        if (intent != null && intent.hasExtra(IntentKeys.EXCHANGE_DATA.getKey())) {
            viewModel.exchangeData = intent.getParcelableExtra(IntentKeys.EXCHANGE_DATA.getKey())
        }

        if (viewModel.exchangeData != null) {
            viewDataBinding!!.toolbar.toolBarHeading.text =
                viewModel.exchangeData?.BusinessManName.toString()

            var date = CommonUtils.getDateInFormat(
                "yyyy-MM-dd'T'HH:mm:ss", "dd MMM yyyy", viewModel.exchangeData?.ExchangeDate ?: ""
            )

            viewDataBinding!!.textExchangeDate.text = "दिनांक: " + date
            viewDataBinding!!.textExchangeRate.text =
                "ब्याज दर: " + viewModel.exchangeData?.InterestRate.toString() + "%"
            viewDataBinding!!.textExchangeAmount.text =
                "अमाउंट: ₹" + viewModel.exchangeData?.Amount.toString()
        }
        if (checkIfInternetOnDialog(tryAgainClick = {
                viewModel.getMortgageList("")
            })) {
            viewModel.getMortgageList("")
        }

        viewDataBinding!!.textAddMortgage.setOnClickListener {

            val resultIntent = Intent(this, MortgageListActivity::class.java)
            resultIntent.putExtra(IntentKeys.COME_FROM.getKey(), "SELECT")
            selectLauncher.launch(resultIntent)

        }


    }

    override fun exchangeItemsListResponse(response: ExchangeItemListResponse) {

        val date = CommonUtils.getDateInFormat(
            "yyyy-MM-dd'T'HH:mm:ss", "dd MMM yyyy", response.exchangeData.get(0).ExchangeDate ?: ""
        )
        viewDataBinding!!.textExchangeDate.text = "दिनांक: " + date
        viewDataBinding!!.textExchangeRate.text =
            "ब्याज दर: " + response.exchangeData.get(0).InterestRate.toString() + "%"
        viewDataBinding!!.textExchangeAmount.text =
            "अमाउंट: ₹" + response.exchangeData.get(0).Amount.toString()

        arrayList.clear()
        arrayList.addAll(response.data)
        mAdapter!!.notifyDataSetChanged()
    }

    override fun addExchangeItemResponse(response: AddExchangeItemResponse) {

        if (checkIfInternetOnDialog(tryAgainClick = {
                viewModel.getMortgageList("")
            })) {
            viewModel.getMortgageList("")
        }
    }

    override fun closeExchangeItemResponse(response: CloseExchangeResponse) {
        if (checkIfInternetOnDialog(tryAgainClick = {
                viewModel.getMortgageList("")
            })) {
            viewModel.getMortgageList("")
        }
    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "गिरवी लिस्ट"
        viewDataBinding!!.recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = ExchangeItemAdapter(this, arrayList, onItemClick = { index ->


        }, onCloseClick = { index ->

            if (checkIfInternetOnDialog(tryAgainClick = {
                    viewModel.closeExchangeItem(arrayList[index].ExchangeItemId.toString())
                })) {
                viewModel.closeExchangeItem(arrayList[index].ExchangeItemId.toString())
            }

        })
        viewDataBinding!!.recyclerView.setHasFixedSize(true)
        viewDataBinding!!.recyclerView.adapter = mAdapter

    }

    private val selectLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.mortgageData =
                    data!!.getParcelableExtra(IntentKeys.MORTGAGE_DATA.getKey())

                if (checkIfInternetOnDialog(tryAgainClick = {
                        viewModel.addExchangeItem()
                    })) {
                    viewModel.addExchangeItem()
                }

            }
        }


}