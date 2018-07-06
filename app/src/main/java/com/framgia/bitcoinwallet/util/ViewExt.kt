package com.framgia.bitcoinwallet.util

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created: 05/07/2018
 * By: Sang
 * Description:
 */
fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration)
}
