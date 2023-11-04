package com.example.numberoneproject.presentation.di

import com.example.numberoneproject.BuildConfig
import com.example.numberoneproject.data.network.ApiResultCallAdapterFactory
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.data.repositoryimpl.GetShelterRepositoryImpl
import com.example.numberoneproject.data.repositoryimpl.LoginRepositoryImpl
import com.example.numberoneproject.data.repositoryimpl.ShelterRepositoryImpl
import com.example.numberoneproject.domain.repository.GetShelterRepository
import com.example.numberoneproject.domain.repository.LoginRepository
import com.example.numberoneproject.domain.repository.ShelterRepository
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Singleton
    @Binds
    abstract fun bindShelterRepository(
        shelterRepositoryImpl: ShelterRepositoryImpl
    ): ShelterRepository

    //shelter url
    @Singleton
    @Binds
    abstract fun bindGetShelterRepository(
        getShelterRepositoryImpl: GetShelterRepositoryImpl
    ): GetShelterRepository

}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}