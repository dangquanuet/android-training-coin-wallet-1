package com.framgia.bitcoinwallet

import android.app.Application
import com.framgia.bitcoinwallet.data.network.service.BitcoinService

class WalletApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        BitcoinService.initBitcoinService(this)
    }
}
