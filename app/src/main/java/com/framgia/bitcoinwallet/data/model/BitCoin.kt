package com.framgia.bitcoinwallet.data.model

/**
 * Created: 05/07/2018
 * By: Sang
 * Description:
 */
data class BitCoin(val id: String, var image: Int, var name: String, var price: String) {
    fun convertPriceToFloat() = price.substring(price.indexOf(" ")).toFloat()
}
