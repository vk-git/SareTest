package com.saretest.data.api

import com.saretest.data.model.UserData
import retrofit2.Response
import retrofit2.http.GET
import io.reactivex.rxjava3.core.Observable;

interface ApiService {

    @GET("output")
    fun getUserData(): Observable<Response<UserData>>
}