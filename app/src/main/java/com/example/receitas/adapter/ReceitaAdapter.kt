package com.example.receitas.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.R
import com.example.receitas.databinding.RcvItemLayoutBinding
import com.example.receitas.domain.model.Receita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.view.DetalhesActivity

class ReceitaAdapter() :RecyclerView.Adapter<ReceitaAdapter.ReceitasViewHolder>(){
    var listReceita = listOf<ReceitaView>()
    fun adicionarLista(lista : MutableList<ReceitaView> ){
         listReceita = lista
         notifyDataSetChanged()
    }


     inner class  ReceitasViewHolder(item:RcvItemLayoutBinding) : RecyclerView.ViewHolder(item.root){

           private lateinit var itembinding: RcvItemLayoutBinding

           init {
               itembinding = item
           }
            fun bind(receita: ReceitaView){
                  itembinding.idTxvTituloReceita.text = receita.titulo.uppercase()
                  itembinding.idTxvTempoReceita.text =receita.tempo
                 itembinding.idImvReceirta.setImageDrawable(
                   ContextCompat.getDrawable(itemView.context,receita.Imagem)
               )

               itemView.setOnClickListener {
                   val intent = Intent(it.context, DetalhesActivity::class.java )
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
