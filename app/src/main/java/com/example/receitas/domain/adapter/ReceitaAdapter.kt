package com.example.receitas.domain.adapter

import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.R

import com.example.receitas.databinding.RcvItemLayoutBinding
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.view.DetalhesActivity
import com.squareup.picasso.Picasso


class ReceitaAdapter(
    private val onClick :(ReceitaView)->Unit
) :RecyclerView.Adapter<ReceitaAdapter.ReceitasViewHolder>(){

    private var listReceita = listOf<ReceitaView>()

    fun adicionarLista(lista : MutableList<ReceitaView> ){
         listReceita = lista
         notifyDataSetChanged()
    }

     inner class  ReceitasViewHolder(item:RcvItemLayoutBinding) : RecyclerView.ViewHolder(item.root){

         private lateinit var itembinding: RcvItemLayoutBinding
           init {
               itembinding = item
           }
            fun bind(receita: ReceitaView,onClick: (ReceitaView)->Unit){
                  itembinding.txvTituloReceita.text = receita.titulo.uppercase()
                  itembinding.txvTempoTeceita.text =receita.tempo

                  if (receita.ImageUrl.isNotEmpty() ){
                       Picasso.get().load(receita.ImageUrl).into(itembinding.imgReceita)
                  }else{
                      Picasso.get().load(R.drawable.demos).into(itembinding.imgReceita)
                  }

               itemView.setOnClickListener {
                    val intent = Intent(it.context, DetalhesActivity::class.java )
                     intent.putExtra("receita",receita)
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
        val receita = listReceita[position]
        holder.bind(receita,onClick )
    }

    override fun getItemCount(): Int {
        return  listReceita.size
    }




}
