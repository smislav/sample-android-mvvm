package com.githubapp.mvvm.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.githubapp.api.GithubApi
import com.githubapp.api.models.Authorization
import com.githubapp.api.models.AuthorizationRequest
import com.githubapp.data.models.User
import com.githubapp.helpers.SimpleCallbackWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Credentials
import java.util.*
import javax.inject.Inject

class LoginManager{
    interface BaseListener{
        fun onNetworkError()
        fun onAccessError()
        fun onUnknownError()
    }

    interface LoginListener: BaseListener {
        fun onLoginSucceeded()
    }

    interface UserListener: BaseListener {
        fun onUserRetrieved(user: User)
    }

    private var mContext: Context
    private var mGithubApi: GithubApi
    private var mPreferences: SharedPreferences

    @Inject
    constructor(context: Context, githubApi: GithubApi){
        mContext = context
        mGithubApi = githubApi
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
    }

    fun login(email: String, password: String, listener: LoginListener){
        val authorization: String? = Credentials.basic(email, password)

        val scopes = ArrayList<String>()
        scopes.add(AuthorizationRequest.SCOPE_USER)

        val notes = "GithubApp" + System.currentTimeMillis()

        val request = AuthorizationRequest(scopes, notes)

        mGithubApi.login(authorization!!, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: SimpleCallbackWrapper<Authorization>(){
                    override fun onNext(t: Authorization) {
                        saveAuthorization(t)
                        listener.onLoginSucceeded()
                    }

                    override fun onAccessError() {
                        listener.onAccessError()
                    }

                    override fun onNetworkError() {
                        listener.onNetworkError()
                    }

                    override fun onUnknownError() {
                        listener.onUnknownError()
                    }
                })
    }

    fun logout(){
        removeAuthorization()
    }

    fun getUser(listener: UserListener){
        val token: String = getAuthorization().token

        mGithubApi.getUserByToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: SimpleCallbackWrapper<User>(){
                    override fun onNext(t: User) {
                        listener.onUserRetrieved(t)
                    }

                    override fun onNetworkError() {
                        listener.onNetworkError()
                    }

                    override fun onAccessError() {
                        listener.onAccessError()
                    }

                    override fun onUnknownError() {
                        listener.onUnknownError()
                    }
                })
    }

    fun getAuthorization(): Authorization {
        return Authorization(
                mPreferences.getString("ID", ""),
                mPreferences.getString("TOKEN", ""))
    }

    private fun saveAuthorization(authorization: Authorization){
        mPreferences
                .edit()
                .putString("ID", authorization.id)
                .putString("TOKEN", authorization.token)
                .apply()
    }

    private fun removeAuthorization(){
        mPreferences
                .edit()
                .remove("ID")
                .remove("TOKEN")
                .apply()
    }
}