package com.heinlin.thenewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.heinlin.thenewsapp.R
import com.heinlin.thenewsapp.databinding.ActivityNewsBinding
import com.heinlin.thenewsapp.databinding.FragmentArticleBinding
import com.heinlin.thenewsapp.models.Article
import com.heinlin.thenewsapp.ui.NewsActivity
import com.heinlin.thenewsapp.ui.NewsViewModel


class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var newsViewmodel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewmodel = (activity as NewsActivity).newsViewModel
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(it)
            }
        }

        binding.fab.setOnClickListener {
            newsViewmodel.addToFavotites(article)
            Snackbar.make(view, "Add tp favorites", Snackbar.LENGTH_SHORT).show()
        }
    }


}