package com.framgia.bitcoinwallet.ui.screen.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import com.framgia.bitcoinwallet.data.source.repository.UserRepository

class MainViewModel(private val context: Application,
                    private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    val currentBalance: MutableLiveData<String> = MutableLiveData()
    val addressCoinScanned: MutableLiveData<String> = MutableLiveData()
}
