package com.heinlin.thenewsapp.repository

import com.heinlin.thenewsapp.api.Retrofit
import com.heinlin.thenewsapp.db.ArticleDatabase
import com.heinlin.thenewsapp.models.Article

@Suppress("SpellCheckingInspection", "MemberVisibilityCanBePrivate")
class NewsRepository(val db: ArticleDatabase) {

    suspend fun getheadlines(countryCode: String, pageNumber: Int) =
        Retrofit.api.getHeadlines(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        Retrofit.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getFavoriteNews() = db.getArticleDao().getALLArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

}