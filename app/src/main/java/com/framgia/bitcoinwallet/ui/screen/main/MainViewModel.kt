package com.framgia.bitcoinwallet.ui.screen.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.util.SharedPreUtils
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(private val context: Application,
                    private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    val currentBalance: MutableLiveData<String> = MutableLiveData()
    val addressCoinScanned: MutableLiveData<String> = MutableLiveData()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getDefaultWallet() {
        userRepository.getUserWallets(SharedPreUtils.getUserId(context))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            when (it) {
                                null -> {
                                }
                                else -> {
                                    SharedPreUtils.saveWalletAddress(context, it[0].id)
                                    dataLoading.value = false
                                }
                            }

                        },
                        {

                        }
                )
    }
}
