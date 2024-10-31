package com.heinlin.thenewsapp.repository

import com.heinlin.thenewsapp.api.RetrofitInstance
import com.heinlin.thenewsapp.db.ArticleDatabase
import com.heinlin.thenewsapp.models.Article

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getheadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getFavoriteNews() = db.getArticleDao().getALLArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

}