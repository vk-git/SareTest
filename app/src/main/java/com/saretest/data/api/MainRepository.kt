package com.saretest.data.api

import com.saretest.data.model.UserData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainRepository(private val apiService: ApiService) {

    val compositeDisposable = CompositeDisposable()

    fun getUserData(onSuccess: (UserData?) -> Unit, onError: (String) -> Unit) {
        apiService.getUserData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                if(it.isSuccessful) {
                    onSuccess(it.body())
                } else {
                    onError(it.message())
                }
            },{
                it.message?.let { it1 -> onError(it1) }
            }).let { compositeDisposable.add(it) }
    }
}