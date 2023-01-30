package com.example.receitas.di.modulos

import com.example.receitas.data.repository.ReceitaRepository
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.data.service.ReceitaService
import com.example.receitas.data.service.interf.IServiceReceita
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.useCase.ReceitaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides()
    fun provideUseCase(repository: IRepository): IReceitaUseCase {
        return  ReceitaUseCase(repository);
    }

    @Provides()
    fun provideRepositoryReceita(service:IServiceReceita):IRepository{
          return ReceitaRepository(service)
    }

    @Provides()
     fun provideService():IServiceReceita{
         return  ReceitaService()
     }
}