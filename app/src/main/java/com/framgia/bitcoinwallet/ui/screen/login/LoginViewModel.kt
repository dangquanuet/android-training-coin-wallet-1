package com.framgia.bitcoinwallet.ui.screen.login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.ui.screen.main.MainActivity
import com.framgia.bitcoinwallet.util.SharedPreUtils

class LoginViewModel(private val context: Application, private val userRepository: UserRepository)
    : AndroidViewModel(context) {

    val notifyMessage: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    fun clickSignIn() {
        loading.value = true
        userRepository.singIn(email.value.toString(), password.value.toString()).subscribe({
            loading.value = false
            when (it.id) {
                null -> {
                    notifyMessage.value = context.getString(R.string.login_failed_title)
                }
                else -> {
                    SharedPreUtils.changeLoginState(context, true)
                    //SharedPreUtils.saveUserId(context, it.id)
                    SharedPreUtils.saveUserId(context, "1")
                    SharedPreUtils.saveWalletAddress(context,"1")
                    context.startActivity(MainActivity.getMainIntent(context))
                }
            }
        }) {
            loading.value = false
            notifyMessage.value = it.message
        }
    }
}
