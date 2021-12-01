package com.example.newsbrowser.ui.breaking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsbrowser.databinding.ArticleItemBinding
import com.example.newsbrowser.databinding.ArticleItemBindingImpl
import com.example.newsbrowser.model.ArticleAppModel

class BreakingNewsAdapter(private val onArticleClickListener: OnArticleClickListener)
    : PagingDataAdapter<ArticleAppModel, BreakingNewsAdapter.ArticleViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ArticleItemBindingImpl.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.binding.articleImage.setOnClickListener{onArticleClickListener.onArticleClick(article, holder.itemView)}
        if (article != null) {
            holder.bind(article)
        }
    }


    class ArticleViewHolder(val binding: ArticleItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleAppModel){
             binding.article = article
             binding.executePendingBindings()
         }
    }

    class OnArticleClickListener(val clickListener: (article: ArticleAppModel?, view: View) -> Unit){
        fun onArticleClick(article: ArticleAppModel?, view: View) = clickListener(article, view)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<ArticleAppModel>(){
        override fun areItemsTheSame(oldItem: ArticleAppModel, newItem: ArticleAppModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ArticleAppModel,
            newItem: ArticleAppModel
        ): Boolean {
            return oldItem.content == newItem.content
        }
    }
}