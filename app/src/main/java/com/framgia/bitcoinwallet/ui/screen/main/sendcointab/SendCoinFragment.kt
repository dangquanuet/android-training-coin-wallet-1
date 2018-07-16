package com.framgia.bitcoinwallet.ui.screen.main.sendcointab

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.view.View
import com.framgia.bitcoinwallet.databinding.FragmentSendCoinBinding
import kotlinx.android.synthetic.main.fragment_send_coin.view.*
import android.os.Build
import android.widget.Toast
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.ui.BaseFragment
import com.framgia.bitcoinwallet.ui.screen.main.MainActivity
import com.framgia.bitcoinwallet.ui.screen.main.MainViewModel
import com.framgia.bitcoinwallet.util.obtainViewModel
import com.google.zxing.integration.android.IntentIntegrator

class SendCoinFragment : BaseFragment<FragmentSendCoinBinding>(), SendCoinNavigator {

    private lateinit var mainViewModel: MainViewModel

    companion object {
        fun newInstance() = SendCoinFragment()
        private const val TAG = "SendCoinFragment"
    }

    override fun getLayoutRes() = R.layout.fragment_send_coin

    override fun initData() {
        viewDataBinding.apply {
            viewModel = (activity as MainActivity).obtainViewModel(SendCoinViewModel::class.java)
            viewModel?.let { lifecycle.addObserver(it) }
        }

        mainViewModel = (activity as MainActivity).obtainViewModel(MainViewModel::class.java)
    }

    override fun startScanScreen() {
        IntentIntegrator(activity).initiateScan()
    }

    override fun observeModelData(view: View) {
        //when current balance is updated by send, update this data in MainViewModel
        // then Receiver Tab can knowing that change
        viewDataBinding.viewModel?.curentBalance?.observe(this, Observer {
            mainViewModel.currentBalance?.value = it
        })

        viewDataBinding.viewModel?.showAlertDialog?.observe(this, Observer {
            if (it != null && it) {
                showVerifySendDialog(view)
            }
        })

        viewDataBinding.viewModel?.sendCoinState?.observe(this, Observer {
            if (it != null && it) {
                notifyMessage(getString(R.string.send_coin_successed))
            } else {
                notifyMessage(getString(R.string.send_coin_failed))
            }
        })

        //Scanner return result to activity, so we need to observe MainViewModel to get this result
        mainViewModel.addressCoinScanned?.observe(this, Observer {
            viewDataBinding.viewModel?.handleScanResults(it.toString())
        })
    }

    override fun setEvents(view: View) {
        view.button_send_coin.setOnClickListener {
            viewDataBinding.viewModel?.verifySendCoin(view.edit_bitcoin_add.text.toString(),
                    view.edit_amount.text.toString())
        }

        view.image_scan.setOnClickListener { startScanScreen() }
    }

    private fun showVerifySendDialog(view: View) {
        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)
        } else {
            builder = AlertDialog.Builder(context)
        }
        builder.setTitle(getString(R.string.dialog_send_title))
                .setMessage(getString(R.string.dialog_send_message))
                .setPositiveButton(android.R.string.yes) { dialog, which ->
                    viewDataBinding.viewModel?.resetVariableState()
                    viewDataBinding.viewModel?.sendCoin(view.edit_bitcoin_add.text.toString(),
                            view.edit_amount.text.toString(), view.edit_note.text.toString())
                }
                .setNegativeButton(android.R.string.no) { dialog, which ->
                    viewDataBinding.viewModel?.resetVariableState()
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()

    }

    private fun notifyMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}
