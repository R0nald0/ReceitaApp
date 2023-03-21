package com.example.receitas.data.remote.retrofiApi

import com.example.receitas.shared.constant.Const
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGetApi {

    fun getCLient():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(InterceptorAuthClient())
            .build()
    }

    fun <T> retrofitCreate (classe:Class<T>):T{
      return  Retrofit.Builder()
            .baseUrl(Const.BASE_URL_LIST_BY_AREA)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getCLient())
            .build()
            .create(classe)
    }
}

/*
*  fun getCLient():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(InterceptorAuthClient())
            .build()
    }

    fun <T> getApi(classe:Class<T>):T {
     return   Retrofit.Builder()
            .baseUrl(Configurcao.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getCLient())
            .build()
            .create(classe)
    }
*
*
*
*
*
*
*
*
* */