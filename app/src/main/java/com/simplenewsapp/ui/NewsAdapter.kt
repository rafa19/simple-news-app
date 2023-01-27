package com.simplenewsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simplenewsapp.R
import com.simplenewsapp.data.model.Article
import com.simplenewsapp.databinding.ItemNewsBinding
import com.simplenewsapp.utils.CommonUtils

class NewsAdapter(private val mList: ArrayList<Article>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    var onItemClick: ((Article) -> Unit)? = null
    private lateinit var binding: ItemNewsBinding


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(mList[adapterPosition])
            }
        }

        fun setData(item: Article) {
            binding.apply {
                txtTitle.text = item.title
                txtContent.text = item.content
                txtDate.text = item.publishedAt
                Glide.with(this.root.context)
                    .load(item.urlToImage)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imgView)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(mList[position])
        holder.setIsRecyclable(false)
    }

    fun addList(newList: List<Article>) {
        val oldCount = mList.size
        mList.addAll(newList)
        notifyItemRangeInserted(oldCount, mList.size)
    }

    override fun getItemCount() = mList.size

}