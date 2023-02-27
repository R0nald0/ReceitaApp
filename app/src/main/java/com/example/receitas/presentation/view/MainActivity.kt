package com.example.receitas.presentation.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.view.WindowManager
import android.widget.SearchView

import android.widget.Toast
import androidx.activity.viewModels
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
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var rcView: RecyclerView
     private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var adapter : ReceitaAdapter? = null
    private var areaAdapter : AreaListAdapter? = null
    private var userReceitasAdapter : UserReceitasAdapter? = null
    private var linearLayoutManager :LinearLayoutManager? = null

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAdpaters()
        initObservers()
        initListeners()



    }

    override fun onStart() {
        super.onStart()
        mainViewModel.listar()
        mainViewModel.listarAreas()
        mainViewModel.recuperarArea("Unknown")
    }



    fun initAdpaters(){

        adapter = ReceitaAdapter{receitaView->
            mainViewModel.deletarTodos(receitaView)
        }
        areaAdapter = AreaListAdapter{
            mainViewModel.recuperarArea(it)
        }
        userReceitasAdapter = UserReceitasAdapter {

        }

    }

    fun initObservers(){
        mainViewModel.listaReceitaLiveData.observe(this){
            if (it !=null ){
                adapter!!.adicionarLista( it as MutableList<ReceitaView>)
            }else{
                showToast("Lista vazia")
            }
        }

        mainViewModel.resultadoListConsultaLiveData.observe(this){
             if (it.sucesso){
                    if (it.list.isNotEmpty()){
                        userReceitasAdapter?.carregarListaDeReceitas(it.list as MutableList<ReceitaView>)
                        binding.txvListaReceitaVaziaTexto.visibility =View.GONE
                        binding.txvCriarReceita.visibility =View.GONE
                    }else{
                        binding.txvListaReceitaVaziaTexto.visibility =View.VISIBLE
                        binding.txvCriarReceita.visibility =View.VISIBLE
                    }
             }else{
                 showToast(it.mensagem)
             }
        }

        mainViewModel.areaRsultadoConsulta.observe(this){
            if (it.sucesso){
                areaAdapter?.addList(it.list as MutableList<Area>)
            }else{
             showToast(it.mensagem)
            }
        }

    }
   fun showToast(messenge :String){
       Toast.makeText(this, messenge, Toast.LENGTH_LONG).show()
   }

    fun initListeners(){
        with(binding){
            idRcView.adapter = adapter
            idRcView.layoutManager =LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)

            rcvArea.adapter= areaAdapter
            rcvArea.layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)

            rcvUserReceitaList.adapter=userReceitasAdapter
            rcvUserReceitaList.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)

            txvCriarReceita.setOnClickListener {
            //    mainViewModel.criarReceita()
            }
            imgButtonAddReceita.setOnClickListener {
               startActivity(Intent(this@MainActivity,SalvarEditarActivity::class.java))
            }

            searchViewBtn.queryHint ="Buscar receita"

            searchViewBtn.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               Const.exibilog("${newText}")
               return true
            }

        } )

        }
    }

}

