package com.example.receitas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.databinding.RcvItemLayoutBinding

class ReceitaAdapter() :RecyclerView.Adapter<ReceitaAdapter.ReceitasViewHolder>(){

     var listReceita = mutableListOf<Receita>(
         Receita("escondidinho de Camarão",R.drawable.carne1,"35min",
             listOf(
                 "1 Kg de camarão branco limpo",
                 "Azeite de oliva",
                 "2 dentes de alho picados ou amassados",
                 "Sal a gosto",
                 "1 cebola média picada",
                 "1 tomate grande picado",
                 "Salsinha e coentro a gosto",
             )
         ),
         Receita("panqueca de carne moída",R.drawable.carne2,"45min",
                 listOf(
                     "1 e 1/2 xícara (chá) de farinha de trigo",
                     "1 xícara (chá) de leite",
                     "2 ovos",
                     "4 colheres (sopa) de óleo",
                     "sal á gosto",
                     "300 g de carne moída",
                     "2 colheres (sopa) de cebola picada ou ralada",
                 )
         ),
         Receita("rocambole de carne moída",R.drawable.carne3,"25min",
                 listOf(
                     "1/2 kg de carne moída",
                     "1 pacote de sopa de cebola",
                     "presunto fatiado",
                     "queijo fatiado",
                     "tempero verde",
                     "sal a gosto",
                 )
         ),
         Receita("escondidinho de carne seca",R.drawable.carne4,"50min",
             listOf(
                 "1 kg de mandioca cozida",
                 "1 lata de creme de leite com soro",
                 "2 colheres de margarina",
                 "1/2 kg de carne-seca dessalgada e cozida",
                 "1 cebola média picadinha",
                 "4 dentes de alho esmagados",
                 "2 tomates sem casca e picados",
                 "sal e pimenta a gosto",
                 "queijo ralado a gosto",
              )
             ),
     )

     inner class  ReceitasViewHolder(item:RcvItemLayoutBinding) : RecyclerView.ViewHolder(item.root){

           private lateinit var itembinding: RcvItemLayoutBinding

           init {
               itembinding = item
           }
            fun bind(receita: Receita){
                  itembinding.idTxvTituloReceita.text = receita.titulo.uppercase()
                  itembinding.idTxvTempoReceita.text =receita.tempo
                itembinding.idImvReceirta.setImageDrawable(
                   ContextCompat.getDrawable(itemView.context,receita.Imagem)
               )

               itemView.setOnClickListener {
                   val intent = Intent(it.context,DetalhesActivity::class.java )
                    var  extras = intent.putExtra("receita",receita)
                    ContextCompat.startActivity(it.context,intent,null)
               }
            }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceitasViewHolder {
    //  val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.rcv_item_layout,parent,false)
        val binding = RcvItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return ReceitasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceitasViewHolder, position: Int) {
        var receita = listReceita[position]
        holder.bind(receita)
    }

    override fun getItemCount(): Int {
        return  listReceita.size
    }




}
