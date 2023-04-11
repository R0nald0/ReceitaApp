package com.example.receitas.data.repository

import com.example.receitas.data.local.database.AreaHelper
import com.example.receitas.data.model.ReceitaDAO
import com.example.receitas.data.remote.model.Dto.AreaDTO
import com.google.common.truth.Truth.assertThat

import com.example.receitas.data.service.ReceitaBannerService

import com.example.receitas.data.service.interf.IServiceApi
import com.example.receitas.data.service.interf.IServiceReceitaDb
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


@RunWith(MockitoJUnitRunner::class)
class ReceitaRepositoryTest {
      @Mock
     lateinit var   mockReceitaBanner : ReceitaBannerService
      @Mock
     lateinit var mockServoceApi : IServiceApi
      @Mock
     lateinit var mockServiceReceitaDb :IServiceReceitaDb
      @Mock
      lateinit var  areaHelper: AreaHelper
     lateinit var  receitaRepository : ReceitaRepository

    @Before
    fun setUp() = runTest {
        MockitoAnnotations.openMocks(this)
          receitaRepository= ReceitaRepository(mockServiceReceitaDb,mockServoceApi,mockReceitaBanner,areaHelper)
          Mockito.`when`(mockServiceReceitaDb.searchByName( ArgumentMatchers.anyString())).thenReturn(lista)
    }
    @Test
    fun `perquisarReceita_dado uma string deve retornar uma lista de receitas`()  = runTest{

       val listaPesquisa = receitaRepository.perquisarReceita("Lasanha")
        assertThat(listaPesquisa).isNotEmpty()

    }

    @Test
    fun  `saveListAreaApiToDb_must return true if it manages to save the list in the database`()= runTest(){
        Mockito.`when`(mockServoceApi.getArealMeal()).thenReturn(
            listOf(
                AreaDTO("canada"),
                AreaDTO("espanha")
            )
        )
        val result = receitaRepository.saveListAreaApiToDb()
        assertThat(result).isTrue()
    }
    @Test
    fun  `saveListAreaApiToDb_must return false if not saving the list in the database`()= runTest(){
        Mockito.`when`(mockServoceApi.getArealMeal()).thenReturn(
            listOf()
        )
        val result = receitaRepository.saveListAreaApiToDb()
        assertThat(result).isFalse()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun saveListAreaApiToDb() {
    }

}

val lista = listOf(
    ReceitaDAO().apply {
        this.nome= "Feijoaado"
        this.time = "34"
        this.instrucao= "Deixar no fogo por 4 minutos"
        this.image ="primeira imagem"
        this.imageLink = "link imagem"
        this.isUserList = true
    },
    ReceitaDAO().apply {
        this.nome= "Lasanha"
        this.time = "34"
        this.instrucao= "Deixar no fogo por 4 minutos"
        this.image ="primeira imagem"
        this.imageLink = "link imagem"
        this.isUserList = true
    },
    ReceitaDAO().apply {
        this.nome= "cafe"
        this.time = "34"
        this.instrucao= "Deixar no fogo por 4 minutos"
        this.image ="primeira imagem"
        this.imageLink = "link imagem"
        this.isUserList = true
    }
)