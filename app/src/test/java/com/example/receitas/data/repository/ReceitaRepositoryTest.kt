package com.example.receitas.data.repository

import android.net.Uri
import com.example.receitas.data.model.ReceitaData
import com.google.common.truth.Truth.assertThat

import com.example.receitas.data.service.ReceitaBannerService

import com.example.receitas.data.service.interf.IServiceApi
import com.example.receitas.data.service.interf.IServiceReceitaDb
import com.example.receitas.domain.model.Receita
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId


@RunWith(MockitoJUnitRunner::class)
class ReceitaRepositoryTest {
    @Mock
    lateinit var   mockReceitaBanner : ReceitaBannerService
      @Mock
     lateinit var mockServoceApi : IServiceApi
      @Mock
     lateinit var mockServiceReceitaDb :IServiceReceitaDb
     @Mock
     lateinit var  receitaRepository : ReceitaRepository

    @Before
    fun setUp() = runTest {
        MockitoAnnotations.openMocks(this)
          receitaRepository= ReceitaRepository(mockServiceReceitaDb,mockServoceApi,mockReceitaBanner)
          Mockito.`when`(mockServiceReceitaDb.searchByName( ArgumentMatchers.anyString())).thenReturn(lista)
    }
    @Test
    fun `perquisarReceita_dado uma string deve retornar uma lista de receitas`()  = runTest{

       val listaPesquisa = receitaRepository.perquisarReceita("Lasanha")
        assertThat(listaPesquisa).isNotEmpty()

    }

    @After
    fun tearDown() {
    }

}

val lista = listOf(
    ReceitaData().apply {
        this.nome= "Feijoaado"
        this.time = "34"
        this.instrucao= "Deixar no fogo por 4 minutos"
        this.image ="primeira imagem"
        this.imageLink = "link imagem"
        this.isUserList = true
    },
    ReceitaData().apply {
        this.nome= "Lasanha"
        this.time = "34"
        this.instrucao= "Deixar no fogo por 4 minutos"
        this.image ="primeira imagem"
        this.imageLink = "link imagem"
        this.isUserList = true
    },
    ReceitaData().apply {
        this.nome= "cafe"
        this.time = "34"
        this.instrucao= "Deixar no fogo por 4 minutos"
        this.image ="primeira imagem"
        this.imageLink = "link imagem"
        this.isUserList = true
    }
)