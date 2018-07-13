package com.framgia.bitcoinwallet.data.source.repository

import com.framgia.bitcoinwallet.data.model.Receiver
import com.framgia.bitcoinwallet.data.model.SendCoin
import com.framgia.bitcoinwallet.data.model.Transaction
import com.framgia.bitcoinwallet.data.model.User
import com.framgia.bitcoinwallet.data.source.UserDataSource
import com.framgia.bitcoinwallet.data.source.local.UserLocalDatasource
import com.framgia.bitcoinwallet.data.source.remote.UserRemoteDatasource
import io.reactivex.Observable
import io.reactivex.Single

class UserRepository(private val userRemoteDatasource: UserRemoteDatasource,
                     private val userLocalDatasource: UserLocalDatasource?) : UserDataSource {

    companion object {
        private var INSTANCE: UserRepository? = null
        @JvmStatic
        fun getInstance(userRemoteDatasource: UserRemoteDatasource,
                        userLocalDatasource: UserLocalDatasource?): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(userRemoteDatasource, userLocalDatasource)
            }
            return INSTANCE!!
        }
    }

    override fun singIn(email: String, password: String): Single<User> {
        return userRemoteDatasource.singIn(email, password)
    }

    override fun signUp(email: String, password: String): Single<User> {
        return userRemoteDatasource.signUp(email, password)
    }

    override fun forgotPassword(email: String): Single<Boolean> {
        return userRemoteDatasource.forgotPassword(email)
    }

    override fun getInforUser(idUser: String): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInforTransactionUser(idUser: String): Single<Transaction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentUserId(): Single<String> {
        return userRemoteDatasource.getCurrentUserId()
    }

    override fun getCurrentBalance(idUser: String, idWallet: String): Observable<Float> {
        return userRemoteDatasource.getCurrentBalance(idUser, idWallet)
    }

    override fun checkCoinAddressExist(addressCoin: String): Single<Receiver> {
        return userRemoteDatasource.checkCoinAddressExist(addressCoin)
    }

    override fun sendCoin(sendCoin: SendCoin, receiver: Receiver, receiverStringRef: String,
                          senderStringRef: String, currentBalance: Float): Single<Boolean> {
        return userRemoteDatasource.sendCoin(sendCoin,receiver, receiverStringRef
                , senderStringRef, currentBalance)
    }

    override fun updateUserTransaction(stringRef: String) {

    }
}
