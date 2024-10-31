package com.heinlin.thenewsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heinlin.thenewsapp.repository.NewsRepository

class NewsViewModelProviderFactory(val app: Application, val newsRespority: NewsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRespority) as T
    }
}