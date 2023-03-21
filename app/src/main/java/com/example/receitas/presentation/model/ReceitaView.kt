package com.example.receitas.presentation.model




import android.net.Uri
import android.os.Parcelable
import android.widget.ImageView
import com.example.receitas.R
import com.squareup.picasso.Picasso
import kotlinx.parcelize.Parcelize



@Parcelize
class ReceitaView(
    var idRealm : String? ,
    var isUserList : Boolean,
    var titulo :String,
    var Imagem : Uri?,
    var ImageUrl : String,
    var instrucao : String,
    var tempo :String,
    var ingredientes: String
):Parcelable
{
    fun checkImg():String?{
        if (this.ImageUrl.isEmpty()) {
            if (this.Imagem.toString().isEmpty())
                return null
               // Picasso.get().load(R.drawable.demos).into(imgView)
            else
               return this.Imagem.toString()
        }
        else return this.ImageUrl

    }
}
data class ReceitaViewCreate(
    var titulo :String,
    var isUserList: Boolean = true,
    var Imagem : Uri?,
    var tempo :String,
    var instrucao :String,
    var ingredientes: String
)





