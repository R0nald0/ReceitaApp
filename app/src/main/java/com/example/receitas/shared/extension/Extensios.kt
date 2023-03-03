package com.example.receitas.shared.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.showToast(messenge:String){
    Toast.makeText(applicationContext, messenge, Toast.LENGTH_LONG).show()
}