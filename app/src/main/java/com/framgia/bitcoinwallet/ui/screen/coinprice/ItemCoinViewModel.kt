package com.framgia.bitcoinwallet.ui.screen.coinprice

import android.arch.lifecycle.MutableLiveData
import com.framgia.bitcoinwallet.data.model.BitCoin

class ItemCoinViewModel(item: BitCoin) {
    val coin: MutableLiveData<BitCoin> = MutableLiveData()

    init {
        coin.value = item
    }
}