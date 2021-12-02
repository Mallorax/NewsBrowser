package com.example.newsbrowser.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.newsbrowser.databinding.BreakingNewsFragmentBinding
import com.example.newsbrowser.ui.breaking.BreakingNewsAdapter
import com.google.android.material.snackbar.Snackbar

class ArticlesBrowserFragment: Fragment() {

    private var _binding: BreakingNewsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BreakingNewsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = createAdapter()
        val recycler = binding.articleRecyclerView
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler.adapter = adapter

        return binding.root
    }

    private fun createAdapter(): BreakingNewsAdapter {
        return BreakingNewsAdapter(BreakingNewsAdapter.OnArticleClickListener { article, view ->
            Snackbar.make(view, article?.title + " has been clicked", Snackbar.LENGTH_LONG).show()
        })
    }
}