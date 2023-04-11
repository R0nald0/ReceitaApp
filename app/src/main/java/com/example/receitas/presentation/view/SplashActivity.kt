package com.example.receitas.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.receitas.databinding.ActivitySplashBinding
import com.example.receitas.domain.results.AppStateList
import com.example.receitas.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        initObserver()

    }

    private fun initObserver() {
          CoroutineScope(Dispatchers.IO).launch {
              delay(2000)
              val intent = Intent(applicationContext,MainActivity::class.java)
              startActivity(intent)
          }



    }
}