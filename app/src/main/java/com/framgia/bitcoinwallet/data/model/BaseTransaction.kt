package com.framgia.bitcoinwallet.data.model

open class BaseTransaction {
    var amount: Float = 0.0f
    lateinit var note: String
    lateinit var timestamp: String
}
