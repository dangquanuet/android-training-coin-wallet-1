package com.framgia.bitcoinwallet.data.source

import com.framgia.bitcoinwallet.data.model.Receiver
import com.framgia.bitcoinwallet.data.model.SendCoin
import com.framgia.bitcoinwallet.data.model.Transaction
import com.framgia.bitcoinwallet.data.model.User
import io.reactivex.Single

interface UserDataSource {
    fun singIn(email: String, password: String): Single<User>
    fun signUp(email: String, password: String): Single<User>
    fun forgotPassword(email: String): Single<Boolean>
    fun getInforUser(idUser: String): Single<User>
    fun getCurrentUserId(): Single<String>
    fun getCurrentBalance(idUser: String, idWallet: String): Single<Float>
    fun sendCoin(sendCoin: SendCoin, receiver: Receiver, receiverStringRef: String
                 , senderStringRef: String, currentBalance: Float): Single<Boolean>
    fun updateUserTransaction(stringRef: String)
    fun checkCoinAddressExist(addressCoin: String): Single<Receiver>
    fun getInforTransactionUser(idUser: String): Single<Transaction>
    interface local {
        fun saveUser(user: User)
        fun updateUser(user: User)
    }
}
