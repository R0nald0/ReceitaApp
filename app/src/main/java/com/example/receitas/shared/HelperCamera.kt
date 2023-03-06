package com.example.receitas.shared

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract.Data
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import androidx.viewbinding.BuildConfig

import com.example.receitas.shared.constant.Const
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import java.util.Date

object  HelperCamera {
     val data =Date()

    fun adicionarFotoPorCamera(resultActivity: ActivityResult, context: Context) : Uri? {

        if(resultActivity.resultCode == Activity.RESULT_OK){
            val bitmap = resultActivity.data?.extras?.get("data") as Bitmap
            val caminho = File(context.cacheDir, "images")
            caminho.mkdirs()
            val file = File(caminho,"${data.time}receita.png")
            val output = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
            output.flush()
            output.close()

            Const.exibilog("hd ${Build.HARDWARE}")
            Const.exibilog("id ${Build.ID}")
            Const.exibilog("build ${BuildConfig.LIBRARY_PACKAGE_NAME}")
           val uriImage = FileProvider.getUriForFile(context, "com.example.receitas.provider", file)

            return uriImage

        }else{
            return null
        }

    }
}