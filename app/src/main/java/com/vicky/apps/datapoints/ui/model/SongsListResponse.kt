package com.vicky.apps.datapoints.ui.model


import com.google.gson.annotations.SerializedName

data class SongsListResponse(
    @SerializedName("artists")
    var artists: String,
    @SerializedName("cover_image")
    var coverImage: String,
    @SerializedName("song")
    var song: String,
    @SerializedName("url")
    var url: String
)