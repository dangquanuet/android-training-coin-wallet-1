package com.framgia.bitcoinwallet.data.model

open class BaseTransaction {
    protected var amount: Float = 0.0f
    protected lateinit var note: String
    protected var timestamp: Long = 0
}