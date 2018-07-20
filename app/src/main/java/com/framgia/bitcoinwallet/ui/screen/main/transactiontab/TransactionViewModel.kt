package com.framgia.bitcoinwallet.ui.screen.main.transactiontab

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import com.framgia.bitcoinwallet.data.source.repository.UserRepository

class TransactionViewModel(private val context: Application,
                           private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    companion object {
        const val TAG = "TransactionViewModel"
    }

    val isLoadingData: MutableLiveData<Boolean> = MutableLiveData()
}