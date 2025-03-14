package com.project.fetch.module

import com.project.fetch.api.ApiService
import com.project.fetch.api.DataRepository
import com.project.fetch.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder().baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single { get<Retrofit>().create(ApiService::class.java) }

    single { DataRepository(get()) }

    viewModel { ListViewModel(get()) }
}
