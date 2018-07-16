package com.framgia.bitcoinwallet.util

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide

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
