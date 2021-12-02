package com.example.newsbrowser.ui.breaking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import autodispose2.AutoDispose.autoDisposable
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.example.newsbrowser.R
import com.example.newsbrowser.databinding.BreakingNewsFragmentBinding
import com.example.newsbrowser.model.NewsRequest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment: Fragment() {

    private val breakingNewsViewModel: BreakingNewsViewModel by viewModels()
    private var _binding: BreakingNewsFragmentBinding? = null
    private val binding get() = _binding!!
    val adapter = createAdapter()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BreakingNewsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        observeNews(adapter)
        val recycler = binding.articleRecyclerView
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler.adapter = adapter

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.articles_menu, menu)
        val searchViewItem = menu.findItem(R.id.article_search)
        val searchView = searchViewItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()){
                    breakingNewsViewModel.getArticles(NewsRequest(query = newText))
                        .doOnError { t ->
                            t.printStackTrace()
                            Snackbar.make(requireView(), "Error occurred: " +t.message, Snackbar.LENGTH_LONG).show()
                        }.to(autoDisposable(AndroidLifecycleScopeProvider.from(this@BreakingNewsFragment)))
                        .subscribe{pagingData ->
                            adapter.submitData(lifecycle, pagingData)
                        }
                }else{
                    observeNews(adapter)
                }
                return true
            }
        })
    }

    private fun createAdapter(): BreakingNewsAdapter{
        return BreakingNewsAdapter(BreakingNewsAdapter.OnArticleClickListener { article, _ ->
            if (article?.url != null){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(Intent.createChooser(intent, "Browse with:"))
            }

        })
    }

    private fun observeNews(adapter: BreakingNewsAdapter){
        breakingNewsViewModel.getBreakingNews()
            .doOnError { t ->
                t.printStackTrace()
                Snackbar.make(requireView(), "Error occurred: " +t.message, Snackbar.LENGTH_LONG).show()
            }
            .to(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe{pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
    }
}