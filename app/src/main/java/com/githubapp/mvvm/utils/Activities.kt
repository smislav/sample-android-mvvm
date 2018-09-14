package com.githubapp.mvvm.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle

fun startBrowser(context: Context, link: String){
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    intent.data = Uri.parse(link)

    context.startActivity(intent)
}