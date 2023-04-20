package com.example.receitas.shared.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.example.receitas.R

class AlertDialogCustom(private val  acttivity :Activity) {
    var dialog : AlertDialog? = null
    val layout = LayoutInflater.from(acttivity).inflate(R.layout.lottie_charging_layout,null,false)
    fun exibirDiaolog(){

         val alertDialog = AlertDialog.Builder(acttivity)
             .setView(layout)
             .setCancelable(false)

          if (dialog != null){
              fecharDialog()
          }else{
              dialog = alertDialog.create()
              dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
              dialog?.show()
          }

    }
    fun fecharDialog(){
        dialog?.dismiss()
    }
}