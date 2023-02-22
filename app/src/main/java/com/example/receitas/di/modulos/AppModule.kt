package com.example.receitas.di.modulos

import com.example.receitas.data.database.RealmHelper
import com.example.receitas.data.repository.ReceitaRepository
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.data.retrofiApi.RetrofitGetApi
import com.example.receitas.data.retrofiApi.TheMealApi
import com.example.receitas.data.service.ReceitaServiceApi
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
    fun provideRepositoryReceita(
        serviceReceita:IServiceReceita,
        receitaServiceApi: ReceitaServiceApi
        ):IRepository{
          return ReceitaRepository(serviceReceita,receitaServiceApi)
    }

    @Provides()
     fun provideService():IServiceReceita{
         return  RealmHelper()
     }

    @Provides()
    fun provideServiceApi(serviceRetrofit: TheMealApi):ReceitaServiceApi{
        return  ReceitaServiceApi(serviceRetrofit)
    }

    @Provides()
    fun provideRetrofiApi():TheMealApi{
        return RetrofitGetApi.retrofitCreate(TheMealApi::class.java)
    }

}