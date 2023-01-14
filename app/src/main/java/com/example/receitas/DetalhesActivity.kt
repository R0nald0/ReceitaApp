package com.example.receitas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.receitas.databinding.ActivityDetalhesBinding

class DetalhesActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetalhesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

       /* txvTituloDetalhes =findViewById(R.id.id_txv_tituloReceitaDetalhes)
        txvTempoDetalhe =findViewById(R.id.id_txv_tempoReceitaDetalhes)
        txvReceitaLis =findViewById(R.id.id_txv_ingredientesReceitaDetalhes)
        imgDetalhes = findViewById(R.id.id_imgReceitaDetalhes)
        btnVoltar =findViewById(R.id.id_BtnVoltarDetalhes)
*/



            val  extra = intent.extras
            val receita  = extra?.getParcelable<Receita>("receita")

            if (receita != null) {
                  binding.idImgReceitaDetalhes.setImageResource(receita.Imagem)
                  binding.TxvTempoReceitaDetalhes.text = receita.tempo
                  binding.TxvTituloReceitaDetalhes.text = receita.titulo.uppercase()
                var ingrediente =""
                receita.ingredientes.map {
                    ingrediente += "- $it \n"
                    binding.idTxvIngredientesReceitaDetalhes.text = ingrediente
                }
            }

            binding.idBtnVoltarDetalhes.setOnClickListener {
                finish()
            }







      /*  if(receita !=null){
            Toast.makeText(this, receita.titulo, Toast.LENGTH_SHORT).show()
            imgDetalhes.setImageDrawable(getDrawable(receita.Imagem))
            txvTituloDetalhes.text= receita.titulo.uppercase()
            txvTempoDetalhe.text = receita.tempo
            var ingrediente =""
            receita.ingredientes.map {
                ingrediente += "- $it \n"
                txvReceitaLis.text = ingrediente
            }
        }
*/



    }
}
