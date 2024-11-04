package com.heinlin.thenewsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.heinlin.thenewsapp.databinding.ActivityNewsBinding
import com.heinlin.thenewsapp.db.ArticleDatabase
import com.heinlin.thenewsapp.repository.NewsRepository
import com.heinlin.thenewsapp.ui.viewmodel.NewsViewModel
import com.heinlin.thenewsapp.ui.viewmodel.NewsViewModelProviderFactory


class NewsActivity : AppCompatActivity() {

    internal lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        installSplashScreen()
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        newsViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.searchIcon.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val toolbarTitle = when (destination.id) {
                R.id.headlinesFragment -> "Headlines"
                R.id.favouritesFragment -> "Favorites"
                R.id.settingsFragment -> "Settings"
                else -> "Search News"
            }
            binding.toolbarTitle.text = toolbarTitle
        }
    }
}