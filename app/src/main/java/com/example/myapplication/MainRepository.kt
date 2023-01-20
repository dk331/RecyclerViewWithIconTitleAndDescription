package com.example.myapplication

class MainRepository(private val retrofitService: RetrofitService) {
    fun getAuthors(page: Int?, limit: Int?) = retrofitService.getAuthors(page, limit)
}