package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate
import com.example.receitas.shared.constant.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalhesViewModel @Inject constructor(
   private val receitaUseCase :IReceitaUseCase
): ViewModel() {
    private val _receitaLiveData = MutableLiveData<ReceitaView>()
    val receitaView : LiveData<ReceitaView>
       get() = _receitaLiveData

    val cerregandoLiveData = MutableLiveData<Boolean>()
    val isReceitaListUser  = MutableLiveData<Boolean>()

    val resultOperacaoLiveDataDelete =MutableLiveData<ResultadoOperacaoDb>()

    val _resultOperacaoLiveDataAddReceita =MutableLiveData<ResultadoOperacaoDb>()
    val resultOperacaoLiveDataAddReceita : LiveData<ResultadoOperacaoDb>
       get() = _resultOperacaoLiveDataAddReceita

    fun getReceitaByName(receitaView:ReceitaView){
        viewModelScope.launch {
            if (receitaView.isUserList){
                _receitaLiveData.value =receitaView
            }else{
                val receita = MapReceita.receitaViewToReceita(receitaView)

                val receitaViewApi = receitaUseCase.getReceitaByName(receita.titulo)
                _receitaLiveData.postValue(receitaViewApi)
            }
        }
    }

    fun addReceitaToUserList(receitaView: ReceitaView){
            viewModelScope.launch {
                val resultadoOperacao = receitaUseCase.addReceitaToUserList(receitaView)
                _resultOperacaoLiveDataAddReceita.postValue(resultadoOperacao)
            }
    }

    fun deletar(receitaView: ReceitaView) {
        val receita = MapReceita.receitaViewToReceita(receitaView)
        viewModelScope.launch {
            val resul = receitaUseCase.deletarReceita(receita)
            resultOperacaoLiveDataDelete.postValue(resul)
        }
    }
}