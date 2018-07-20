package com.framgia.bitcoinwallet.data.network.api

import com.framgia.bitcoinwallet.data.network.message.CryptoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface BitcoinApi {
    @GET("pricemulti")
    fun getCryptoPrices(@Query("fsyms") fsyms: String,
                        @Query("tsyms") tsyms: String): Observable<CryptoResponse>
}
