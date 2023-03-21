package com.example.receitas.domain.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.R
import com.example.receitas.shared.constant.Const
import com.example.receitas.databinding.RcvItemLayoutBinding
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.view.DetalhesActivity
import com.squareup.picasso.Picasso

class UserReceitasAdapter(
    private val onClick :(String)->Unit
) :RecyclerView.Adapter<UserReceitasAdapter.UserReceitasViewHolder>() {

    private  var listUserReceita  = listOf<ReceitaView>()

    fun carregarListaDeReceitas(listaReceita:List<ReceitaView>){
        listUserReceita = listaReceita
        notifyDataSetChanged()
    }

    inner class UserReceitasViewHolder(item :RcvItemLayoutBinding):RecyclerView.ViewHolder(item.root){
       private val binding :RcvItemLayoutBinding

       init {
           binding =item
       }

        fun bind(receitaView :ReceitaView,funDelete:(String)->Unit){
              binding.txvTempoTeceita.text = "${receitaView.tempo}min"
              binding.txvTituloReceita.text = receitaView.titulo

               val image = receitaView.checkImg()
               if (image != null)Picasso.get().load(Uri.parse(image)).into(binding.imgReceita)
               else Picasso.get().load(R.drawable.demos).into(binding.imgReceita)


            itemView.setOnClickListener {
                    val intent = Intent(itemView.context,DetalhesActivity::class.java)
                     intent.putExtra(Const.TEXT_INTENT_EXTRAS_RECEITA,receitaView)
                   ContextCompat.startActivity(itemView.context,intent,null)
              }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReceitasViewHolder {
        val viewInit = RcvItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserReceitasViewHolder(viewInit)
    }

    override fun getItemCount(): Int = listUserReceita.size

    override fun onBindViewHolder(holder: UserReceitasViewHolder, position: Int) {
        val receitaView =listUserReceita[position]
        holder.bind(receitaView,onClick)
    }
}