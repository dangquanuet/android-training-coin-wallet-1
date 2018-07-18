package com.framgia.bitcoinwallet.ui.screen.main

import android.app.Application
import android.arch.lifecycle.*
import com.framgia.bitcoinwallet.data.model.User
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.util.SharedPreUtils
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(private val context: Application,
                    private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    val currentBalance: MutableLiveData<String> = MutableLiveData()
    val addressCoinScanned: MutableLiveData<String> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startViewModel() {
        getUserInfor(SharedPreUtils.getUserId(context))
    }

    fun getDefaultWallet() {
        userRepository.getUserWallets(SharedPreUtils.getUserId(context))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            when (it.size) {
                                0 -> {
                                    dataLoading.value = false
                                }
                                else -> {
                                    SharedPreUtils.saveWalletAddress(context, it[0].id)
                                    dataLoading.value = false
                                }
                            }
                        },
                        {
                            dataLoading.value = false
                        }
                )
    }

    private fun getUserInfor(idUser: String) {
        userRepository.getInforUser(idUser).subscribe(
                {
                    user.value = it
                },
                {

                }
        )
    }
}
