package com.dhenu.app.ui.exchange.adapter

import android.content.Context
import android.view.ViewGroup
import com.dhenu.app.R
import com.dhenu.app.databinding.ItemExchangeDetailListBinding
import com.dhenu.app.ui.base.BaseRecyclerAdapter
import com.dhenu.app.ui.exchange.response.ExchangeItemListResponse.ExchangeItem

class ExchangeItemAdapter(
    private val context: Context,
    private val list: ArrayList<ExchangeItem>,
    private val onItemClick: (position: Int) -> Unit,
    private val onCloseClick: (position: Int) -> Unit
) :
    BaseRecyclerAdapter<ItemExchangeDetailListBinding, Any, ExchangeItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        viewDataBinding: ItemExchangeDetailListBinding,
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

        holder.viewDataBinding.imageClose.setOnClickListener {
            onCloseClick.invoke(position)
        }

        holder.viewDataBinding.textTokenNumber.text =
            "टोकन नंबर: " + list.get(position).MortgageId.toString()
        holder.viewDataBinding.textMortgageDate.text = list.get(position).ItemDate
        holder.viewDataBinding.textItemName.text =
            "आइटम: " + list.get(position).ItemName + "," + list[position].CustomerName + "," + list[position].Address
        holder.viewDataBinding.textAmount.text = "₹ " + list.get(position).Amount

    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_exchange_detail_list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(mViewDataBinding: ItemExchangeDetailListBinding) :
        BaseViewHolder(mViewDataBinding) {

        override val viewModel: Any
            get() = Any()

        override val bindingVariable: Int
            get() = 0

    }

}