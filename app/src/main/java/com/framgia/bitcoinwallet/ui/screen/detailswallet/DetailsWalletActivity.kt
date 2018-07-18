package com.framgia.bitcoinwallet.ui.screen.detailswallet

import android.content.Context
import android.content.Intent
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.databinding.ActivityDetailsWalletBinding
import com.framgia.bitcoinwallet.ui.BaseActivity
import com.framgia.bitcoinwallet.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_details_wallet.*

class DetailsWalletActivity : BaseActivity<ActivityDetailsWalletBinding>() {

    private lateinit var currentWallet: Wallet

    companion object {
        private const val TAG = "DetailsWalletActivity"
        private const val SHARE_TITLE = "Choose sharing method"
        private const val SHARE_SUBJECT = "Subject/Title"
        private const val SHARE_TYPE = "text/plain"
        private const val EXTRA_WALLET = "com.framgia.bitcoinwallet.ui.screen.detailswallet.EXTRA_WALLET"
        fun getDetailsWallet(context: Context, wallet: Wallet): Intent =
                Intent(context, DetailsWalletActivity::class.java).apply {
                    putExtra(EXTRA_WALLET, wallet)
                }
    }

    override fun navigateLayout(): Boolean {
        return false
    }

    override fun getLayoutRes() = R.layout.activity_details_wallet

    override fun initComponents() {
        getIntentData()
        initViewModel()
    }

    override fun setEvents() {
        image_back.setOnClickListener { finish() }
        text_share_wallet.setOnClickListener { shareWallet() }
    }

    override fun observeViewModel() {

    }

    private fun getIntentData() {
        currentWallet = intent.getSerializableExtra(EXTRA_WALLET) as Wallet
    }

    private fun initViewModel() {
        binding.viewModel = this@DetailsWalletActivity.obtainViewModel(DetailsWalletViewModel::class.java).apply {
            lifecycle.addObserver(this)
            updateUi(currentWallet)
        }
    }

    private fun shareWallet() {
        startActivity(Intent.createChooser(Intent(android.content.Intent.ACTION_SEND).apply {
            type = SHARE_TYPE
            putExtra(android.content.Intent.EXTRA_SUBJECT, SHARE_SUBJECT)
            putExtra(android.content.Intent.EXTRA_TEXT, currentWallet.id)
        }, SHARE_TITLE))
    }
}
