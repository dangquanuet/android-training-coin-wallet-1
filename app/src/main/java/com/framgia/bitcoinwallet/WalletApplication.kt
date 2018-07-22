package com.framgia.bitcoinwallet

import android.app.Application
import android.support.multidex.MultiDex
import com.framgia.bitcoinwallet.data.network.service.BitcoinService

class WalletApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        BitcoinService.initBitcoinService(this)
    }
}
