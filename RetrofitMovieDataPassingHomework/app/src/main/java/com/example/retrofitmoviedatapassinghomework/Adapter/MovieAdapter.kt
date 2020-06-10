package com.example.retrofitmoviehomework.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitmoviedatapassinghomework.R
import com.example.retrofitmoviehomework.Model.Result
import com.squareup.picasso.Picasso


import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter:RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    var mClickListener: ClickListener? = null

    fun setClickListener(clickListener: ClickListener) {
        this.mClickListener = clickListener
    }

    var resultList: List<Result> = listOf()

    inner class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),View.OnClickListener
    {
        private lateinit var result: Result

        init {
            itemView.setOnClickListener(this)//initialize onClick fun:s and/ when start create obj,it works
        }

        fun bindMovie(result:Result){
            this.result = result
            Picasso.get().load("https://image.tmdb.org/t/p/w200${result.poster_path}")
                .placeholder(R.drawable.ic_launcher_background).into(itemView.imageMovie)//place holder is to show temp image when loading
            itemView.tvMovieName.text = result.title
            itemView.tvDate.text  = result.release_date
        }

        override fun onClick(view: View?) {
            mClickListener?.onClick(result.id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resultList.size
        //Log.d("new>>>>>",resultList.size.toString())
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(resultList[position])
    }

    fun updateList(result : List<Result>)
    {
        this.resultList = result
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(id: Int )
    }

}