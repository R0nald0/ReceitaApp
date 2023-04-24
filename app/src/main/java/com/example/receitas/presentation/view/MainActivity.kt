package com.example.receitas.presentation.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.SearchView

import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receitas.R
import com.example.receitas.domain.adapter.AreaListAdapter
import com.example.receitas.domain.adapter.ReceitaAdapter
import com.example.receitas.domain.adapter.UserReceitasAdapter
import com.example.receitas.databinding.ActivityMainBinding
import com.example.receitas.domain.adapter.SearchListAdapter
import com.example.receitas.domain.model.Area
import com.example.receitas.domain.results.AppStateRequest
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.viewmodel.MainViewModel
import com.example.receitas.shared.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition {
           CoroutineScope(Dispatchers.IO).launch {
               delay(2000)
           }
            false
        }

        setContentView(binding.root)
        supportActionBar?.hide()
        initAdpaters()
        initObservers()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getListUserReceitas()
        mainViewModel.listarAreas()
        mainViewModel.recuperarArea(areaAdapter?.areaName)
        mainViewModel.getListReceitaBanner()
    }

    fun initAdpaters(){

        adapter = ReceitaAdapter{receitaView-> }

        areaAdapter = AreaListAdapter{nameArea ->
            mainViewModel.recuperarArea(nameArea)
        }

        userReceitasAdapter = UserReceitasAdapter {}
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

        mainViewModel.listaReceitaApiLiveData.observe(this){
            if (it.isNotEmpty() ){
                adapter!!.adicionarLista( it as MutableList<ReceitaView>)
            }else{
                applicationContext.showToast(getString(R.string.lista_vazia))
            }
        }

        mainViewModel.resultadoListConsultaLiveData.observe(this){
             if (it.sucesso){
                    if (it.list.isNotEmpty()){
                        userReceitasAdapter?.carregarListaDeReceitas(it.list)
                        binding.rcvUserReceitaList.visibility =View.VISIBLE
                        binding.txvListaReceitaVaziaTexto.visibility =View.GONE
                        binding.txvCriarReceita.visibility =View.GONE
                    }else{
                        binding.txvListaReceitaVaziaTexto.visibility =View.VISIBLE
                        binding.txvCriarReceita.visibility =View.VISIBLE
                        binding.rcvUserReceitaList.visibility =View.GONE
                    }
             }else{
                 applicationContext.showToast(it.mensagem)
             }
        }


        mainViewModel.pesquisaLiveData.observe(this){
             if (it.sucesso){
                 searchListAdapter?.carregarItemList(it.list)
             }else{
                 applicationContext.showToast(it.mensagem)
             }
        }
        mainViewModel.receitaBannerResultListLiveData.observe(this){resultListReceitaBanner->
             if (resultListReceitaBanner.sucesso){
                 val listImage = resultListReceitaBanner.list.map {receitaView ->
                     CarouselItem(
                         imageDrawable =receitaView.Imagem.toString().toInt(),
                          caption = "${receitaView.titulo}  ${receitaView.tempo}"
                         )
                 }
                 binding.imgSlideCarousel.setData(listImage)
             }else{
                 showToast(getString(R.string.erro_carregar))
             }
        }
        mainViewModel.appStateRequest.observe(this){
             when(it){
                AppStateRequest.loading ->{
                    binding.rcvArea.visibility = View.GONE
                    binding.rcvListReceitaApi.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                AppStateRequest.loaded ->{
                    binding.rcvArea.visibility = View.VISIBLE
                    binding.rcvListReceitaApi.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                 else -> {}
             }
        }

    }


    fun initListeners(){
        with(binding){
            rcvListReceitaApi.adapter = adapter
            rcvListReceitaApi.layoutManager =LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)


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

            initSearchBar()

        }
    }

    private fun initSearchBar() {
      binding.searchViewBtn.setOnQueryTextListener(object :SearchView.OnQueryTextListener{

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

