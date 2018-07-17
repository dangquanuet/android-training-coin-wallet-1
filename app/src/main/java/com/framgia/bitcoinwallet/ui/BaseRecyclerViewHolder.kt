package com.framgia.bitcoinwallet.ui

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

abstract class BaseRecyclerViewHolder<View : ViewDataBinding, ItemData : Any>(val binding: View)
    : RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(item: ItemData)

    interface OnItemClickListener<ItemData> {
        fun onItemClick(position: Int, data: ItemData)
    }
}
