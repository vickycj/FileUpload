package com.vicky.apps.datapoints.data.remote


import com.vicky.apps.datapoints.ui.model.SongsListResponse
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {
    @GET("/studio")
    fun getDataFromService(): Single<List<SongsListResponse>>
}
