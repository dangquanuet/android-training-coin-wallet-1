package com.framgia.bitcoinwallet.ui.screen.wallet

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.databinding.ItemWalletBinding
import com.framgia.bitcoinwallet.ui.BaseRecyclerView
import com.framgia.bitcoinwallet.ui.BaseRecyclerViewHolder

class WalletAdapter(var wallets: MutableList<Wallet>,
                    val listener: BaseRecyclerViewHolder.OnItemClickListener<Wallet>)
    : BaseRecyclerView<WalletAdapter.WalletHolder, ItemWalletBinding, Wallet>(wallets) {

    private var previousChoosed: Int = -1
    private var ishowCheckUi: Boolean = false

    override fun getViewHolder(parent: ViewGroup, viewType: Int): WalletHolder {
        return WalletHolder(ItemWalletBinding.inflate(LayoutInflater.from(parent.context)
                , parent, false))
    }

    inner class WalletHolder(binding: ItemWalletBinding)
        : BaseRecyclerViewHolder<ItemWalletBinding, Wallet>(binding) {
        override fun bindData(item: Wallet) {
            binding.apply {
                viewModel = ItemWalletViewModel(item)
                viewModel?.isShowCheckUi?.value = ishowCheckUi

                if (previousChoosed != -1 && adapterPosition == previousChoosed) {
                    item.isChoosed = false
                }

                executePendingBindings()
                root.setOnClickListener {
                    listener.onItemClick(adapterPosition, item)
                }
                checkboxItemWallet.setOnClickListener {
                    item.isChoosed = checkboxItemWallet.isChecked
                    listener.onItemClick(adapterPosition, item)
                }
            }
        }
    }

    fun updateAddWallet(wallet: Wallet) {
        wallets.add(wallet)
        notifyItemInserted(itemCount)
    }

    fun notifyPreviousItemCheck(currentPosistion: Int) {
        if (previousChoosed != -1 && previousChoosed!= currentPosistion) {
            wallets[previousChoosed].isChoosed = false
            notifyItemChanged(previousChoosed)
        }
        previousChoosed = currentPosistion
    }

    fun showCheckBoxChoose(isShow: Boolean) {
        ishowCheckUi = isShow
        notifyDataSetChanged()
    }

    fun updateData(newWallets: MutableList<Wallet>) {
        wallets.apply {
            clear()
            addAll(newWallets)
        }
        showCheckBoxChoose(false)
    }
}
