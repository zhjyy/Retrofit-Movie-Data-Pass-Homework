package com.example.retrofitmoviehomework.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.retrofitmoviedatapassinghomework.R
import com.example.retrofitmoviehomework.Viewmodel.MovieViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetailFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    fun observeViewModel(){
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getDetails().observe(
            viewLifecycleOwner, Observer {
                tvDetailName.text = it.title
                tvOverView.text =  it.overview
                tvDetailDate.text  = it.release_date
                tvDetailRating.text = "Rating: " + it.vote_average.toString()

                Log.d("OverView:>>>>>",it.overview)
                Picasso.get().load("https://image.tmdb.org/t/p/w200${it.poster_path}")
                    .placeholder(R.drawable.ic_launcher_background).into(imageDetail)
            }
        )

        btnBack.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_detailFragment_to_movieFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        var data = arguments.let { DetailFragmentArgs.fromBundle(it!!) }
        var resultData  = data.detail
        tvDetailId.text="MovieId: " + resultData.toString()
        movieViewModel.loadDetail(resultData)
    }
}