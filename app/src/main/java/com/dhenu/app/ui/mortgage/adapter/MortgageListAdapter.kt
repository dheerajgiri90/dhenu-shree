package com.dhenu.app.ui.mortgage.adapter

import android.content.Context
import android.view.ViewGroup
import com.dhenu.app.R
import com.dhenu.app.databinding.ItemMortageListBinding
import com.dhenu.app.ui.base.BaseRecyclerAdapter
import com.dhenu.app.ui.mortgage.response.MortgageListResponse.MortgageData

class MortgageListAdapter(
    private val context: Context,
    private val list: ArrayList<MortgageData>,
    private val onItemClick: (position: Int) -> Unit
) :
    BaseRecyclerAdapter<ItemMortageListBinding, Any, MortgageListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        viewDataBinding: ItemMortageListBinding,
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

        holder.viewDataBinding.textTokenNumber.text =
            "टोकन नंबर: " + list.get(position).Id.toString()
        holder.viewDataBinding.textMortgageDate.text = list.get(position).MortgageDate
        holder.viewDataBinding.textItemName.text = "आइटम: " + list.get(position).ItemName
        holder.viewDataBinding.textAmount.text = "₹ " + list.get(position).Amount

        if (list[position].IsClosed) {
            holder.viewDataBinding.root.background =
                context.getDrawable(R.drawable.dr_item_close_bg)
        } else if (list[position].IsExchanged) {
            holder.viewDataBinding.root.background =
                context.getDrawable(R.drawable.dr_item_exchange_bg)
        } else {
            holder.viewDataBinding.root.background =
                context.getDrawable(R.drawable.dr_edittext_background)
        }

    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_mortage_list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(mViewDataBinding: ItemMortageListBinding) :
        BaseViewHolder(mViewDataBinding) {

        override val viewModel: Any
            get() = Any()

        override val bindingVariable: Int
            get() = 0

    }

}