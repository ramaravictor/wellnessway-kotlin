package com.example.wellnessway.di



import com.example.wellnessway.common.Constants
import com.example.wellnessway.data.local.StepsDao
import com.example.wellnessway.data.remote.WellnessWayApi
import com.example.wellnessway.data.repository.StepCountRepositoryImpl
import com.example.wellnessway.data.repository.WellnessWayRepositoryImpl
import com.example.wellnessway.domain.repository.StepCountRepository
import com.example.wellnessway.domain.repository.WellnessWayRepository
import com.example.wellnessway.domain.use_case.GetStepCountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }


    @Provides
    @Singleton
    fun provideSoulSpaceApi(okHttpClient: OkHttpClient): WellnessWayApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WellnessWayApi::class.java)
    }


    @Provides
    @Singleton
    fun provideWellnessWayRepository(wellnessWayApi: WellnessWayApi): WellnessWayRepository {
        return WellnessWayRepositoryImpl(wellnessWayApi)
    }


    @Provides
    fun provideStepCountRepository(
        stepsDao: StepsDao
    ): StepCountRepositoryImpl {
        return StepCountRepositoryImpl(stepsDao)
    }

    @Provides
    fun provideGetStepCountUseCase(
        repository: StepCountRepository
    ): GetStepCountUseCase {
        return GetStepCountUseCase(repository)
    }
}