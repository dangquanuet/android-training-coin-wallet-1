package com.framgia.bitcoinwallet.data.source

import com.framgia.bitcoinwallet.data.model.Transaction
import com.framgia.bitcoinwallet.data.model.User
import io.reactivex.Single

interface UserDataSource {
    fun singIn(email: String, password: String): Single<User>
    fun signUp(email: String, password: String): Single<User>
    fun forgotPassword(email: String): Single<Boolean>
    fun getInforUser(idUser: String): Single<User>
    fun getInforTransactionUser(idUser: String): Single<Transaction>
    interface local {
        fun saveUser(user: User)
        fun updateUser(user: User)
    }
}