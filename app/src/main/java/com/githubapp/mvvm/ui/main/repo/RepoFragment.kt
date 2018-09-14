package com.githubapp.mvvm.ui.main.repo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.utils.startBrowser
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_repo.*
import java.text.DateFormat
import javax.inject.Inject

class RepoFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: RepoViewModelFactory

    private lateinit var viewModel: RepoViewModel

    private lateinit var username: String
    private lateinit var repoName: String

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

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
                LoadingState.IN_PROGRESS -> showLoading()
                LoadingState.DONE -> hideLoading()
                LoadingState.ACCESS_ERROR -> showError(getString(R.string.error_access))
                LoadingState.NETWORK_ERROR -> showError(getString(R.string.error_network))
                LoadingState.UNKNOWN_ERROR -> showError(getString(R.string.error_unknown))
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

    private fun showLoading(){
        progress.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    private fun hideLoading(){
        progress.visibility = View.GONE
        content.visibility = View.VISIBLE
    }

    private fun showError(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        hideLoading()
    }
}
