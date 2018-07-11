package com.framgia.bitcoinwallet.data.model

data class User(var id: String? = "", var email: String? = "", var fullName: String? = "", var password: String? = "",
                var phoneNumber: String? = "",
                var country:String? = "",
                var wallet: List<Wallet>? = null,
                var transaction: Transaction? = null)