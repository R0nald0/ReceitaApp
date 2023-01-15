package com.example.receitas.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.adapter.ReceitaAdapter
import com.example.receitas.data.repository.ReceitaRepository
import com.example.receitas.data.service.ReceitaService
import com.example.receitas.databinding.ActivityMainBinding
import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.useCase.ReceitaUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var rcView: RecyclerView
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var repository :ReceitaRepository? = null
    private  lateinit var  useCase :ReceitaUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val service = ReceitaService()
        repository = ReceitaRepository(service)
        useCase = ReceitaUseCase(repository!!)
      CoroutineScope(Dispatchers.IO).launch {
          useCase.listarReceitar()
          Log.i("Receita-", "onCreate://////////////////// ")

         // useCase.recuperaReceitaPorId(1)
          /*var  receita = Receita(
              4,"Cafe",2,"30min", listOf<String>("cafe moido", "açucar"))*/
         // useCase.criarReceita(receita)

         // useCase.atualizarReceita(0,receita)
         // useCase.deletarReceita(0)
         //  useCase.listarReceitar()
      }






     /*   rcView =findViewById(R.id.idRcView)
        rcView.adapter = ReceitaAdapter()
        rcView.layoutManager =LinearLayoutManager(this)
        rcView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
*/
        with(binding){
            idRcView.adapter = ReceitaAdapter()
            binding.idRcView.layoutManager = LinearLayoutManager(applicationContext)
        }

    }
}
