package com.example.receitas.domain.useCase

import android.net.Uri
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.model.Area
import com.example.receitas.domain.model.Receita
import com.example.receitas.presentation.model.ReceitaView
import com.google.common.truth.Truth.assertThat
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

@RunWith(MockitoJUnitRunner::class)
class ReceitaUseCaseTest {
    @Mock
    lateinit var  repositoryMock: IRepository

    private lateinit var useCase: IReceitaUseCase
    val listReceita = listOf(
        Receita( org.mongodb.kbson.ObjectId(),true,"cafe", Uri.EMPTY,"imageUrl","Colocar no fogo ","34","po de cafe"),
        Receita(org.mongodb.kbson.ObjectId(),true,"Pavê", Uri.EMPTY,"imageUrl","Colocar no fogo ","65"," ingrediente")
    )

    @Before
    fun setUp() {
      MockitoAnnotations.openMocks(this)
        useCase = ReceitaUseCase(repositoryMock)
    }


    @Test
    fun listarArea_retunrClassResultConsultaAreaWithTrueAndMessageOfSucesso()= runTest{
         Mockito.`when`(repositoryMock.recuperarListaArea()).thenReturn(
             listOf(
                 Area("Brasil"), Area("Canada"), Area("México")
             )
         )
        val resultConsultasAreas= useCase.listarArea()

        assertThat(resultConsultasAreas.sucesso).isTrue()
        assertThat(resultConsultasAreas.mensagem).isEqualTo("Sucesso ao carregar lista de areas")
        assertThat(resultConsultasAreas.list).hasSize(3)
        assertThat(resultConsultasAreas.list[0]).isEqualTo(Area("Brasil"))
    }

    @Test
    fun listarArea_retunrClassResultConsultaAreaWithTrueAndMessageOfListEmpty()= runTest{
        Mockito.`when`(repositoryMock.recuperarListaArea()).thenReturn(
            listOf()
        )
        val resultConsultasAreas= useCase.listarArea()

        assertThat(resultConsultasAreas.sucesso).isTrue()
        assertThat(resultConsultasAreas.mensagem).isEqualTo("Lista vazia")
        assertThat(resultConsultasAreas.list).hasSize(0)
    }
    @Test
    fun listarArea_retunrClassResultConsultaAreaWithFalseAndMessageOfListEmpty()= runTest{
        Mockito.`when`(repositoryMock.recuperarListaArea()).thenThrow()
        val resultConsultasAreas= useCase.listarArea()

        assertThat(resultConsultasAreas.sucesso).isFalse()
        assertThat(resultConsultasAreas.mensagem).isEqualTo("erro ao carregar lista de areas")
        assertThat(resultConsultasAreas.list).hasSize(0)
    }

    @Test
    fun listarReceita_mustReturnResultConsultasReceitaWithTrueAndMessagemOfSueccessoAndList() = runTest {
            Mockito.`when`(repositoryMock.getUserListReceitasDb()).thenReturn(
             listReceita
            )

        val resultConslt =useCase.listarReceita()

        assertThat(resultConslt.sucesso).isTrue()
        assertThat(resultConslt.mensagem).isEqualTo("Lista carregada com sucesso")
        assertThat(resultConslt.list).hasSize(2)
        assertThat(resultConslt.list[0].titulo).isEqualTo("cafe")
    }

    @Test
    fun listarReceita_mustReturnResultConsultasReceitaWithTrueAndMessagemOfSueccessoAndListEmpty() = runTest {
        Mockito.`when`(repositoryMock.getUserListReceitasDb()).thenReturn(
            listOf()
        )

        val resultConslt =useCase.listarReceita()

        assertThat(resultConslt.sucesso).isTrue()
        assertThat(resultConslt.mensagem).isEqualTo("Lista vazia")
        assertThat(resultConslt.list).hasSize(0)
    }

    @Test
    fun listarReceita_mustReturnResultConsultasReceitaWithFalseAndMessagemOfErrorAndListEmpty() = runTest {
        Mockito.`when`(repositoryMock.getUserListReceitasDb()).thenThrow()

        val resultConslt =useCase.listarReceita()

        assertThat(resultConslt.sucesso).isFalse()
        assertThat(resultConslt.mensagem).isEqualTo("Erro ao recuperar lista")
        assertThat(resultConslt.list).hasSize(0)
    }

    @Test
    fun getReceitaByAreaName_receiveNameAreaAndReturnListOfReceitaViewFromArea()= runTest {
        Mockito.`when`(repositoryMock.recuperarReceitasPorArea(ArgumentMatchers.anyString())).thenReturn(
            listReceita
        )
         val listReceitaV =  useCase.getReceitaByAreaName("brasil")
         assertThat(listReceitaV.size).isEqualTo(2)
         assertThat(listReceitaV[1].titulo).isEqualTo("Pavê")
    }

    @Test
    fun getReceitaByAreaName_receiveNameAreaAndReturnListEmpty()= runTest {
        Mockito.`when`(repositoryMock.recuperarReceitasPorArea(ArgumentMatchers.anyString())).thenThrow()
        val listReceitaV =  useCase.getReceitaByAreaName("brasil")
        assertThat(listReceitaV.size).isEqualTo(0)
    }

    @Test
    fun getReceitaByName_mustReceiveNameReceitaNameAndReturnDetails()= runTest {
        Mockito.`when`(repositoryMock.recuperaReceitaPorNome(ArgumentMatchers.anyString())).thenReturn(
            Receita(org.mongodb.kbson.ObjectId(),true,"Pavê", Uri.EMPTY,"imageUrl","Colocar no fogo ","65"," ingrediente")
        )
       val receitaDetail = useCase.getReceitaByName("Pavê")

        assertThat(receitaDetail.isSuccess).isTrue()
        assertThat(receitaDetail.getOrThrow().titulo).isEqualTo("Pavê")
    }

    @Test
    fun getReceitaByName_mustReturnErroMsg()= runTest {
        Mockito.`when`(repositoryMock.recuperaReceitaPorNome(ArgumentMatchers.anyString())).thenThrow()
        val receitaDetail = useCase.getReceitaByName("Pavê")

        assertThat(receitaDetail.isFailure).isTrue()
    }
    @Test
    fun criarReceita_mustRetutnResultadoOperacaoDbWithTrueAndMessageSuccess()= runTest{
        val receitaTest =Receita(org.mongodb.kbson.ObjectId(),true,"Pavê", Uri.EMPTY,"imageUrl","Colocar no fogo ","65"," ingrediente")
        Mockito.lenient().`when`(repositoryMock.criarReceita(receitaTest)).thenReturn(
            true
        )
        val resultadoOperacaoDb = useCase.criarReceita(receitaTest)
        assertThat(resultadoOperacaoDb.sucesso).isTrue()
        assertThat(resultadoOperacaoDb.mensagem).isEqualTo("salvo com sucesso")
    }
    @Test
    fun criarReceita_mustRetutnResultadoOperacaoDbWithFalseAndMessageErroByTituloEmpty()= runTest{
        val receitaTest =Receita(org.mongodb.kbson.ObjectId(),true,"", Uri.EMPTY,"imageUrl","Colocar no fogo ","65"," ingrediente")
        Mockito.lenient().`when`(repositoryMock.criarReceita(receitaTest)).thenReturn(
            false
        )
        val resultadoOperacaoDb = useCase.criarReceita(receitaTest)
        assertThat(resultadoOperacaoDb.sucesso).isFalse()
        assertThat(resultadoOperacaoDb.mensagem).isEqualTo("preenchos campos o nome")
    }
    @Test
    fun criarReceita_mustRetutnResultadoOperacaoDbWithFalseAndMessageErroByIngredienteEmpty()= runTest{
        val receitaTest =Receita(org.mongodb.kbson.ObjectId(),true,"Pavê", Uri.EMPTY,"imageUrl","Colocar no fogo ","65","")
        Mockito.lenient().`when`(repositoryMock.criarReceita(receitaTest)).thenReturn(
            false
        )
        val resultadoOperacaoDb = useCase.criarReceita(receitaTest)
        assertThat(resultadoOperacaoDb.sucesso).isFalse()
        assertThat(resultadoOperacaoDb.mensagem).isEqualTo("preencha os ingredientes")
    }

    @Test
    fun criarReceita_mustRetutnResultadoOperacaoDbWithFalseAndMessageErroByInstrucoesEmpty()= runTest{
        val receitaTest =Receita(org.mongodb.kbson.ObjectId(),true,"Pavê", Uri.EMPTY,"imageUrl","","65","ingrediente")
       Mockito.lenient().`when`(repositoryMock.criarReceita(receitaTest)).thenReturn(
            false
        )
        val resultadoOperacaoDb = useCase.criarReceita(receitaTest)
        assertThat(resultadoOperacaoDb.sucesso).isFalse()
        assertThat(resultadoOperacaoDb.mensagem).isEqualTo("preencha as instrucoes")
    }

    @After
    fun tearDown() {
    }
}