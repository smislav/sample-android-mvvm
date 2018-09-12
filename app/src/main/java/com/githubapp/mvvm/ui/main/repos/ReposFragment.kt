package com.githubapp.mvvm.ui.main.repos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.enums.Sort
import com.githubapp.mvvm.ui.base.ReposViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_repos.*
import javax.inject.Inject


class ReposFragment : Fragment() {
    @Inject
    lateinit var mViewModelFactory: ReposViewModelFactory

    lateinit var mViewModel: ReposViewModel

    lateinit var mAdapter: ReposAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = ReposAdapter()

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)

        recyclerRepos.setAdapter(mAdapter)
        recyclerRepos.setLayoutManager(layoutManager)
        recyclerRepos.addItemDecoration(itemDecoration)

        refreshRepos.setOnRefreshListener {
            mViewModel.reloadData()
            refreshRepos.isRefreshing = false
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mViewModel.setQuery(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        groupSort.setOnCheckedChangeListener { _, id ->
            when(id){
                R.id.toggleStars -> mViewModel.setSort(Sort.STARS)
                R.id.toggleForks -> mViewModel.setSort(Sort.FORKS)
                R.id.toggleUpdated -> mViewModel.setSort(Sort.UPDATED)
            }
        }

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ReposViewModel::class.java)
        mViewModel.repos.observe(this, Observer {
            mAdapter.submitList(it)
        })
        mViewModel.getLiveDataSource().observe(this, Observer {
            it.loadingState.observe(this, Observer {
                when(it!!){
                    LoadingState.IN_PROGRESS -> showLoading()
                    LoadingState.DONE -> hideLoading()
                    LoadingState.ACCESS_ERROR -> showError("Access error")
                    LoadingState.NETWORK_ERROR -> showError("Network error")
                    LoadingState.UNKNOWN_ERROR -> showError("Unknown error")
                }
            })
        })
    }

    private fun showLoading(){
        refreshRepos.isRefreshing = true
    }

    private fun hideLoading(){
        refreshRepos.isRefreshing = false
    }

    private fun showError(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        hideLoading()
    }
}
