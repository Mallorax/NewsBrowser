package com.example.newsbrowser.ui.breaking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import autodispose2.AutoDispose.autoDisposable
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.example.newsbrowser.databinding.BreakingNewsFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment: Fragment() {

    private val breakingNewsViewModel: BreakingNewsViewModel by viewModels()
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
        observeNews(adapter)
        val recycler = binding.articleRecyclerView
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler.adapter = adapter

        return binding.root
    }

    private fun createAdapter(): BreakingNewsAdapter{
        return BreakingNewsAdapter(BreakingNewsAdapter.OnArticleClickListener { article, view ->
            Snackbar.make(view, article?.title + " has been clicked", Snackbar.LENGTH_LONG).show()
        })
    }

    private fun observeNews(adapter: BreakingNewsAdapter){
        breakingNewsViewModel.getBreakingNews()
            .to(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe{pagingData -> adapter.submitData(lifecycle, pagingData)}
    }
}