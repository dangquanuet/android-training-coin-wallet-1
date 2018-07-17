package com.framgia.bitcoinwallet.ui

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseRecyclerView <ViewHoler :
BaseRecyclerViewHolder<View, Item>, View : ViewDataBinding, Item : Any>
(val items: List<Item>)
    : RecyclerView.Adapter<BaseRecyclerViewHolder<View, Item>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BaseRecyclerViewHolder<View, Item> = getViewHolder(parent, viewType)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<View, Item>, position: Int) {
        holder.bindData(items[position])
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int) : ViewHoler
}
