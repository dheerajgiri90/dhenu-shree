package com.dhenu.app.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<T : ViewDataBinding, V : Any, VH : BaseRecyclerAdapter<T, V, VH>.BaseViewHolder> :
    RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateViewHolder(createDataBinding(parent, viewType), parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, position, 1)
    }

    abstract fun onBindViewHolder(holder: VH, position: Int, type: Int)

    abstract fun onCreateViewHolder(viewdataBinding: T, parent: ViewGroup, viewType: Int): VH

    @LayoutRes
    abstract fun getLayoutId(viewType: Int): Int

    private fun createDataBinding(parent: ViewGroup, viewType: Int): T {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType), parent, false
        )
    }

    abstract inner class BaseViewHolder(mViewDataBinding: T) :
        RecyclerView.ViewHolder(mViewDataBinding.root) {
        var viewDataBinding: T
            internal set

        abstract val bindingVariable: Int
        abstract val viewModel: V

        init {
            this.viewDataBinding = mViewDataBinding
        }

        fun bindTo(position: Int) {}

        fun bindToDataVM(vm: Int, objecct: Any) {
            viewDataBinding.setVariable(vm, objecct)
            viewDataBinding.executePendingBindings()
        }

        fun bindToDataVarM(vm: Int, objecct: Any): BaseViewHolder {
            viewDataBinding.setVariable(vm, objecct)
            return this
        }

        fun executeToDataVM() {
            viewDataBinding.executePendingBindings()
        }
    }

    open fun onItemClicked(position: Int) {}
    open fun onItemRemoved(position: Int) {}
    open fun onItemDetail(position: Int) {}
    open fun onItemCancelled(position: Int) {}
    open fun onItemRefreshed(position: Int) {}
    open fun onItemEvent(vararg event: Any) {}
}