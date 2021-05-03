package com.saretest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saretest.data.api.MainRepository
import com.saretest.data.model.UserData
import com.saretest.utils.Resource

class MainViewModel(val mainRepository: MainRepository) : ViewModel() {

    val userdata = MutableLiveData<Resource<UserData>>()

    init {
        getUserData()
    }

    private fun getUserData() {
        mainRepository.getUserData({
            userdata.postValue(Resource.success(it))
        }, {
            userdata.postValue(Resource.error(it))
        })
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.compositeDisposable.dispose()
    }
}