package com.framgia.bitcoinwallet.ui.screen.coinprice

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.BitCoin
import com.framgia.bitcoinwallet.databinding.ActivityCoinPriceBinding
import com.framgia.bitcoinwallet.ui.BaseActivity
import com.framgia.bitcoinwallet.ui.BaseRecyclerViewHolder
import com.framgia.bitcoinwallet.util.SharedPreUtils
import com.framgia.bitcoinwallet.util.obtainViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_coin_price.*
import kotlinx.android.synthetic.main.activity_wallet.*

class CoinPriceActivity : BaseActivity<ActivityCoinPriceBinding>(),
        BaseRecyclerViewHolder.OnItemClickListener<BitCoin> {

    private var coinAdapter: CoinAdapter? = null

    companion object {
        const val USD_CHOOSE_POS = 0
        const val VND_CHOOSE_POS = 1
        const val EUR_CHOOSE_POS = 2
        const val JPY_CHOOSE_POS = 3
        private const val TAG = "CoinPriceActivity"
        fun getCoinPriceWallet(context: Context): Intent =
                Intent(context, CoinPriceActivity::class.java)
    }

    override fun initComponents() {
        initViewModel()

        if (coinAdapter == null) {
            coinAdapter = CoinAdapter(mutableListOf(), this)
            recycler_coin.adapter = coinAdapter
        }

        spinner_coin_type.adapter =
                ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        binding.viewModel?.getCoinType())

        when (SharedPreUtils.getCoinTypeChoosed(this)) {
            CoinPriceViewModel.USD_TYPE -> {
                spinner_coin_type.setSelection(USD_CHOOSE_POS)
            }
            CoinPriceViewModel.VND_TYPE -> {
                spinner_coin_type.setSelection(VND_CHOOSE_POS)
            }
            CoinPriceViewModel.EUR_TYPE -> {
                spinner_coin_type.setSelection(EUR_CHOOSE_POS)
            }
            CoinPriceViewModel.JPY_TYPE -> {
                spinner_coin_type.setSelection(JPY_CHOOSE_POS)
            }
        }
    }

    override fun navigateLayout() = false

    override fun getLayoutRes() = R.layout.activity_coin_price

    override fun setEvents() {
        image_back.setOnClickListener { finish() }
        spinner_coin_type.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.viewModel?.getCrypto(p2)
            }

        }
    }

    override fun observeViewModel() {
        binding.viewModel?.lineData?.observe(this, Observer {
            it?.let {
                chart_prices.apply {
                    data = it
                    invalidate()
                }
            }
        })
    }

    override fun onItemClick(position: Int, data: BitCoin) {

    }

    private fun initViewModel() {
        binding.viewModel = this@CoinPriceActivity.obtainViewModel(CoinPriceViewModel::class.java)
                .apply {
                    lifecycle.addObserver(this)
                }
    }
}
