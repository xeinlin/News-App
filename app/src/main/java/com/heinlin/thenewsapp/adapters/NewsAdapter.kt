package com.heinlin.thenewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.heinlin.thenewsapp.R
import com.heinlin.thenewsapp.models.Article
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Suppress("UselessCallOnNotNull", "MemberVisibilityCanBePrivate", "ComplexRedundantLet",
    "SpellCheckingInspection"
)
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var articleImage: ImageView
    lateinit var articleSource: TextView
    lateinit var articleTitle: TextView
    lateinit var articleDateTime: TextView

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        articleImage = holder.itemView.findViewById(R.id.articleImage)
        articleSource = holder.itemView.findViewById(R.id.articleSource)
        articleTitle = holder.itemView.findViewById(R.id.articleTitle)
        articleDateTime = holder.itemView.findViewById(R.id.dateTime)

        holder.itemView.apply {
            if (article.urlToImage.isNullOrBlank() || article.title.isNullOrBlank() || article.source.name.isNullOrBlank()) {
                // Skip binding the view if any required fields are missing
                holder.itemView.visibility = View.GONE
                holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
            } else {
                holder.itemView.visibility = View.VISIBLE
                holder.itemView.layoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                Glide.with(this).load(article.urlToImage).into(articleImage)
                articleSource.text = article.source.name
                articleTitle.text = article.title
                articleDateTime.text = article.publishedAt.let { getRelativeTime(it) }

                setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    fun getRelativeTime(time: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date? = dateFormat.parse(time)
        date?.let {
            val now = System.currentTimeMillis()
            val diff = now - date.time
            val seconds = (diff / 1000).toInt()
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            return when {
                seconds < 60 -> "just now"
                seconds < 120 -> "1 min ago"
                minutes < 60 -> "$minutes min${if (minutes > 1) "s" else ""} ago"
                hours < 24 -> "$hours hr${if (hours > 1) "s" else ""} ago"
                hours < 48 -> "yesterday"
                days < 30 -> "$days day${if (days > 1) "s" else ""} ago"
                days < 365 -> "${days / 30} month${if (days / 30 > 1) "s" else ""} ago"
                else -> "${days / 365} year${if (days / 365 > 1) "s" else ""} ago"
            }
        }
        return ""
    }
}
