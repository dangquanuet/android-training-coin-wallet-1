package com.framgia.bitcoinwallet.data.source.local

import com.framgia.bitcoinwallet.data.model.User
import com.framgia.bitcoinwallet.data.source.UserDataSource
import com.framgia.bitcoinwallet.data.source.remote.UserRemoteDatasource
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserLocalDatasource : UserDataSource.local {

    companion object {
        private var INSTANCE: UserLocalDatasource? = null
        @JvmStatic
        fun getInstance(): UserLocalDatasource {
            if (INSTANCE == null) {
                INSTANCE = UserLocalDatasource()
            }
            return INSTANCE!!
        }
    }

    override fun saveUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}