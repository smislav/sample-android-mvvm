package com.githubapp.mvvm.api.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class AuthorizationRequest(
        @SerializedName("scopes") var scopes: ArrayList<String> = arrayListOf("user"),
        @SerializedName("note") var note: String)