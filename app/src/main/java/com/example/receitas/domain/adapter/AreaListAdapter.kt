package com.example.receitas.domain.adapter

import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.R
import com.example.receitas.data.remote.model.Dto.AreaDTO
import com.example.receitas.databinding.ItemAreaLayoutBinding
import com.example.receitas.domain.model.Area

class AreaListAdapter (
     val onclick :(String)->Unit
 ): RecyclerView.Adapter<AreaListAdapter.AreaListaViewHolder>() {

    private var listArea = mutableListOf<Area>()
    private val itemSelecionado = SparseArray<Drawable>()
    var areaName  = "Unknown"

    fun addList(list : MutableList<Area>){
        listArea = list
        notifyDataSetChanged()
     }

    inner class AreaListaViewHolder(itemAreaLayotview:ItemAreaLayoutBinding) :RecyclerView.ViewHolder(itemAreaLayotview.root){
       private var bindingArea: ItemAreaLayoutBinding
        init {
             bindingArea = itemAreaLayotview
        }

        fun bind(area: Area, onclick: (String) -> Unit, position: Int){
            val padrao = itemSelecionado[position,ContextCompat.getDrawable(itemView.context,R.drawable.bg_rounded_area_name)]
            bindingArea.constrainLayout.background = padrao

            bindingArea.txtArea.text =area.strArea
            bindingArea.constrainLayout.setOnClickListener {view->
                 areaName = area.strArea
                 onclick(areaName)

                  itemSelecionado.clear()
                  notifyDataSetChanged()
                  itemSelecionado.put(position,ContextCompat.getDrawable(itemView.context,R.drawable.bg_area_item_selected))
                  bindingArea.constrainLayout.background =  itemSelecionado[0]

             }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaListaViewHolder {
        val view = ItemAreaLayoutBinding.
          inflate(LayoutInflater.from(parent.context),parent,false)
        return  AreaListaViewHolder(view)
    }

    override fun getItemCount(): Int = listArea.size

    override fun onBindViewHolder(holder: AreaListaViewHolder, position: Int) {

        val area = listArea[position]
        holder.bind(area,onclick,position)
    }






}