package com.example.conduit_kotlin_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.conduit_kotlin_app.R
import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.databinding.ItemArticleBinding
import com.example.conduit_kotlin_app.utils.convertDateAndTime

class ArticleAdapter(
    private val listner: onItemClickListner
) : RecyclerView.Adapter<ArticleAdapter.MyArticleViewHolder>() {
    inner class MyArticleViewHolder(private val binding: ItemArticleBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.apply {
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvAuthorName.text = article.author?.username
                tvCreatedat.text = convertDateAndTime(article.createdAt)
                Glide.with(itemView)
                    .load(article.author?.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .error(R.drawable.ic_error)
                    .into(imgAuthor)
            }
        }
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = differ.currentList[position]
                    if (item != null){
                        listner.onItemClick(item)
                    }
                }
            }
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Article, newItem: Article) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyArticleViewHolder {
        val binding =
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return   MyArticleViewHolder(binding)

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        if(article!=null){
            holder.bind(article)
        }




    }

    interface onItemClickListner{
        fun onItemClick(article: Article)
    }


}