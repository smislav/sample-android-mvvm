package com.githubapp.mvvm.ui.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.ui.base.BaseFragment
import com.githubapp.mvvm.utils.startBrowser
import kotlinx.android.synthetic.main.fragment_user.*
import javax.inject.Inject

class UserFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: UserViewModelFactory

    private lateinit var viewModel: UserViewModel
    private lateinit var username: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = UserFragmentArgs.fromBundle(arguments).username

        viewModel = ViewModelProviders.of(this, viewModelFactory)[UserViewModel::class.java]
        viewModel.state.observe(this, Observer { state ->
            when(state!!){
                LoadingState.LOADING -> loading()
                LoadingState.LOADED -> loaded()
                LoadingState.ACCESS_ERROR -> accessError()
                LoadingState.NETWORK_ERROR -> networkError()
                LoadingState.UNKNOWN_ERROR -> unknownError()
            }
        })
        viewModel.user.observe(this, Observer { user ->
            Glide   .with(this)
                    .load(user.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageUser)

            textUsername.text = user.username

            buttonView.setOnClickListener{
                startBrowser(context!!, user.url)
            }

            infoName.text = user.name
            infoBio.text = user.bio
            infoEmail.text = user.email
            infoLocation.text = user.location
            infoCompany.text = user.company
            infoUsername.text = user.username
            infoType.text = user.type
        })
        viewModel.getUser(username)
    }

    override fun showLoadingView() {
        progress.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    override fun hideLoadingView() {
        progress.visibility = View.GONE
        content.visibility = View.VISIBLE
    }
}
