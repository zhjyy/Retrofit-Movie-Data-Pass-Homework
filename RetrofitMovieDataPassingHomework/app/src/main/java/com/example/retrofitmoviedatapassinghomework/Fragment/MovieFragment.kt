package com.example.retrofitmoviehomework.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitmoviedatapassinghomework.R
import com.example.retrofitmoviehomework.Adapter.MovieAdapter
import com.example.retrofitmoviehomework.Model.Result

import com.example.retrofitmoviehomework.Viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(),MovieAdapter.ClickListener {

    private lateinit var movieAdapter : MovieAdapter
    private lateinit var viewManager  : RecyclerView.LayoutManager
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = GridLayoutManager(activity,2)
        movieAdapter = MovieAdapter()//no need to add constructor cause of we defined array list
        rcyMovie.apply {
            adapter= movieAdapter
            layoutManager = viewManager
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)//create VM object
        //movieViewModel.loadResult()
        movieViewModel.topResults.observe(
            viewLifecycleOwner, Observer {
                rcyMovie.visibility = View.VISIBLE
                tvError.visibility=View.GONE
                movieAdapter.updateList(it)
                movieAdapter.setClickListener(this)
            }
        )

        movieViewModel.getError().observe(
            viewLifecycleOwner, Observer {
                if (it) {
                    tvError.visibility = View.VISIBLE
                    rcyMovie.visibility = View.GONE
                } else {
                    tvError.visibility = View.GONE
                    rcyMovie.visibility = View.VISIBLE
                }
            }
        )

    }


    override fun onResume() {//to get result
        super.onResume()
        movieViewModel.loadResult()//data loading and get data

    }

    override fun onClick(id: Int) {
        var action = MovieFragmentDirections.actionMovieFragmentToDetailFragment(id)
        findNavController().navigate(action)
        Log.d("detailID>>>>>>>",id.toString())
    }
}
