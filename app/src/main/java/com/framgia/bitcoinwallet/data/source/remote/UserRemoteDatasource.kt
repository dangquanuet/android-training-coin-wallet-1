package com.framgia.bitcoinwallet.data.source.remote

import com.framgia.bitcoinwallet.data.model.Transaction
import com.framgia.bitcoinwallet.data.model.User
import com.framgia.bitcoinwallet.data.source.UserDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRemoteDatasource : UserDataSource {
    private var mAuth: FirebaseAuth
    private var mFireDatabase: FirebaseDatabase

    private constructor(auth: FirebaseAuth, dataBase: FirebaseDatabase) {
        mAuth = auth
        mFireDatabase = dataBase
    }

    companion object {
        private var INSTANCE: UserRemoteDatasource? = null
        @JvmStatic
        fun getInstance(mAuth: FirebaseAuth, dataBase: FirebaseDatabase): UserRemoteDatasource {
            if (INSTANCE == null) {
                INSTANCE = UserRemoteDatasource(mAuth, dataBase)
            }
            return INSTANCE!!
        }
    }

    override fun singIn(email: String, password: String): Single<User> {
        return Single.create<User> { e ->
            var user: User
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    var userFirebase = mAuth.currentUser
                    user = User(userFirebase!!.uid, userFirebase.email!!)
                    e.onSuccess(user)
                }
            }.addOnFailureListener {
                e.onError(it)
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun forgotPassword(email: String): Single<Boolean> {
        return Single.create<Boolean>({ e ->
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                e.onSuccess(it.isSuccessful)
            }
                    .addOnFailureListener {
                        e.onError(it)
                    }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getInforUser(idUser: String): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInforTransactionUser(idUser: String): Single<Transaction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signUp(email: String, password: String): Single<User> {
        return Single.create<User> { e ->
            var user: User
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userFirebase = mAuth.currentUser
                    user = User(userFirebase?.uid, userFirebase?.email)
                    e.onSuccess(user)
                } else {
                    e.onError(it.exception!!)
                }
            }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}