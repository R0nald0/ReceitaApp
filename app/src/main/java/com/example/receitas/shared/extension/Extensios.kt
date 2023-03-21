package com.example.receitas.shared.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.showToast(messenge:String){
    Toast.makeText(applicationContext, messenge, Toast.LENGTH_LONG).show()
}
fun View.esconderTeclado(){
    val inputMethodManager  =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    if(inputMethodManager.isAcceptingText){
        inputMethodManager
            .hideSoftInputFromWindow(windowToken,0)
    }

}