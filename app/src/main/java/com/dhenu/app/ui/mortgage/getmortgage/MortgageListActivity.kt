package com.dhenu.app.ui.mortgage.getmortgage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.databinding.ActivityMortgageListBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.mortgage.adapter.MortgageListAdapter
import com.dhenu.app.ui.mortgage.mortgagedetail.MortgageDetailActivity
import com.dhenu.app.ui.mortgage.response.MortgageListResponse
import com.dhenu.app.ui.mortgage.response.MortgageListResponse.MortgageData
import com.dhenu.app.util.enums.IntentKeys

class MortgageListActivity : BaseActivity<ActivityMortgageListBinding, MortgageListViewModel>(),
    MortgageListNavigator {

    override val bindingVariable: Int
        get() = BR.commonListVM

    override val layoutId: Int
        get() = R.layout.activity_mortgage_list

    override val viewModel = MortgageListViewModel()

    var mAdapter: MortgageListAdapter? = null
    var arrayList: ArrayList<MortgageData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

        init()
        if (intent != null && intent.hasExtra(IntentKeys.CUSTOMER_DATA.getKey())) {
            viewModel.customerData = intent.getParcelableExtra("CUSTOMER_DATA")
        }
        if (intent != null && intent.hasExtra(IntentKeys.COME_FROM.getKey())) {
            viewModel.comeFrom = intent.getStringExtra(IntentKeys.COME_FROM.getKey())
        }

        if (viewModel.customerData != null) {
            viewDataBinding!!.toolbar.toolBarHeading.text = viewModel.customerData?.Name.toString()
        }
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

        mAdapter = MortgageListAdapter(this, arrayList,
            onItemClick = { index ->
                if (!arrayList[index].IsClosed) {

                    if (viewModel.comeFrom == "SELECT") {
                        val resultIntent = Intent()
                        resultIntent.putExtra(
                            IntentKeys.MORTGAGE_DATA.getKey(),
                            arrayList[index]
                        )
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    } else {
                        val resultIntent = Intent(this, MortgageDetailActivity::class.java)
                        resultIntent.putExtra(IntentKeys.MORTGAGE_DATA.getKey(), arrayList[index])
                        mortgageDetailLauncher.launch(resultIntent)
                    }


                } else {
                    showValidationError("पहले से ही क्लोज है ")
                }

            })
        viewDataBinding!!.recyclerView.setHasFixedSize(true)
        viewDataBinding!!.recyclerView.adapter = mAdapter

    }

    private val mortgageDetailLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (checkIfInternetOnDialog(tryAgainClick = {
                        viewModel.getMortgageList("")
                    })) {
                    viewModel.getMortgageList("")
                }
            }
        }

    override fun mortgageListResponse(response: MortgageListResponse) {
        arrayList.clear()
        arrayList.addAll(response.data)
        mAdapter!!.notifyDataSetChanged()

    }


}