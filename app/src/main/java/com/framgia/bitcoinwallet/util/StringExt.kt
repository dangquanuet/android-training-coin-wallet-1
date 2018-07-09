package com.framgia.bitcoinwallet.util

import android.text.TextUtils
import android.util.Patterns

fun String.emailValidate(email: String): Boolean {
    return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
}
