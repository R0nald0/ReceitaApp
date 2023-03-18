package com.example.receitas.di.modulos

import com.example.receitas.data.local.database.RealmHelper
import com.example.receitas.data.local.database.RealmeDb
import com.example.receitas.data.repository.ReceitaRepository
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.data.remote.retrofiApi.RetrofitGetApi
import com.example.receitas.data.remote.retrofiApi.TheMealApi
import com.example.receitas.data.service.ReceitaBannerService
import com.example.receitas.data.service.ReceitaServiceApi
import com.example.receitas.data.service.interf.IServiceApi
import com.example.receitas.data.service.interf.IServiceReceitaDb
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.useCase.ReceitaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.realm.kotlin.Realm

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides()
    fun provideUseCase(repository: IRepository): IReceitaUseCase {
        return  ReceitaUseCase(repository)
    }

    @Provides()
    fun provideRepositoryReceita(
        serviceReceita:IServiceReceitaDb,
        receitaServiceApi: IServiceApi,
        receitaBannerService: ReceitaBannerService
        ):IRepository{
          return ReceitaRepository(serviceReceita,receitaServiceApi,receitaBannerService)
    }

    @Provides()
     fun provideService(realm : Realm):IServiceReceitaDb{
         return  RealmHelper(realm)
     }
    @Provides()
    fun provideRealmDb(): Realm {
        return RealmeDb().getRealm()
    }

    @Provides()
    fun provideServiceApi(serviceRetrofit: TheMealApi):ReceitaServiceApi{
        return  ReceitaServiceApi(serviceRetrofit)
    }

    @Provides
    fun provideReceitaServiceBanner():ReceitaBannerService{
        return ReceitaBannerService()
    }

    @Provides
    fun providesReceitaServiceApi(theMealApi: TheMealApi):IServiceApi{
          return  ReceitaServiceApi(theMealApi)
    }
    @Provides()
    fun provideRetrofiApi(): TheMealApi {
        return RetrofitGetApi.retrofitCreate(TheMealApi::class.java)
    }

}