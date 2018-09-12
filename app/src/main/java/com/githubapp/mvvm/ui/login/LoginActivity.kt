package com.githubapp.mvvm.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.githubapp.mvvm.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       // AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener{ onLoginClick() }

    }

    fun onLoginClick(){

    }
}
