package com.framgia.bitcoinwallet.ui.screen.coinprice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.bitcoinwallet.data.model.BitCoin
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.databinding.ItemCoinBinding
import com.framgia.bitcoinwallet.ui.BaseRecyclerView
import com.framgia.bitcoinwallet.ui.BaseRecyclerViewHolder

class CoinAdapter(var coins: MutableList<BitCoin>,
                  val listener: BaseRecyclerViewHolder.OnItemClickListener<BitCoin>)
    : BaseRecyclerView<CoinAdapter.CoinHolder, ItemCoinBinding, BitCoin>(coins) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): CoinHolder {
        return CoinHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context)
                , parent, false))
    }

    fun updateData(newCoins: MutableList<BitCoin>) {
        coins.apply {
            clear()
            addAll(newCoins)
        }
        notifyDataSetChanged()
    }

    inner class CoinHolder(binding: ItemCoinBinding)
        : BaseRecyclerViewHolder<ItemCoinBinding, BitCoin>(binding) {
        override fun bindData(item: BitCoin) {
            binding.apply {
                viewModel = ItemCoinViewModel(item)
                executePendingBindings()
                root.setOnClickListener {
                    listener.onItemClick(adapterPosition, item)
                }
            }
        }

    }
}