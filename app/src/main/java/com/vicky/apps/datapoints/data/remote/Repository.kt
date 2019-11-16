package com.vicky.apps.datapoints.data.remote

import com.vicky.apps.datapoints.ui.model.SongsListResponse
import io.reactivex.Single

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {

    fun getDataFromApi(): Single<List<SongsListResponse>> = apiService.getDataFromService()

}