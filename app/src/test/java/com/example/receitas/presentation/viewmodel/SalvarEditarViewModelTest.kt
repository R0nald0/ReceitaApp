
package com.example.receitas.presentation.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.domain.results.VerificaCampos
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate

import com.google.common.truth.Truth.assertThat
import com.jamiltondamasceno.projetotestesnapratica.utils.getOrAwaitValue
import kotlinx.coroutines.test.runTest


import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SalvarEditarViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
     lateinit var receitaUseCase: IReceitaUseCase

     private lateinit var salvarEditarViewModel: SalvarEditarViewModel
    val receitaCreata =  ReceitaViewCreate("cafe", true, Uri.EMPTY,"34","Colocar no fogo ","po de cafe")


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        salvarEditarViewModel = SalvarEditarViewModel(receitaUseCase)
    }


    @Test
    fun editarReceita() = runTest {
        val receita =  ReceitaView("asdsadsa", true,"cafe" ,Uri.EMPTY,"ima","Colocar no fogo ","34","po de cafe")
        Mockito.`when`(receitaUseCase.atualizarReceita(receita)).thenReturn(
             ResultadoOperacaoDb(true,"Atualizado")
         )
        salvarEditarViewModel.editareceita(receita)

         val resultCreate  = salvarEditarViewModel.resultadoOperacaoDb.getOrAwaitValue()
        assertThat(resultCreate.sucesso).isTrue()
        assertThat(resultCreate.mensagem).isEqualTo("Atualizado")
    }
    @Test
    fun verificarCampos() = runTest {
        Mockito.`when`(receitaUseCase.verificarCampos(receitaCreata)).thenReturn(
            VerificaCampos(receitaCreata)
        )
        salvarEditarViewModel.verificarCampos(receitaCreata)

        val result = salvarEditarViewModel.verifiacaCampoLiveData.getOrAwaitValue()

        assertThat(result.sucesso).isTrue()
    }

    @After
    fun tearDown() {
    }
}
