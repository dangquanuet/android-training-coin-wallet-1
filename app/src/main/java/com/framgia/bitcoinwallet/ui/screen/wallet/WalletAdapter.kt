package com.framgia.bitcoinwallet.ui.screen.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.databinding.ItemWalletBinding
import com.framgia.bitcoinwallet.ui.BaseRecyclerView
import com.framgia.bitcoinwallet.ui.BaseRecyclerViewHolder

class WalletAdapter(context: Context, wallets: List<Wallet>,
                    val listener: BaseRecyclerViewHolder.OnItemClickListener<Wallet>)
    : BaseRecyclerView<WalletAdapter.WalletHolder, ItemWalletBinding, Wallet>(
        context, wallets
) {
    override fun getViewHolder(parent: ViewGroup, viewType: Int): WalletHolder {
        return WalletHolder(ItemWalletBinding.inflate(LayoutInflater.from(context)
                ,parent, false))
    }

    inner class WalletHolder(binding: ItemWalletBinding)
        : BaseRecyclerViewHolder<ItemWalletBinding, Wallet>(binding) {
        override fun bindData(item: Wallet) {
            binding.apply {
                viewModel = ItemWalletViewModel(item)
                executePendingBindings()
                root.setOnClickListener {
                    listener.onItemClick(adapterPosition, item)
                }
            }

        }
    }
}
