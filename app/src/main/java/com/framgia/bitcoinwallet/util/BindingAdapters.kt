package com.framgia.bitcoinwallet.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("imageResource")
fun setImageResource (view: ImageView, res: Int) {
    Glide.with(view.context).load(res).into(view)
}
