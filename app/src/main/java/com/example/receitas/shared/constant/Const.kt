package com.example.receitas.shared.constant

import android.util.Log

object  Const {
      val BASE_URL_LIST_BY_AREA ="https://www.themealdb.com/api/json/v1/1/"
      val TEXT_INTENT_EXTRAS_RECEITA ="receita"

    fun exibilog(message: String ){
        Log.i("INFO_"," ${message}")
    }
}