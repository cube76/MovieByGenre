package com.example.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListReviewBinding
import com.example.core.domain.model.Review
import com.example.core.domain.model.ReviewResult
import com.example.core.utils.ConvertHtml
import java.time.format.DateTimeFormatterBuilder
import java.util.*


class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ListViewHolder>() {

    private var listData = ArrayList<ReviewResult>()
    var onItemClick: ((ReviewResult) -> Unit)? = null

    fun setData(newListData: Review?) {
        if (newListData == null) return
        listData.addAll(newListData.results)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_review, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListReviewBinding.bind(itemView)
        fun bind(data: ReviewResult) {
            with(binding) {
                if (data.author_details.avatar_path.isNullOrEmpty()){
                    Glide.with(itemView.context)
                        .load(R.drawable.noimage)
                        .into(ivProfilePicture)
                }else {
                    var image = ""
                    if(data.author_details.avatar_path.contains("https")){
                        image = data.author_details.avatar_path.drop(1)
                    }else {
                        image = "https://image.tmdb.org/t/p/original" + data.author_details.avatar_path
                    }
                    Glide.with(itemView.context)
                        .load(image)
                        .into(ivProfilePicture)
                }
                tvName.text = data.author
                tvContent.text = ConvertHtml(data.content).fromHtml(data.content)
                score.rating = data.author_details.rating.toFloat()
                tvDate.text = data.updated_at.split("T").toTypedArray()[0]
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

}
