package com.framgia.bitcoinwallet

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.framgia.bitcoinwallet.data.source.local.UserLocalDatasource
import com.framgia.bitcoinwallet.data.source.remote.UserRemoteDatasource
import com.framgia.bitcoinwallet.data.source.repository.BitCoinRepository
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.ui.screen.forgotpasswd.ForgotPassWdViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ViewModelFactory private constructor(
        private val application: Application,
        private val bitcointRepository: BitCoinRepository,
        private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(ForgotPassWdViewModel::class.java) ->
                        ForgotPassWdViewModel(application, userRepository)

                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T


    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) = INSTANCE ?: ViewModelFactory(application,
                BitCoinRepository.getInstance(),
                UserRepository.getInstance(
                        UserRemoteDatasource.getInstance(FirebaseAuth.getInstance(),
                                FirebaseDatabase.getInstance()),
                        UserLocalDatasource.getInstance()))

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
