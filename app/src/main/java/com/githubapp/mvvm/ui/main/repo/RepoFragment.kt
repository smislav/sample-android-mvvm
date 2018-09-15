package com.githubapp.mvvm.ui.main.repo

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
import kotlinx.android.synthetic.main.fragment_repo.*
import java.text.DateFormat
import javax.inject.Inject

class RepoFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: RepoViewModelFactory

    private lateinit var viewModel: RepoViewModel
    private lateinit var username: String
    private lateinit var repoName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = RepoFragmentArgs.fromBundle(arguments).username
        repoName = RepoFragmentArgs.fromBundle(arguments).repoName

        viewModel = ViewModelProviders.of(this, viewModelFactory)[RepoViewModel::class.java]
        viewModel.state.observe(this, Observer { state ->
            when(state!!){
                LoadingState.LOADING -> loading()
                LoadingState.LOADED -> loaded()
                LoadingState.ACCESS_ERROR -> accessError()
                LoadingState.NETWORK_ERROR -> networkError()
                LoadingState.UNKNOWN_ERROR -> unknownError()
            }
        })
        viewModel.repo.observe(this, Observer { repo ->
            Glide   .with(this)
                    .load(repo.user.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageUser)

            textUsername.text = repo.user.username

            buttonView.setOnClickListener{
                startBrowser(context!!, repo.url)
            }

            infoName.text = repo.name
            infoDesc.text = repo.description
            infoWatchers.text = repo.watchers.toString()
            infoForks.text = repo.forks.toString()
            infoIssues.text = repo.issues.toString()
            infoDate.text = DateFormat.getDateInstance()
                    .format(repo.date)
        })
        viewModel.getRepo(username, repoName)
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
