package com.githubapp.api.models

import com.google.gson.annotations.SerializedName

data class AuthorizationRequest(
        @SerializedName("scopes") var scopes: ArrayList<String>,
        @SerializedName("note") var note: String) {
    companion object {
        const val SCOPE_USER = "user"
    }
}