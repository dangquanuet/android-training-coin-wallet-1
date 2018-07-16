package com.framgia.bitcoinwallet.ui.screen.main.receivecointab

import android.app.Application
import android.arch.lifecycle.*
import android.graphics.Bitmap
import android.util.Log
import com.framgia.bitcoinwallet.data.model.QrCode
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.framgia.bitcoinwallet.ui.screen.main.sendcointab.SendCoinViewModel
import com.framgia.bitcoinwallet.util.SharedPreUtils
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class ReceiveViewModel(private val context: Application,
                       private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    var loginState: Boolean = false
    var previousRequestAmount: Float = 0F
    val curentBalance: MutableLiveData<String> = MutableLiveData()
    val isLoadingData: MutableLiveData<Boolean> = MutableLiveData()
    val bitmapQrCode: MutableLiveData<Bitmap> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startViewModel() {
        getCurrentBalance()
        generateQrCode(generateStringQrCode())
    }

    fun requestNewQrCode(amount: Float) {
        if (checkDifferPreviousAmount(amount)) {
            generateQrCode(generateStringQrCode(amount))
            previousRequestAmount = amount
        }
    }

    fun generateStringQrCode(amount: Float): String {
        val currentAddress = SharedPreUtils.getCurrentWalletAddress(context)
        return if (currentAddress.isNotEmpty())
            Gson().toJson(QrCode(currentAddress, amount)).toString()
        else ""
    }

    private fun generateQrCode(inputCode: String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter
                    .encode(inputCode, BarcodeFormat.QR_CODE, 1000, 1000)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            bitmapQrCode.value = bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    /**
     * Get current balance base on user id and wallet id
     */
    private fun getCurrentBalance() {
        // fix login state is true, need to be check loginState
        // and get current User id, current wallet id later
        if (true) {
            userRepository.getCurrentUserId().subscribe(
                    {
                        userRepository
                                .getCurrentBalance(SharedPreUtils.getUserId(context)
                                        , SharedPreUtils.getCurrentWalletAddress(context))
                                .subscribe(
                                        {
                                            curentBalance.value = it.toString()
                                            isLoadingData.value = false
                                        },
                                        {
                                            Log.e(SendCoinViewModel.TAG, it.toString())
                                        }
                                )
                    },
                    {
                        Log.e(SendCoinViewModel.TAG, it.toString())
                    }
            )
        }
    }

    private fun generateStringQrCode(): String {
        val currentAddress = SharedPreUtils.getCurrentWalletAddress(context)
        return if (currentAddress.isNotEmpty())
            Gson().toJson(QrCode(currentAddress, null)).toString()
        else ""
    }

    private fun checkDifferPreviousAmount(amount: Float): Boolean = amount != previousRequestAmount
}
