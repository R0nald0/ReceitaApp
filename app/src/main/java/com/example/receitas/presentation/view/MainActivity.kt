package com.example.receitas.presentation.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.view.WindowManager
import android.widget.LinearLayout

import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.R
import com.example.receitas.adapter.AreaListAdapter
import com.example.receitas.adapter.ReceitaAdapter
import com.example.receitas.adapter.UserReceitasAdapter
import com.example.receitas.constant.Const
import com.example.receitas.data.model.Dto.Area
import com.example.receitas.data.model.Dto.MealAreaList
import com.example.receitas.data.retrofiApi.RetrofitGetApi
import com.example.receitas.data.retrofiApi.TheMealApi
import com.example.receitas.databinding.ActivityMainBinding
import com.example.receitas.databinding.CadastrarReceitaLayoutBinding
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var rcView: RecyclerView
     private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var adapter :ReceitaAdapter? = null
    private var areaAdapter :AreaListAdapter? = null
    private var userReceitasAdapter :UserReceitasAdapter? = null
    private var linearLayoutManager :LinearLayoutManager? = null

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


     /*   rcView =findViewById(R.id.idRcView)
        rcView.adapter = ReceitaAdapter()
        rcView.layoutManager =LinearLayoutManager(this)
        rcView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
*/
        initAdpaters()
        initObservers()
        initListeners()


        binding.fabAdicionar.setOnClickListener {
            viewModel.criarReceita()
            // exibirDialog()

        }
    }

    fun exibirDialog(){
        val diolog = BottomSheetDialog(this,R.style.BottomSheetDialogg).apply {
             this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        val bottomSheetDiaolog : CadastrarReceitaLayoutBinding =
                    CadastrarReceitaLayoutBinding.inflate(layoutInflater,null,false)
        diolog.setContentView(bottomSheetDiaolog.root)
        diolog.show()
    }

    fun initAdpaters(){

        adapter = ReceitaAdapter{receitaView->
            viewModel.deletarTodos(receitaView)
        }
        areaAdapter = AreaListAdapter{
            viewModel.recuperarArea(it)
        }
        userReceitasAdapter = UserReceitasAdapter {

        }

    }

    fun initObservers(){
        viewModel.listaReceitaLiveData.observe(this){
            if (it !=null ){
                adapter!!.adicionarLista( it as MutableList<ReceitaView>)
            }else{
                Toast.makeText(this, "Lista vazia", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.userListReceita.observe(this){
             if (it.isNotEmpty()){
                 userReceitasAdapter?.carregarListaDeReceitas(it as MutableList<ReceitaView>)
             }
        }

        viewModel.areas.observe(this){
            if (it != null){
                areaAdapter?.addList(it as MutableList<Area>)
            }else{
                Toast.makeText(this, "Lista Area vazia", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.listCarregada.observe(this){
             if (it == true){
                 Toast.makeText(this, "carregou", Toast.LENGTH_LONG).show()

             }else{
                 Toast.makeText(this, "Carregando..", Toast.LENGTH_LONG).show()
             }
        }
        viewModel.listVazia.observe(this){
            if (it ==true){
                Toast.makeText(this, "Lista de receitas user esta vazia", Toast.LENGTH_LONG).show()
                binding.txvListaReceitaVaziaTexto.visibility = View.VISIBLE
                binding.txvCriarReceita.visibility = View.VISIBLE
            }else{
                Toast.makeText(this, "Lista de receitas user contem receitas", Toast.LENGTH_LONG).show()
                binding.txvListaReceitaVaziaTexto.visibility = View.GONE
                binding.txvCriarReceita.visibility = View.GONE
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

            txvCriarReceita.setOnClickListener {
                viewModel.criarReceita()
            }
            searchViewBtn.queryHint ="Buscar receita"
        }
    }

}

