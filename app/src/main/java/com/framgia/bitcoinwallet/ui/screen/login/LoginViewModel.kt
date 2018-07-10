package com.framgia.bitcoinwallet.ui.screen.login

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.ui.screen.main.MainActivity
import com.framgia.bitcoinwallet.util.SharedPreUtils

class LoginViewModel(private val context: Application, private val userRepository: UserRepository) : ViewModel() {

    val notifyMessage: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    fun clickSignIn() {
        loading.setValue(true)
        userRepository.singIn(email.value.toString(), password.value.toString()).subscribe({
            loading.setValue(false)
            SharedPreUtils.changeLoginState(context, true)
            it.id?.let { it1 -> SharedPreUtils.saveUserId(context, it1) }
            context.startActivity(MainActivity.getMainIntent(context))
        }, {
            loading.setValue(false)
            notifyMessage.value = it.message
        })
    }
}