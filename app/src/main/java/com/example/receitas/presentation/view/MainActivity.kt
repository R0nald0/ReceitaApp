package com.example.receitas.presentation.view


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.view.WindowManager
import android.widget.SearchView

import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.R
import com.example.receitas.domain.adapter.AreaListAdapter
import com.example.receitas.domain.adapter.ReceitaAdapter
import com.example.receitas.domain.adapter.UserReceitasAdapter
import com.example.receitas.shared.constant.Const
import com.example.receitas.data.model.Dto.Area
import com.example.receitas.databinding.ActivityMainBinding
import com.example.receitas.databinding.CadastrarReceitaLayoutBinding
import com.example.receitas.domain.adapter.SearchListAdapter
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.viewmodel.MainViewModel
import com.example.receitas.shared.extension.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

     private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var adapter : ReceitaAdapter? = null
    private var areaAdapter : AreaListAdapter? = null
    private var userReceitasAdapter : UserReceitasAdapter? = null
    private var searchListAdapter : SearchListAdapter? = null
    private var areaName :String? = null

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        initAdpaters()
        initObservers()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.listar()
        mainViewModel.listarAreas()
        mainViewModel.recuperarArea(areaAdapter?.areaName)
        mainViewModel.getListReceitaBanner()
    }

    fun initAdpaters(){

        adapter = ReceitaAdapter{receitaView-> }

        areaAdapter = AreaListAdapter{nameArea ->
            mainViewModel.recuperarArea(nameArea)
        }

        userReceitasAdapter = UserReceitasAdapter {

        }
        searchListAdapter =SearchListAdapter()

    }

    fun initObservers(){
        mainViewModel.areaRsultadoConsulta.observe(this){
            if (it.sucesso){
                areaAdapter?.addList(it.list as MutableList<Area>)
            }else{
                showToast(it.mensagem)
            }
        }
        mainViewModel.areaNameObserve.observe(this){areaNameObserve ->
              if (areaNameObserve.isNotEmpty()){
                  areaName = areaNameObserve
              }

        }

        mainViewModel.listaReceitaLiveData.observe(this){
            if (it !=null ){
                adapter!!.adicionarLista( it as MutableList<ReceitaView>)
            }else{
                applicationContext.showToast("Lista vazia")
            }
        }

        mainViewModel.resultadoListConsultaLiveData.observe(this){
             if (it.sucesso){
                    if (it.list.isNotEmpty()){
                        userReceitasAdapter?.carregarListaDeReceitas(it.list)

                        binding.txvListaReceitaVaziaTexto.visibility =View.GONE
                        binding.txvCriarReceita.visibility =View.GONE
                    }else{
                        binding.txvListaReceitaVaziaTexto.visibility =View.VISIBLE
                        binding.txvCriarReceita.visibility =View.VISIBLE
                    }
             }else{
                 applicationContext.showToast(it.mensagem)
             }
        }


        mainViewModel.pesquisaLiveData.observe(this){
             if (it.sucesso){
                 searchListAdapter?.carregarItemList(it.list)
                 applicationContext.showToast(it.mensagem)
             }else{
                 applicationContext.showToast(it.mensagem)
             }
        }
        mainViewModel.receitaBannerResultListLiveData.observe(this){resultListReceitaBanner->
             if (resultListReceitaBanner.sucesso){
                 val listImage = resultListReceitaBanner.list.map {receitaView ->
                     CarouselItem(
                         imageDrawable =receitaView.Imagem.toInt(),
                          caption = "${receitaView.titulo}  ${receitaView.tempo}"
                         )
                 }
                 binding.imgSlideCarousel.setData(listImage)
             }else{
                 showToast("Erro ao carrega")
             }
        }

    }


    fun initListeners(){
        with(binding){
            idRcView.adapter = adapter
            idRcView.layoutManager =LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)


            rcvArea.adapter= areaAdapter
            rcvArea.layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)

            rcvUserReceitaList.adapter=userReceitasAdapter
            rcvUserReceitaList.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)

            rcvSearchReceitas.adapter =searchListAdapter
            rcvSearchReceitas.layoutManager =GridLayoutManager(this@MainActivity,2,)

            txvCriarReceita.setOnClickListener {
                startActivity(Intent(this@MainActivity,SalvarEditarActivity::class.java))
            }
            imgButtonAddReceita.setOnClickListener {
               startActivity(Intent(this@MainActivity,SalvarEditarActivity::class.java))
            }

            searchViewBtn.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query !=null){
                    mainViewModel.pesquisarReceita(query)
                    binding.rcvSearchReceitas.visibility = View.VISIBLE
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 if (newText !=null){
                     mainViewModel.pesquisarReceita(newText)
                     if(newText.length >0){
                         binding.rcvSearchReceitas.visibility = View.VISIBLE
                     }else{
                         binding.rcvSearchReceitas.visibility =View.GONE
                     }
                 }

               return true
            }

        } )



        }
    }

}

