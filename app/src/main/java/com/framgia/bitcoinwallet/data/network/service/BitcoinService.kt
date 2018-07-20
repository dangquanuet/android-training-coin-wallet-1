package com.framgia.bitcoinwallet.data.network.service

import android.app.Application
import com.framgia.bitcoinwallet.data.network.RetrofitHelper
import com.framgia.bitcoinwallet.data.network.api.BitcoinApi
import io.reactivex.annotations.NonNull

object BitcoinService {

    private const val CRYPTO_BASE_URL = "https://min-api.cryptocompare.com/data/"
    private lateinit var bitcoinApi: BitcoinApi

    fun initBitcoinService(@NonNull app: Application) {
        bitcoinApi = RetrofitHelper.createService(app, CRYPTO_BASE_URL, BitcoinApi::class.java)
    }

    fun getBitcoinApiInstance(): BitcoinApi = if (bitcoinApi != null) bitcoinApi
    else throw RuntimeException("Need call method ServiceClient#initialize() first")
}
