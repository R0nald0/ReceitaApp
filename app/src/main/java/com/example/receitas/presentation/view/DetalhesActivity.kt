package com.example.receitas.presentation.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import com.example.receitas.R
import com.example.receitas.shared.constant.Const
import com.example.receitas.domain.model.Receita
import com.example.receitas.databinding.ActivityDetalhesBinding
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.viewmodel.DetalhesViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalhesActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetalhesBinding
    private val viewModel :DetalhesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initObserver()
        initBinds()

    }

    fun initBinds(){
        with(binding) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scrollView2.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    if (scrollY > oldScrollY  ) {
                        //idBtnVoltarDetalhes.visibility = View.GONE
                         btnPlay.visibility =View.GONE
                    } else {
                        //idBtnVoltarDetalhes.visibility = View.VISIBLE
                        btnPlay.visibility =View.VISIBLE
                    }

                }
            } else {
                TODO("VERSION.SDK_INT < M")
            }

            imgBtnVoltar.setOnClickListener {
                startActivity(Intent(this@DetalhesActivity,MainActivity::class.java))
            }
            btnPlay.setOnClickListener {
                //TODO iniciar video
            }

            setSupportActionBar(toolbar)
            //TODO verificar bakcButton
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getReceitaByName(getNameReceita())
    }

 fun initObserver(){
     viewModel.itemReceita?.observe(this){
         if (it != null){
             getViewReceita(it)
         }
     }
     viewModel.cerregandoLiveData.observe(this){
         // TODO  progressBar
     }
 }
    fun getNameReceita():ReceitaView?{
        val  extra = intent.extras
        val receita  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            extra?.getParcelable("receita",ReceitaView::class.java)
        } else {
            extra?.getParcelable<ReceitaView>("receita") as ReceitaView
        }

        return if (receita != null) {
            Const.exibilog("receita : ${receita.idRealm}")
            receita
        }
        else return null

    }

    fun  getViewReceita(receitaView: ReceitaView) {
        with(binding) {
            Const.exibilog("reiceta image -- ${receitaView.Imagem}")

            if(receitaView.ImageUrl.isEmpty() && receitaView.Imagem == "null"){
                Picasso.get().load(R.drawable.demos).into(idImgReceitaDetalhes)
            }else if (receitaView.ImageUrl.isEmpty()){
                Picasso.get().load(Uri.parse(receitaView.Imagem)).into(idImgReceitaDetalhes)
            }
            else Picasso.get().load(receitaView.ImageUrl).into(idImgReceitaDetalhes)

             txvReceitaInstrcoes.text = receitaView.instrucao
            TxvTempoReceitaDetalhes.text = receitaView.tempo
            TxvTituloReceitaDetalhes.text = receitaView.titulo.uppercase()
            idTxvIngredientesReceitaDetalhes.text =receitaView.ingredientes


        }
    }

    fun addReceitaToUserlist(){

    }

    fun createMenu(){
        addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                  val myMenu = menuInflater.inflate(R.menu.menu_detalhe_receita,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               return when(menuItem.itemId) {
                   R.id.add_receita -> {
                       Toast.makeText(applicationContext, "add menu", Toast.LENGTH_LONG).show()
                       true
                   }
                  else -> false
               }
            }

        })
    }
}
