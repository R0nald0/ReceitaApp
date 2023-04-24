package com.example.receitas.presentation.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.model.Area
import com.example.receitas.domain.results.ResultConsultasAreas
import com.example.receitas.domain.results.ResultConsultasReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.viewmodel.utils.CoroutineRule
import com.google.common.truth.Truth.assertThat
import com.jamiltondamasceno.projetotestesnapratica.utils.getOrAwaitValue
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    @Mock
    lateinit var receitaUseCaseMock: IReceitaUseCase
    private lateinit var mainViewModel: MainViewModel

    val listaReceita = listOf(
        ReceitaView("1",true,"cafe", Uri.EMPTY,"imageUrl","Colocar no fogo ","34","po de cafe"),
        ReceitaView("1",true,"Pavê", Uri.EMPTY,"imageUrl","Colocar no fogo ","65"," ingrediente")
    )

    @Before
    fun setUp() {

        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(receitaUseCaseMock)

    }

    @Test
    fun listarAreas_veificaResultConsultasAreaComListaAreaETrue() = runTest {
         Mockito.`when`(receitaUseCaseMock.listarArea()).thenReturn(
             ResultConsultasAreas(
                 true,"Sucesso ao carregar lista de areas",
                 listOf(
                     Area("Brasil"),Area("Canada"),Area("México")
                 )
             )
         )
        mainViewModel.listarAreas()
        val result = mainViewModel.areaRsultadoConsulta.getOrAwaitValue()

         assertThat(result.sucesso).isTrue()
         assertThat(result.mensagem).isEqualTo("Sucesso ao carregar lista de areas")
         assertThat(result.list).contains(Area("Brasil"))
    }
    @Test
    fun listarAreas_deveRetornarResultConsultasAreaComListaAreaVaziaEFalse() = runTest {
        Mockito.`when`(receitaUseCaseMock.listarArea()).thenReturn(
            ResultConsultasAreas(
                false,"erro ao carregar lista de areas",
                listOf()
            )
        )
        mainViewModel.listarAreas()
        val result = mainViewModel.areaRsultadoConsulta.getOrAwaitValue()

        assertThat(result.sucesso).isFalse()
        assertThat(result.mensagem).isEqualTo("erro ao carregar lista de areas")
        assertThat(result.list).isEmpty()
    }

    @Test
    fun getListUserReceitas_verificaResultConsultasReceitaComListaDeReceitaEtrue() = runTest{
            Mockito.`when`(receitaUseCaseMock.listarReceita()).thenReturn(
                ResultConsultasReceita(
                    true,"Lista carregada com sucesso", listaReceita

             )
            )
        mainViewModel.getListUserReceitas()

        val result = mainViewModel.resultadoListConsultaLiveData.getOrAwaitValue()

        assertThat(result.sucesso).isTrue()
        assertThat(result.mensagem).contains("Lista carregada com sucesso")
        assertThat(result.list[1].titulo).isEqualTo("Pavê")
    }

    @Test
    fun getListUserReceitas_verificaResultConsultasReceitaComListaVziaDeReceitaEfalse() = runTest{
        Mockito.`when`(receitaUseCaseMock.listarReceita()).thenReturn(
            ResultConsultasReceita(
                false,"Erro ao recuperar lista",
                listOf()
            )
        )
        mainViewModel.getListUserReceitas()

        val consulta = mainViewModel.resultadoListConsultaLiveData.getOrAwaitValue()

        assertThat(consulta.sucesso).isFalse()
        assertThat(consulta.mensagem).isEqualTo("Erro ao recuperar lista")
        assertThat(consulta.list).hasSize(0)
    }

    @Test
    fun getReceitaByAreaName_deveVerificarUmaListaDeReceitaAPartirDeUmaString() =  runTest {
        Mockito.`when`(receitaUseCaseMock.getReceitaByAreaName(ArgumentMatchers.anyString())).thenReturn(
          listaReceita
        )
        mainViewModel.recuperarArea("area")

        val listretorn = mainViewModel.listaReceitaApiLiveData.getOrAwaitValue()
        assertThat(listretorn[1].titulo).isEqualTo("Pavê")
    }

    @Test
    fun getReceitaByAreaName_deveRetornar_Uma_Lista_Vazia_A_Partir_De_Uma_Nome_De_Area_Desconhceido() =  runTest {
        Mockito.`when`(receitaUseCaseMock.getReceitaByAreaName(ArgumentMatchers.anyString())).thenReturn(
            listOf()
        )
        mainViewModel.recuperarArea("desconhecido")

        val listretorn = mainViewModel.listaReceitaApiLiveData.getOrAwaitValue()
        assertThat(listretorn).hasSize(0)
    }

    @After
    fun tearDown() {
    }
}