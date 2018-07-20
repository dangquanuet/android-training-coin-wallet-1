package com.framgia.bitcoinwallet.ui.screen.coinprice

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.BitCoin
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.data.source.repository.UserRepository

class CoinPriceViewModel(private val context: Application,
                         private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    companion object {
        const val TAG = "CoinPriceViewModel"
        const val FSYMS = "BTC,ETH,XMR,LTC,DASH,BCH,ZEC,ETC,XRP,ETN"
        const val USD_TYPE = "USD"
        const val VND_TYPE = "VND"
        const val EUR_TYPE = "EUR"
        const val JPY_TYPE = "JPY"
    }

    val isLoadingData: MutableLiveData<Boolean> = MutableLiveData()
    val coins: MutableLiveData<MutableList<BitCoin>> = MutableLiveData()

    init {
        isLoadingData.value = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startViewModel() {
        getCrypto(USD_TYPE)
    }

    fun getCoinType(): MutableList<String> = mutableListOf<String>().apply {
        add(USD_TYPE)
        add(VND_TYPE)
        add(EUR_TYPE)
        add(JPY_TYPE)
    }

    fun getCrypto(position: Int) {
        isLoadingData.value = true
        var tsyms = ""
        when (position) {
            CoinPriceActivity.USD_CHOOSE_POS -> { tsyms = USD_TYPE}
            CoinPriceActivity.VND_CHOOSE_POS -> { tsyms = VND_TYPE}
            CoinPriceActivity.EUR_CHOOSE_POS -> { tsyms = EUR_TYPE}
            CoinPriceActivity.JPY_CHOOSE_POS -> { tsyms = JPY_TYPE}
        }
        getCrypto(tsyms)
    }

    /**
     * Fix, modify USD choose with spinner later
     */
    private fun getCrypto(tsyms: String) {
        userRepository.getCryptoPrice(FSYMS, tsyms)
                .subscribe(
                        {
                            this@CoinPriceViewModel.coins.value = it
                            isLoadingData.value = false
                        },
                        {
                            isLoadingData.value = false
                        }
                )
    }
}