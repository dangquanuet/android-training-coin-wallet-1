package com.framgia.bitcoinwallet.ui.screen.main.sendcointab

import android.os.Bundle
import android.view.View
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseFragment

class SendCoinFragment: BaseFragment() {

    override fun initComponentsOnCreate(savedInstanceState: Bundle?) {

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_send_coin
    }

    override fun initComponentsOnCreateView(rootView: View, savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance() = SendCoinFragment()
        private const val TAG = "SendCoinFragment"
    }
}
