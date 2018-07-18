package com.framgia.bitcoinwallet.ui.screen.detailswallet

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.util.Log
import com.framgia.bitcoinwallet.data.model.Wallet
import com.framgia.bitcoinwallet.data.source.repository.UserRepository
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class DetailsWalletViewModel(private val context: Application,
                             private val userRepository: UserRepository)
    : AndroidViewModel(context), LifecycleObserver {

    val wallet: MutableLiveData<Wallet> = MutableLiveData()
    val bitmapQrCode: MutableLiveData<Bitmap> = MutableLiveData()

    fun updateUi(wallet: Wallet) {
        this@DetailsWalletViewModel.wallet.value = wallet
        gererateQrCode()
    }

    private fun gererateQrCode() {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter
                    .encode(wallet.value?.id, BarcodeFormat.QR_CODE, 1000, 1000)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            bitmapQrCode.value = bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}
