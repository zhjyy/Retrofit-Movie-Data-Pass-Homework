package com.example.retrofitmoviehomework.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitmoviehomework.Api.MovieApi
import com.example.retrofitmoviehomework.Model.Movie
import com.example.retrofitmoviehomework.Model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel: ViewModel() {
    var topResults: MutableLiveData<List<Result>> = MutableLiveData()
    var detailResult: MutableLiveData<Result> = MutableLiveData()
    var resultLoadError : MutableLiveData<Boolean> = MutableLiveData()
    var loading : MutableLiveData<Boolean> = MutableLiveData()

    fun getResults(apiKey:String): LiveData<List<Result>> = topResults
    fun getDetails():LiveData<Result> = detailResult
    fun getError() : LiveData<Boolean> = resultLoadError
    fun getLoading() : LiveData<Boolean> = loading

    private val movieApi: MovieApi = MovieApi()//obj//movie api => getNews()
    private val apiKey : String = "f66f74d7ca62dc1d7ac1e75ba3df1530"

    fun loadResult() {
        loading.value = true
        val apiCall = movieApi.getNews(apiKey)
        apiCall.enqueue(object : Callback<Movie> {

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                resultLoadError.value = true
                loading.value = false
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                response.isSuccessful.let {
                    loading.value = false
                    val resultList:List<Result> = response.body()?.results?: emptyList()
                    topResults.value = resultList

                }
            }
        })
    }

    fun loadDetail(id: Int){
        loading.value = true
        val apiCall = movieApi.getDetails(id,apiKey)
        apiCall.enqueue(object : Callback<Result> {

            override fun onFailure(call: Call<Result>, t: Throwable) {
                resultLoadError.value = true
                loading.value = false
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                response.isSuccessful.let {
                    loading.value = false
                    detailResult.value = response.body()
                }
            }
        })
    }
}