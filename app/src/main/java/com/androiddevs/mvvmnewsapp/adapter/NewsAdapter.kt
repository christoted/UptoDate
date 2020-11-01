package com.androiddevs.mvvmnewsapp.adapter

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.model.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {


//        fun bind(article: Article) {
//            Glide.with(itemView)
//                .load(article.urlToImage)
//                .into(itemView.ivArticleImage)
//
//            itemView.tvSource.text = article.source.name
//            itemView.tvTitle.text = article.title
//            itemView.tvDescription.text = article.description
//            itemView.tvPublishedAt.text = article.publishedAt
//        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        //Check apakah item nya sama, berhubung kita pake online get API maka cek image url nya sabi
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    //Sekarang buat function aysnc list differ -> guannya ambil 2 list tersebut(oldItem) (newItem) dan bakal compare
    val differ =  AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(
            R.layout.item_article_preview,parent, false)

        return ArticleViewHolder(layout)
    }

    override fun getItemCount(): Int {

        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {

            Glide.with(this)
                .load(article.urlToImage)
                .into(ivArticleImage)

            tvSource.text = article.source?.name ?: null
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt

            //Kalo ga null jalanin
            setOnClickListener {
                onItemClickListener?.let {
                    (it(article))
                }
            }

        }
    }


    //Buat listenernya
    private var onItemClickListener: ((Article) -> Unit) ? = null

    fun setOnItemClickListerner(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}