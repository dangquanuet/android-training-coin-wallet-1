package com.framgia.bitcoinwallet.ui.screen.changepassws

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.util.emailValidate
import com.google.firebase.auth.EmailAuthProvider

class ChangePassWdViewModel(private val context: Application,
                            private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    val notifyMessage: MutableLiveData<String> = MutableLiveData()

    fun changePassWord(email: String, passWd: String, newPassWd: String) {
        if (validateEmail(email) && validatePassWd(passWd, newPassWd)) {
            val credential = EmailAuthProvider
                    .getCredential(email, passWd)
            userRepository.reAuth(credential).subscribe(
                    {
                        when (it) {
                            true -> {
                                userRepository.changePassWord(newPassWd).subscribe(
                                        {
                                            when (it) {
                                                newPassWd -> {
                                                    notifyMessage.value = context.getString(R.string.change_passwd_successed)
                                                }
                                                else -> {
                                                    notifyMessage.value = context.getString(R.string.change_passwd_failed)
                                                }
                                            }
                                        },
                                        {
                                            notifyMessage.value = it.message
                                        }
                                )
                            }
                            else -> {
                                notifyMessage.value = context.getString(R.string.invalid_email_passwd)
                            }
                        }
                    },
                    {
                        notifyMessage.value = it.message
                    }
            )
        }
    }

    private fun validateEmail(email: String) = email.emailValidate(email)
    private fun validatePassWd(passWd: String, newPassWd: String) =
            passWd.isNotEmpty() && newPassWd.isNotEmpty()
}
