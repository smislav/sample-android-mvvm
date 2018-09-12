package com.githubapp.mvvm.ui.main.repos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.githubapp.data.models.Repo
import com.githubapp.mvvm.glide.GlideApp
import kotlinx.android.synthetic.main.item_repo.view.*

class ReposViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bind(repo: Repo?){
        if(repo != null){
            itemView.textUser.text = repo.user.username
            itemView.textName.text = repo.name
            itemView.textWatchers.text = repo.watchers.toString()
            itemView.textForks.text = repo.forks.toString()
            itemView.textIssues.text = repo.issues.toString()

            itemView.setOnClickListener(object: View.OnClickListener{
                override fun onClick(p0: View?) {
                    //clickListener.onRepoClick(repo)
                }
            })
            itemView.imageThumbnail.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    //clickListener.onOwnerClick(repo.user)
                }
            })

            GlideApp.with(itemView.context)
                    .load(repo.user.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemView.imageThumbnail)
        }
    }
}