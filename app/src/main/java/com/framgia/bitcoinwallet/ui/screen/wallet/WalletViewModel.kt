package com.framgia.bitcoinwallet.ui.screen.wallet

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import java.util.ArrayList

class WalletViewModel(private val context: Application,
                      private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    val curentBalance: MutableLiveData<String> = MutableLiveData()
    val notifyMessage: MutableLiveData<String> = MutableLiveData()
    val wallets: MutableLiveData<ArrayList<Wallet>> = MutableLiveData()
    val newWalletAdded: MutableLiveData<Wallet> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startViewModel() {
        getCurrentBalance(getCurrentUserId(), getCurrentWalletAddress())
        getWalletsOfCurrentUser(getCurrentUserId())
    }

    fun changeCurrentWallet() {

    }

    fun addWallet(name: String) {
        wallets.value?.let {
            when (it.find { it.name == name }) {
                null -> {
                    userRepository.addWallet(getCurrentUserId(), name).subscribe(
                            {
                                wallets.value?.add(it)
                                newWalletAdded.value = it
                            },
                            {

                            }
                    )
                }
                else -> {
                    notifyMessage.value = context.getString(R.string.wallet_name_exist_title)
                }
            }
        }
    }

    private fun getWalletsOfCurrentUser(userId: String) {
        userRepository.getUserWallets(userId).subscribe(
                {
                    wallets.value = filterWallet(it)
                },
                {

                }
        )
    }

    /**
     * Filter current wallet out of list other wallet
     */
    private fun filterWallet(wallets: ArrayList<Wallet>?) = wallets?.apply {
        remove(wallets.find {
            it.id == getCurrentWalletAddress()
        })
    }

    /**
     * Get current balance base on user id and wallet id
     */
    private fun getCurrentBalance(idUser: String, idWallet: String) {
        // fix login state is true, need to be check loginState
        // and get current User id, current wallet id later
        if (true) {
            userRepository
                    .getCurrentBalance(idUser, idWallet)
                    .subscribe(
                            {
                                curentBalance.value = it.toString()
                            },
                            {
                                notifyMessage.value = context
                                        .getString(R.string.error_message)
                                Log.e(TAG, it.toString())
                            }
                    )
        }
    }

    private fun getCurrentUserId(): String {
        //fake data, need to be get from SharedPref
        return "1"
    }

    private fun getCurrentWalletAddress(): String {
        //fake data, need to be get from SharedPref
        return "erys346sdjh25346xzdh"
    }

    fun getWalletName(): String {
        //fake data, need to be get from SharedPref
        return "Dat's wallet"
    }

    companion object {
        const val TAG = "WalletViewModel"
    }
}
