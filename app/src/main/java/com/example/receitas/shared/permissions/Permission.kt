package com.example.receitas.shared.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat

class Permission {
    fun requestPermission(context: Context ,permission : String){
        val isPermited  = ContextCompat.checkSelfPermission(context,permission)
             if (isPermited == PackageManager.PERMISSION_DENIED){

             }else{

             }
    }
}