package com.framgia.bitcoinwallet.ui.screen.signup

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.model.User
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.ui.screen.main.MainActivity
import com.framgia.bitcoinwallet.util.SharedPreUtils

class SignUpViewModel(private var context: Application, private var userRepository: UserRepository) : ViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()
    val rePassword: MutableLiveData<String> = MutableLiveData()
    val notifyMessage: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loading.value = false
    }

    fun onRegister() {
        if (validatePass()) {
            loading.value = true
            userRepository.signUp(user.value).subscribe({
                loading.value = false
                SharedPreUtils.changeLoginState(context, true)
                it.id?.let { it1 -> SharedPreUtils.saveUserId(context, it1) }
                context.startActivity(MainActivity.getMainIntent(context))
            }, {
                notifyMessage.value = it.message
                loading.value = false
            })
        }
    }

    private fun validatePass(): Boolean {
        if (user.value?.password.isNullOrBlank()) {
            notifyMessage.value = context.getString(R.string.err_pass_is_null_blank)
            return false
        }
        if (!user.value?.password.equals(rePassword.value)) {
            notifyMessage.value = context.getString(R.string.err_pass_not_match)
            return false
        }
        return true
    }
}