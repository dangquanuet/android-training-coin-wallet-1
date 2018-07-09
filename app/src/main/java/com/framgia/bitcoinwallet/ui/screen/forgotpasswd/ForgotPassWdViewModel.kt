package com.framgia.bitcoinwallet.ui.screen.forgotpasswd

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.util.calculateTimeDuration
import com.framgia.bitcoinwallet.util.emailValidate
import io.reactivex.functions.Consumer
import java.util.*

class ForgotPassWdViewModel(
        private val context: Application,
        private val userRepository: UserRepository
) : AndroidViewModel(context), LifecycleObserver {

    val visibleCodeInputUi: MutableLiveData<Boolean> = MutableLiveData()
    val lastSendVerify: MutableLiveData<Date> = MutableLiveData()
    val notifyMessage: MutableLiveData<String> = MutableLiveData()

    fun sendEmail(email: String) {
        email?.run {
            when (emailValidate(this)) {
                true -> {
                    visibleCodeInputUi.value = true
                    userRepository.forgotPassword(email).subscribe(
                             {
                                lastSendVerify?.value = Date()
                                notifyMessage.value =
                                        context.resources.getString(R.string.success_verify_request)
                            },
                             {
                                notifyMessage.value =
                                        context.resources.getString(R.string.failed_verify_request)
                            }
                    )
                }
                else -> notifyMessage.value = context.resources.getString(R.string.email_invalid)
            }
        }
    }

    fun retryVerify(email: String) {
        val timeDuration: Long =
                lastSendVerify?.value?.calculateTimeDuration(Date()) ?: 0

        when (timeDuration) {
            in 1..60 -> {
                val remain: Long = 61 - timeDuration
                val retryMes = context.resources.getString(R.string.retry_verify_request)
                notifyMessage.value = "$retryMes" +
                        " $remain seconds"
            }
            else -> {
                sendEmail(email)
            }
        }
    }
}
