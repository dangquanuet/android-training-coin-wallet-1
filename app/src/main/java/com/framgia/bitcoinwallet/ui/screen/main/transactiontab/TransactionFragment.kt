package com.framgia.bitcoinwallet.ui.screen.main.transactiontab

import android.view.View
import android.widget.Toast
import com.framgia.bitcoinwallet.R
import com.framgia.bitcoinwallet.databinding.FragmentTransactionBinding
import com.framgia.bitcoinwallet.ui.BaseFragment
import com.framgia.bitcoinwallet.ui.screen.main.MainActivity
import com.framgia.bitcoinwallet.ui.screen.main.MainViewModel
import com.framgia.bitcoinwallet.util.obtainViewModel

class TransactionFragment : BaseFragment<FragmentTransactionBinding>() {

    private lateinit var mainViewModel: MainViewModel

    companion object {
        fun newInstance() = TransactionFragment()
        private const val TAG = "TransactionFragment"
    }

    override fun getLayoutRes() = R.layout.fragment_transaction

    override fun initData() {
        viewDataBinding.apply {
            viewModel = (activity as MainActivity).obtainViewModel(TransactionViewModel::class.java)
            viewModel?.let { lifecycle.addObserver(it) }
        }

        mainViewModel = (activity as MainActivity).obtainViewModel(MainViewModel::class.java)
    }

    override fun observeModelData(view: View) {

    }

    override fun setEvents(view: View) {

    }

    private fun notifyMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}