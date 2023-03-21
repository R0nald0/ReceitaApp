package com.example.receitas.domain.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.receitas.R
import com.example.receitas.databinding.SearchListItemBinding
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.view.DetalhesActivity
import com.squareup.picasso.Picasso

class SearchListAdapter :Adapter<SearchListAdapter.SearchViewHolder>() {
    var listItemSearch = listOf<ReceitaView>()
    fun carregarItemList (list : List<ReceitaView>){
        listItemSearch = list
        notifyDataSetChanged()
    }
    inner  class  SearchViewHolder(itemLayout:SearchListItemBinding) : ViewHolder(itemLayout.root){
        private val binding : SearchListItemBinding
        init {
            binding = itemLayout
        }
        fun bind(receitaView :ReceitaView){
            val image = receitaView.checkImg()
            if (image != null)Picasso.get().load(Uri.parse(image)).into(binding.imgSearchReceita)
            else Picasso.get().load(R.drawable.demos).into(binding.imgSearchReceita)

            binding.txvTituloSearchReceita.text = receitaView.titulo
            itemView.setOnClickListener {
                val intens = Intent(it.context,DetalhesActivity::class.java)
                     intens.putExtra("receita",receitaView)
                ContextCompat.startActivity(it.context,intens,null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
         val view = SearchListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  SearchViewHolder(view)
    }

    override fun getItemCount(): Int = listItemSearch.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
       val itemReceita = listItemSearch[position]
        holder.bind(itemReceita)
    }
}