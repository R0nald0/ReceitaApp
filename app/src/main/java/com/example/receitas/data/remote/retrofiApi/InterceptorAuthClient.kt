package com.example.receitas.data.remote.retrofiApi

import okhttp3.Interceptor
import okhttp3.Response

class InterceptorAuthClient :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
         val resposta = chain.request().newBuilder()
         val chainResposta = resposta.addHeader(
             "Authorization","1"
         ).build()
        return chain.proceed(chainResposta)
    }
}

/*
*
         val retornoChain = chain.request().newBuilder()
           val resposta = retornoChain.addHeader(
                "Authorization", Configurcao.ACESSE_KEY
           ).build()
        return  chain.proceed(resposta)
*
* */