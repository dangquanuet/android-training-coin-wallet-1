package com.framgia.bitcoinwallet.data.network.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoResponse(
        @SerializedName("BTC")
        @Expose
        var bTC: BTC,
        @SerializedName("ETH")
        @Expose
        var eTH: ETH,
        @SerializedName("XMR")
        @Expose
        var xMR: XMR,
        @SerializedName("LTC")
        @Expose
        var lTC: LTC,
        @SerializedName("DASH")
        @Expose
        var dASH: DASH,
        @SerializedName("BCH")
        @Expose
        var bCH: BCH,
        @SerializedName("ZEC")
        @Expose
        var zEC: ZEC,
        @SerializedName("ETC")
        @Expose
        var eTC: ETC,
        @SerializedName("XRP")
        @Expose
        var xRP: XRP,
        @SerializedName("ETN")
        @Expose
        var eTN: ETN
)