package com.framgia.bitcoinwallet.ui.screen.main.receivecointab

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.view.View
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.databinding.FragmentReceiveBinding
import com.framgia.bitcoinwallet.ui.BaseFragment
import com.framgia.bitcoinwallet.ui.screen.main.MainActivity
import com.framgia.bitcoinwallet.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_receive.view.*
import android.text.InputType
import android.widget.EditText
import com.framgia.bitcoinwallet.ui.screen.main.MainViewModel

class ReceiveFragment : BaseFragment<FragmentReceiveBinding>() {

    private lateinit var mainViewModel: MainViewModel

    companion object {
        fun newInstance() = ReceiveFragment()
        private const val TAG = "ReceiveFragment"
    }

    override fun getLayoutRes() = R.layout.fragment_receive

    override fun initData() {
        viewDataBinding.apply {
            viewModel = (activity as MainActivity).obtainViewModel(ReceiveViewModel::class.java)
            viewModel?.let { lifecycle.addObserver(it) }
        }

        mainViewModel = (activity as MainActivity).obtainViewModel(MainViewModel::class.java)
    }

    override fun observeModelData(view: View) {
        //when current balance is updated by send coin, update balance at this fragment
        mainViewModel.currentBalance.observe(this, Observer {
            viewDataBinding.viewModel?.curentBalance?.value = it
        })

    }

    override fun setEvents(view: View) {
        view.text_request_address.setOnClickListener { showEditDialog() }
    }

    private fun showEditDialog() {
        val alert = AlertDialog.Builder(activity)
        val edittext = EditText(activity)
        edittext.inputType = InputType.TYPE_CLASS_NUMBER
        alert.setMessage(getString(R.string.enter_amount_title))
        alert.setView(edittext)
        alert.setPositiveButton(getString(R.string.request_title)) { dialog, whichButton ->
            if (edittext.text.isNotEmpty()) {
                viewDataBinding.viewModel?.requestNewQrCode(edittext.text.toString().toFloat())
            }
        }
        alert.show()
    }
}
