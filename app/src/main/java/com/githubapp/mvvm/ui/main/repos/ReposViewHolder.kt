package com.githubapp.mvvm.ui.main.repos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.request.RequestOptions
import com.githubapp.data.models.Repo
import com.githubapp.mvvm.R
import com.githubapp.mvvm.glide.GlideApp

class ReposViewHolder: RecyclerView.ViewHolder {
    @BindView(R.id.text_user)
    lateinit var userName : TextView

    @BindView(R.id.image_thumbnail)
    lateinit var userImage : ImageView

    @BindView(R.id.text_name)
    lateinit var repoName : TextView

    @BindView(R.id.text_watchers)
    lateinit var watchers : TextView

    @BindView(R.id.text_forks)
    lateinit var forks : TextView

    @BindView(R.id.text_issues)
    lateinit var issues : TextView


    constructor(view: View) : super(view){
        ButterKnife.bind(this, view)
    }

    fun bind(repo: Repo?){
        if(repo != null){
            userName.text = repo.user.username
            repoName.text = repo.name
            watchers.text = repo.watchers.toString()
            forks.text = repo.forks.toString()
            issues.text = repo.issues.toString()

            itemView.setOnClickListener(object: View.OnClickListener{
                override fun onClick(p0: View?) {
                    //clickListener.onRepoClick(repo)
                }
            })
            userImage.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    //clickListener.onOwnerClick(repo.user)
                }
            })

            GlideApp.with(itemView.context)
                    .load(repo.user.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(userImage)
        }
    }
}