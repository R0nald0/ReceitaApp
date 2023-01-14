package com.example.receitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var rcView: RecyclerView
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
