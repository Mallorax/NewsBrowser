package com.example.newsbrowser.ui.breaking

import android.content.Context
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
class BreakingNewsFragment : Fragment() {

    private val breakingNewsViewModel: BreakingNewsViewModel by viewModels()
    private var _binding: BreakingNewsFragmentBinding? = null
    private val binding get() = _binding!!
    private val newsRequest = NewsRequest()
    val adapter = createAdapter()
    private val sharedPref by lazy {
        requireActivity().getSharedPreferences(
            getString(R.string.preference_language_key),
            Context.MODE_PRIVATE
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BreakingNewsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        newsRequest.language = NewsRequest.Language.valueOf(
            sharedPref.getString(
                getString(R.string.chosen_language_key),
                NewsRequest.Language.ENGLISH.name
            )!!
        )

        observeBreakingNews()
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
                observeArticles()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    newsRequest.query = newText
                    observeArticles()
                } else {
                    observeBreakingNews()
                }
                return true
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.relevancy -> newsRequest.sortOrder = NewsRequest.SortOrder.RELEVANCY
            R.id.popularity -> newsRequest.sortOrder = NewsRequest.SortOrder.POPULARITY
            R.id.publication_date -> newsRequest.sortOrder = NewsRequest.SortOrder.PUBLICATION_DATE
            R.id.article_search -> newsRequest.sortOrder = newsRequest.sortOrder
            else -> newsRequest.sortOrder = NewsRequest.SortOrder.PUBLICATION_DATE
        }
        return true
    }

    private fun createAdapter(): BreakingNewsAdapter {
        return BreakingNewsAdapter(BreakingNewsAdapter.OnArticleClickListener { article, _ ->
            if (article?.url != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(Intent.createChooser(intent, "Browse with:"))
            }
        })
    }

    private fun observeArticles(){
        breakingNewsViewModel.getArticles(newsRequest)
            .doOnError { t ->
                t.printStackTrace()
                Snackbar.make(
                    requireView(),
                    "Error occurred: " + t.message,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            .to(autoDisposable(AndroidLifecycleScopeProvider.from(this@BreakingNewsFragment)))
            .subscribe { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
    }

    private fun observeBreakingNews() {
        breakingNewsViewModel.getBreakingNews()
            .doOnError { t ->
                t.printStackTrace()
                Snackbar.make(requireView(), "Error occurred: " + t.message, Snackbar.LENGTH_LONG)
                    .show()
            }
            .to(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
    }
}