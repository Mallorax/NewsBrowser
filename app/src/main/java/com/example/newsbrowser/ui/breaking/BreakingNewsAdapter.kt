package com.example.newsbrowser.ui.breaking

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsbrowser.databinding.ActivityMainBinding
import com.example.newsbrowser.databinding.ArticleItemBinding
import com.example.newsbrowser.model.ArticleAppModel

class BreakingNewsAdapter(): PagingDataAdapter<ArticleAppModel, BreakingNewsAdapter.ArticleViewHolder>(DiffCallback) {



    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        TODO("Not yet implemented")
    }

    class ArticleViewHolder(private val binding: ArticleItemBinding): RecyclerView.ViewHolder(binding.root) {

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