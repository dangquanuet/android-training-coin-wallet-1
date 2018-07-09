package com.framgia.bitcoinwallet.data.source.repository

/**
 * Created: 05/07/2018
 * By: Sang
 * Description:
 */
class BitCoinRepository {
    companion object {
        private var INSTANCE: BitCoinRepository? = null

        fun getInstance() = INSTANCE ?: BitCoinRepository()
    }
}
