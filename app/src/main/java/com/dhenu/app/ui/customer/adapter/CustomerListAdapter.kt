package com.dhenu.app.ui.customer.adapter

import android.content.Context
import android.view.ViewGroup
import com.dhenu.app.R
import com.dhenu.app.databinding.ItemVillageListBinding
import com.dhenu.app.ui.base.BaseRecyclerAdapter
import com.dhenu.app.ui.customer.response.CustomerListResponse.CustomerData

class CustomerListAdapter(
    private val context: Context,
    private val list: ArrayList<CustomerData>,
    private val onItemClick: (position: Int) -> Unit,
    private val onEditClick: (position: Int) -> Unit
) :
    BaseRecyclerAdapter<ItemVillageListBinding, Any, CustomerListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        viewDataBinding: ItemVillageListBinding,
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

        holder.viewDataBinding.imageEditOption.setOnClickListener {
            onEditClick.invoke(position)
        }

        holder.viewDataBinding.textStudentName.text =
            list[position].Name + ", " + list[position].VillageName

    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_village_list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(mViewDataBinding: ItemVillageListBinding) :
        BaseViewHolder(mViewDataBinding) {

        override val viewModel: Any
            get() = Any()

        override val bindingVariable: Int
            get() = 0

    }

}