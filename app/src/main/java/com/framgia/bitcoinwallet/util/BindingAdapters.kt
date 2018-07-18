package com.framgia.bitcoinwallet.util

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.ui.screen.wallet.WalletAdapter

/**
 * Created: 06/07/2018
 * By: Sang
 * Description:
 */

@BindingAdapter("imageResource")
fun setImageResource(view: ImageView, res: Int) {
    Glide.with(view.context).load(res).into(view)
}

@BindingAdapter("imageBm")
fun setImageBm(view: ImageView, bitmap: Bitmap) {
    Glide.with(view.context).load(bitmap).into(view)
}

@BindingAdapter("walletItems")
fun setWalletItems(view: RecyclerView, item: MutableList<Wallet>?) {
    when (view.adapter) {
        is WalletAdapter -> item?.let {
            (view.adapter as WalletAdapter).updateData(it)
        }
    }
}
