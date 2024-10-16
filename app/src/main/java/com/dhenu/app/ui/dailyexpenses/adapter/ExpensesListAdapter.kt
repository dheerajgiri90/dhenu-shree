package com.dhenu.app.ui.dailyexpenses.adapter

import android.content.Context
import android.view.ViewGroup
import com.dhenu.app.R
import com.dhenu.app.databinding.ItemDailyExpensesListBinding
import com.dhenu.app.ui.base.BaseRecyclerAdapter
import com.dhenu.app.ui.dailyexpenses.response.ExpensesListResponse.DailyExpenseItem
import com.dhenu.app.ui.exchange.response.ExchangeListResponse.ExchangeData
import com.dhenu.app.util.CommonUtils

class ExpensesListAdapter(
    private val context: Context,
    private val list: ArrayList<DailyExpenseItem>,
    private val onItemClick: (position: Int) -> Unit
) :
    BaseRecyclerAdapter<ItemDailyExpensesListBinding, Any, ExpensesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        viewDataBinding: ItemDailyExpensesListBinding,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, type: Int) {
        holder.bindToDataVM(holder.bindingVariable, holder.viewModel)
        holder.bindTo(position)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(position)
        }

        holder.viewDataBinding.textAmount.text = "₹" + list[position].Amount + "/-"
        holder.viewDataBinding.textDescription.text = list[position].Description

    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_daily_expenses_list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(mViewDataBinding: ItemDailyExpensesListBinding) :
        BaseViewHolder(mViewDataBinding) {

        override val viewModel: Any
            get() = Any()

        override val bindingVariable: Int
            get() = 0

    }

}