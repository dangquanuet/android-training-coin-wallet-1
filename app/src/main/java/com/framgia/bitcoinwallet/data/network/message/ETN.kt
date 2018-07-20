package com.framgia.bitcoinwallet.data.network.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ETN(@SerializedName("VND")
          @Expose
          var vND: Double,
          @SerializedName("USD")
          @Expose
          var uSD: Double,
          @SerializedName("EUR")
          @Expose
          var eUR: Double,
          @SerializedName("JPY")
          @Expose
          var jPY: Double)
