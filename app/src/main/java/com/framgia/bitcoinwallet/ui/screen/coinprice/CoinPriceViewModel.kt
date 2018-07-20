package com.framgia.bitcoinwallet.ui.screen.coinprice

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.BitCoin
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.util.SharedPreUtils
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

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
        const val CHART_PRICE_TITLE = "Crypto Chart"
    }

    val isLoadingData: MutableLiveData<Boolean> = MutableLiveData()
    val coins: MutableLiveData<MutableList<BitCoin>> = MutableLiveData()
    val lineData: MutableLiveData<LineData> = MutableLiveData()

    private lateinit var coinType: String

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startViewModel() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun saveCoinTypeChoosed() {
        SharedPreUtils.saveCoinTypeChoosed(context, coinType)
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
            CoinPriceActivity.USD_CHOOSE_POS -> {
                tsyms = USD_TYPE
            }
            CoinPriceActivity.VND_CHOOSE_POS -> {
                tsyms = VND_TYPE
            }
            CoinPriceActivity.EUR_CHOOSE_POS -> {
                tsyms = EUR_TYPE
            }
            CoinPriceActivity.JPY_CHOOSE_POS -> {
                tsyms = JPY_TYPE
            }
        }
        getCrypto(tsyms)
    }

    private fun getCrypto(tsyms: String) {
        coinType = tsyms
        userRepository.getCryptoPrice(FSYMS, tsyms)
                .subscribe(
                        {
                            this@CoinPriceViewModel.coins.value = it
                            isLoadingData.value = false
                            updatePriceChart()
                        },
                        {
                            isLoadingData.value = false
                        }
                )
    }

    private fun updatePriceChart() {
        var entries: ArrayList<Entry> = ArrayList<Entry>()
        coins.value?.let {
            for (bitcoin: BitCoin in it) {
                entries.add(Entry(it.indexOf(bitcoin).toFloat(), bitcoin.convertPriceToFloat()))
            }
        }
        lineData.value = LineData(LineDataSet(entries, CHART_PRICE_TITLE).apply {
            color = R.color.color_deep_purple_50_700
            valueTextColor = R.color.color_deep_purple_50_700
            mode = LineDataSet.Mode.CUBIC_BEZIER
            //setDrawCircles(true)
            //setCircleColors(R.color.color_deep_purple_50_700)
        })
    }
}
