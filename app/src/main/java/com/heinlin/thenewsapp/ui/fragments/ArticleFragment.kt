package com.heinlin.thenewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.heinlin.thenewsapp.NewsActivity
import com.heinlin.thenewsapp.R
import com.heinlin.thenewsapp.databinding.FragmentArticleBinding
import com.heinlin.thenewsapp.helper.PreferencesManager
import com.heinlin.thenewsapp.ui.viewmodel.NewsViewModel

@Suppress("MemberVisibilityCanBePrivate")
class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var binding: FragmentArticleBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleNecessary(view)
    }

    override fun onResume() {
        super.onResume()
        PreferencesManager(requireContext()).applyLanguage(requireContext())
    }

    private fun articleNecessary(view: View) {
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            newsViewModel.addToFavorites(article)
            Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_SHORT).show()
        }

        binding.articleBack.setOnClickListener {
            // Navigate back to the previous fragment
            findNavController().navigateUp()
            (activity as NewsActivity).recreate()
        }
    }


}

