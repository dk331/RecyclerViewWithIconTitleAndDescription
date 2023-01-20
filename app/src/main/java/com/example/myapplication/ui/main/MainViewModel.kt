package com.example.myapplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.MainRepository
import com.example.myapplication.model.Author
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {
    private val listMutableLiveData: MutableLiveData<ArrayList<Author>> =
        MutableLiveData<ArrayList<Author>>()
    private var authorArrayList: ArrayList<Author> = ArrayList()
    private val errorMessage: MutableLiveData<String> = MutableLiveData<String>()

    fun getAuthorMutableLiveData(): MutableLiveData<ArrayList<Author>> {
        return listMutableLiveData
    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMessage
    }

    init {
        listMutableLiveData.value = authorArrayList
    }

    fun populateList() {
        val response = repository.getAuthors(2, 20)
        response.enqueue(object : Callback<List<Author>> {
            override fun onResponse(call: Call<List<Author>>, response: Response<List<Author>>) {
                authorArrayList = response.body() as ArrayList<Author>
                listMutableLiveData.postValue(authorArrayList)
            }

            override fun onFailure(call: Call<List<Author>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })

    }
}
