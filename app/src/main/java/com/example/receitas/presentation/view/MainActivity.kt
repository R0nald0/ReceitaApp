package com.example.receitas.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.adapter.ReceitaAdapter
import com.example.receitas.databinding.ActivityMainBinding
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var rcView: RecyclerView
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var adapter :ReceitaAdapter? = null
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

     /*   rcView =findViewById(R.id.idRcView)
        rcView.adapter = ReceitaAdapter()
        rcView.layoutManager =LinearLayoutManager(this)
        rcView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
*/
        adapter = ReceitaAdapter()

        with(binding){
            idRcView.adapter = adapter
            binding.idRcView.layoutManager = LinearLayoutManager(applicationContext)
        }

     val receitaObserver = viewModel.listar()
        receitaObserver.observe(this){
             if (it !=null ){
                 adapter!!.adicionarLista(it as MutableList<ReceitaView>)
             }
        }

        binding.fabAdicionar.setOnClickListener {
            viewModel.criarReceita()
        }

    }

}

