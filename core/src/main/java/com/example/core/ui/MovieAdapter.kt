package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.domain.model.Movie
import com.example.core.databinding.ItemListMovieBinding
import com.bumptech.glide.Glide
import com.example.core.domain.model.MovieList

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: MovieList?) {
        if (newListData == null) return
        listData.addAll(newListData.results)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListMovieBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                if (data.poster_path.isNullOrEmpty()){
                    Glide.with(itemView.context)
                        .load(R.drawable.noimage)
                        .into(ivItemImage)
                }else {
                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/original"+data.poster_path)
                        .into(ivItemImage)
                }
                tvItemTitle.text = data.original_title
                tvItemSubtitle.text = data.overview
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}