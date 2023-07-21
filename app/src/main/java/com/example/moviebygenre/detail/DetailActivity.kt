package com.example.moviebygenre.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.data.Resource
import com.example.core.domain.model.*
import com.example.core.ui.ReviewAdapter
import com.example.moviebygenre.R
import com.example.moviebygenre.databinding.ActivityDetailBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalTime


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val reviewAdapter = ReviewAdapter()
    private lateinit var detailData: DetailMovie
    var listReview = arrayListOf<ReviewResult>()
    private lateinit var detailReview: Review
    var page = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(EXTRA_DATA)
        }

        detailViewModel.getDetailMovie(movie!!.id, "videos").observe(this) { detail ->
            if (detail != null) {
                when (detail) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        detailData = detail.data as DetailMovie
                        with(binding) {
                            if (detailData.poster_path.isNullOrEmpty()) {
                                Glide.with(this@DetailActivity)
                                    .load(com.example.core.R.drawable.noimage)
                                    .into(content.ivPoster)
                            } else {
                                Glide.with(this@DetailActivity)
                                    .load("https://image.tmdb.org/t/p/original" + detailData.poster_path)
                                    .into(content.ivPoster)
                            }
                            content.tvTitle.text = detailData.original_title
                            content.tvOverview.text = detailData.overview
                            content.tvDuration.text = "Duration: " + LocalTime.MIN.plus(
                                Duration.ofMinutes(detailData.runtime.toLong())
                            ).toString()
                            content.tvYear.text =
                                detailData.release_date.split("-").toTypedArray()[0]
                            var genres = ""
                            detailData.genres.forEachIndexed { index, element ->
                                genres += if (index == 0) {
                                    element.name
                                } else {
                                    ", " + element.name
                                }
                            }
                            content.tvGenres.text = "Genre: $genres"
                            content.rbRating.rating = (detailData.vote_average / 2).toFloat()
                            content.rbRating.numStars = 5
                            content.tvRating.text =
                                detailData.vote_average.toBigDecimal()
                                    .setScale(1, RoundingMode.DOWN).toDouble()
                                    .toString()

                            for (i in detailData.videos.results) {
                                if (i.type == "Trailer") {
                                    youtubePlayerView.addYouTubePlayerListener(object :
                                        AbstractYouTubePlayerListener() {
                                        override fun onReady(youTubePlayer: YouTubePlayer) {
                                            val videoId = i.key
                                            youTubePlayer.loadVideo(videoId, 0f)

                                        }
                                    })
                                    break
                                }
                            }
                            setSupportActionBar(findViewById(R.id.toolbar))
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                            supportActionBar?.setDisplayShowHomeEnabled(true)
                            toolbarLayout.title = detailData.original_title

                        }

                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            detail.message ?: getString(R.string.something_wrong)
                    }
                }
            }

        }

        with(binding.content.rvReview) {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            setHasFixedSize(true)
            adapter = reviewAdapter
        }
        getReview(movie.id, page)

        binding.content.groupReview.setOnClickListener {
            Handler().post { binding.content.rvReview.scrollTo(0, binding.content.rvReview.top) }
        }

        binding.content.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listReview.size - 1) {
                    if (detailReview.page != detailReview.total_pages) {
                        page += 1
                        getReview(movie.id, page)
                    }
                }
            }
        })


    }

    private fun getReview(id: Int, page: Int) {
        detailViewModel.getReview(id, page).observe(this) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        if (page == 1) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.content.pbLoad.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.content.pbLoad.visibility = View.GONE
                        reviewAdapter.setData(movie.data as Review?)
                        detailReview = movie.data as Review
                        listReview.addAll(detailReview.results)
                        if (detailReview.results.isEmpty()) {
                            binding.content.tvNoReview.visibility = View.VISIBLE
                        }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}