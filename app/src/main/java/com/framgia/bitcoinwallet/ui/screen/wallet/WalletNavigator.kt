package com.framgia.bitcoinwallet.ui.screen.wallet

import com.framgia.bitcoinwallet.data.model.Wallet

interface WalletNavigator {
    fun goWalletDetailsScreen(item: Wallet)
}