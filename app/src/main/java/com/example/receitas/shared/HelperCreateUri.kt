package com.example.receitas.shared

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import com.example.receitas.shared.constant.Const

import java.io.File
import java.io.FileOutputStream
import java.util.Date

object  HelperCreateUri {

     private const val  authority ="com.example.receitas.provider"
     private val nameImageChild = "imgReceita.png"

      fun adicionarFotoPorCamera(resultActivity: ActivityResult, context: Context) : Uri? {

        if(resultActivity.resultCode == Activity.RESULT_OK){
            val data =Date()
            val bitmap = resultActivity.data?.extras?.get("data") as Bitmap
            val caminho = File(context.cacheDir, "images")
            val uriFile = createFileUri(caminho,"${data.time}$nameImageChild",context,bitmap)
            return  uriFile
           /*
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
            output.flush()
            output.close()
            if (file.exists()){
                 Const.exibilog("Uri -- ${Uri.fromFile(file)}")
                //val uriImage = FileProvider.getUriForFile(context, "com.example.receitas.provider",file)
                return Uri.fromFile(file)
               // return uriImage
            }else{
                return Uri.EMPTY
            }*/
        }else{
            return null
        }
    }
      fun salvarFotoGaleriaUri(uri: Uri, context: Context) : Uri? {
        if(uri !=null){
            val data =Date()
            val bitmap =  MediaStore.Images.Media.getBitmap(context.contentResolver,uri)

            val caminho = File(context.filesDir, "images")

            val uriFile = createFileUri(caminho,"${data.time}${nameImageChild}",context,bitmap)

            return uriFile
        }else{
            return null
        }
    }

      private fun createFileUri(caminho :File, childName:String, context: Context, bitmap: Bitmap ):Uri?{
          caminho.mkdirs()

          val file = File(caminho,childName)
          val output = FileOutputStream(file)
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
          output.flush()
          output.close()

          val uriImage = FileProvider.getUriForFile(context, "com.example.receitas.fileprovider",file,"ReceitaImagem")

          println(uriImage)
          return uriImage
    }
}