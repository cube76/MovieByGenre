package com.example.moviebygenre.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devstune.searchablemultiselectspinner.SearchableItem
import com.devstune.searchablemultiselectspinner.SearchableMultiSelectSpinner
import com.devstune.searchablemultiselectspinner.SelectionCompleteListener
import com.example.core.data.Resource
import com.example.core.domain.model.*
import com.example.core.ui.MovieAdapter
import com.example.moviebygenre.R
import com.example.moviebygenre.databinding.ActivityMovieBinding
import com.example.moviebygenre.detail.DetailActivity
import com.example.moviebygenre.detail.DetailActivity.Companion.EXTRA_DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var binding: ActivityMovieBinding
    private var listGenre = ArrayList<Genre>()
    private var items = mutableListOf<SearchableItem>()
    private val movieAdapter = MovieAdapter()
    private lateinit var detailMovie: MovieList
    var listMovie = arrayListOf<Movie>()
    private var selectedGenre = ""
    private var selectedGenreName = ""
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        supportActionBar?.hide()
        movieViewModel.genre.observe(this) { genre ->
            if (genre != null) {
                when (genre) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setData(genre.data as GenreList?)

                        for (i in listGenre) {
                            items?.add(SearchableItem(i.name, i.id.toString()))
                        }

                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            genre.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
        getMovie(page)

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(this@MovieActivity)
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        binding.spinnerGenre.setOnClickListener {
            SearchableMultiSelectSpinner.show(this, "Select Genre", "Done",
                items, object :
                    SelectionCompleteListener {
                    override fun onCompleteSelection(selectedItems: ArrayList<SearchableItem>) {
                        selectedGenre = ""
                        selectedItems.forEachIndexed { index, element ->
                            selectedGenre += if (index == 0) {
                                element.code
                            } else {
                                "," + element.code
                            }
                            selectedGenreName += if (index == 0) {
                                element.text
                            } else {
                                "," + element.text
                            }
                        }
                        getMovie(page)
                    }

                })
        }
        binding.rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listMovie.size - 1) {
                    if(detailMovie.page != detailMovie.total_pages) {
                        page += 1
                        getMovie(page)
                    }
                }
            }
        })
    }

    fun getMovie(page: Int) {
        if (selectedGenre != "") {
            binding.tvSelected.text = selectedGenreName
        }
        movieViewModel.getMovies(selectedGenre, page).observe(this) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        if (page == 1) {
                            binding.progressBar.visibility = View.VISIBLE
                        }else{
                            binding.pbNext.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.pbNext.visibility = View.GONE
                        detailMovie = movie.data as MovieList
                        listMovie.addAll(detailMovie.results)
                        movieAdapter.setData(detailMovie)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            movie.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
    }

    private fun setData(newListData: GenreList?) {
        if (newListData == null) return
        listGenre.clear()
        listGenre.addAll(newListData.genres)
    }
}