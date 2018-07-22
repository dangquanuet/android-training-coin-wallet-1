package com.framgia.bitcoinwallet.ui.screen.main.transactiontab

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import com.framgia.bitcoinwallet.data.model.Transaction
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.util.SharedPreUtils
import io.reactivex.rxkotlin.subscribeBy

class TransactionViewModel(private val context: Application,
                           private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    companion object {
        const val TAG = "TransactionViewModel"
    }

    val isLoadingData: MutableLiveData<Boolean> = MutableLiveData()
    val isSendTransactionShowed: MutableLiveData<Boolean> = MutableLiveData()
    val isShowNoSendTransactionTitle: MutableLiveData<Boolean> = MutableLiveData()
    val isShowNoReceiveTransactionTitle: MutableLiveData<Boolean> = MutableLiveData()
    val sendTransaction: MutableLiveData<List<Transaction>> = MutableLiveData()
    val receiveTransaction: MutableLiveData<List<Transaction>> = MutableLiveData()

    //Don't get balance at this frg, because it is loaded in sendcoin frg and save in MainViewModel
    //So we only obsever that var in MainViewModel
    val curentBalance: MutableLiveData<String> = MutableLiveData()

    var isHasSendTransaction: Boolean = false
    var isHasReceiveTransaction: Boolean = false

    init {
        isLoadingData.value = true
        isSendTransactionShowed.value = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startViewModel() {
        getSendTransaction()
        getReceiveTransaction()
    }

    fun showSendTransaction() {
        isSendTransactionShowed.value = true
        if (isHasSendTransaction) {
            isShowNoSendTransactionTitle.value = false
            isShowNoReceiveTransactionTitle.value = false
        } else {
            isShowNoSendTransactionTitle.value = true
            isShowNoReceiveTransactionTitle.value = false
        }
    }

    fun showReceiveTransaction() {
        isSendTransactionShowed.value = false
        if (isHasReceiveTransaction) {
            isShowNoSendTransactionTitle.value = false
            isShowNoReceiveTransactionTitle.value = false
        } else {
            isShowNoSendTransactionTitle.value = false
            isShowNoReceiveTransactionTitle.value = true
        }
    }

    private fun getSendTransaction() {
        userRepository.getSendTransaction(SharedPreUtils.getUserId(context)
                , SharedPreUtils.getCurrentWalletAddress(context)).subscribeBy(
                onError = {
                    Log.e(TAG, it.toString())
                    isLoadingData.value = false
                },
                onNext = {
                    if (it.isEmpty()) {
                        isShowNoSendTransactionTitle.value = true
                        isHasSendTransaction = false
                        sendTransaction.value = mutableListOf<Transaction>()

                    } else {
                        sendTransaction.value = it
                        isHasSendTransaction = true
                    }
                    isLoadingData.value = false
                    showSendTransaction()
                }
        )
    }

    private fun getReceiveTransaction() {
        userRepository.getReceiveTransaction(SharedPreUtils.getUserId(context)
                , SharedPreUtils.getCurrentWalletAddress(context)).subscribeBy(
                onError = {
                    Log.e(TAG, it.toString())
                },
                onNext = {
                    if (it.isEmpty()) {
                        isHasReceiveTransaction = false
                        receiveTransaction.value = mutableListOf<Transaction>()

                    } else {
                        receiveTransaction.value = it
                        isHasReceiveTransaction = true
                    }
                }
        )
    }
}
