package com.dhenu.app.ui.items

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhenu.app.BR
import com.dhenu.app.R
import com.dhenu.app.data.ListData
import com.dhenu.app.databinding.ActivityItemsListBinding
import com.dhenu.app.databinding.DialogAddVillageBinding
import com.dhenu.app.ui.base.BaseActivity
import com.dhenu.app.ui.items.response.AddItemsResponse
import com.dhenu.app.ui.items.response.ItemsListResponse
import com.dhenu.app.ui.village.adapter.VillageListAdapter
import com.dhenu.app.util.CommonUtils
import com.dhenu.app.util.DataBinding
import com.dhenu.app.util.enums.IntentKeys
import java.util.Objects

class ItemsListActivity : BaseActivity<ActivityItemsListBinding, ItemsListViewModel>(),
    ItemsListNavigator {

    override val bindingVariable: Int
        get() = BR.commonListVM

    override val layoutId: Int
        get() = R.layout.activity_items_list

    override val viewModel = ItemsListViewModel()

    var mAdapter: VillageListAdapter? = null
    var arrayList: ArrayList<ListData> = ArrayList()

    internal var delay: Long = 600 // 1 seconds after user stops typing
    internal var last_text_edit: Long = 0
    internal var handler = Handler()
    var searchKeyOffer: String = ""
    var comefrom: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        init()

        if (intent != null && intent.hasExtra(IntentKeys.COME_FROM.getKey())) {
            comefrom = intent.getStringExtra(IntentKeys.COME_FROM.getKey()).toString()
        }

        DataBinding.onSingleClick(viewDataBinding!!.textAddVillage) {
            hideKeyboard()
            showAddDialog(null)
        }

        val input_finish_checker = Runnable {
            if (System.currentTimeMillis() > last_text_edit + delay - 200) {
//                if (searchKeyOffer.length > 0 || searchKeyOffer.isEmpty()) {
                if (checkIfInternetOnDialog(tryAgainClick = {
                        viewModel.villageListAPI(searchKeyOffer)
                    })) {
                    viewModel.villageListAPI(searchKeyOffer)
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
                viewModel.villageListAPI(searchKeyOffer)
            })) {
            viewModel.villageListAPI(searchKeyOffer)
        }
    }

    override fun itemListResponse(response: ItemsListResponse) {
        arrayList.clear()
        arrayList.addAll(response.data)
        mAdapter!!.notifyDataSetChanged()

    }

    override fun addItemsResponse(response: AddItemsResponse) {

        CommonUtils.showMessage(response.message, this)
        if (checkIfInternetOnDialog(tryAgainClick = {
                viewModel.villageListAPI(searchKeyOffer)
            })) {
            viewModel.villageListAPI(searchKeyOffer)
        }

    }

    override fun init() {

        viewDataBinding!!.toolbar.stepBackButton.setOnClickListener { finish() }
        viewDataBinding!!.toolbar.toolBarHeading.text = "आइटम"

        viewDataBinding!!.recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = VillageListAdapter(this, arrayList,
            onItemClick = { index ->
                if (comefrom == "SELECT") {
                    val resultIntent = Intent()
                    resultIntent.putExtra(IntentKeys.ITEM_DATA.getKey(), arrayList.get(index))
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }

            }, onEditClick = { index ->
                showAddDialog(arrayList.get(index))
            })
        viewDataBinding!!.recyclerView.setHasFixedSize(true)
        viewDataBinding!!.recyclerView.adapter = mAdapter

    }

    private fun showAddDialog(data: ListData?) {

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
            screenWidth - 2 * margin,
            ViewGroup.LayoutParams.WRAP_CONTENT
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
                }))
                if (viewModel.checkValidation(binding)) {
                    alertDialog.dismiss()
                    viewModel.addVillageApi(binding, data?.Id.toString())
                }
        }

        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

}