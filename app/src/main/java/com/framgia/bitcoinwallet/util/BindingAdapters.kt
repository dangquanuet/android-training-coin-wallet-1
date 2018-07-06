package com.framgia.bitcoinwallet.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created: 06/07/2018
 * By: Sang
 * Description:
 */
object BindingAdapters {

    @BindingAdapter("imageResource")
    @JvmStatic
    fun setImageResource(view: ImageView, res: Int) {
        Glide.with(view.context).load(res).into(view)
    }
}
