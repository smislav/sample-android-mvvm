package com.githubapp.mvvm.api.models

import com.google.gson.annotations.SerializedName

data class Authorization(
        @SerializedName("id") var id: String,
        @SerializedName("token") var token: String)