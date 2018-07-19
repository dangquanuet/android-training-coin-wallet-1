package com.framgia.bitcoinwallet.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wallet(var id: String = "", var coin: Float, var createAt: String, var name: String)
    : Parcelable {
    constructor() : this("",0f,"","Personal Wallet")
    var isChoosed: Boolean = false
}
