package com.daepiro.numberoneproject.presentation.di

import android.content.Context
import com.daepiro.numberoneproject.BuildConfig
import com.daepiro.numberoneproject.data.network.ApiResultCallAdapterFactory
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.data.repositoryimpl.DisasterRepositoryImpl
import com.daepiro.numberoneproject.data.repositoryimpl.FamilyRepositoryImpl
import com.daepiro.numberoneproject.data.repositoryimpl.FundingRepositoryImpl
import com.daepiro.numberoneproject.data.repositoryimpl.GetShelterRepositoryImpl
import com.daepiro.numberoneproject.data.repositoryimpl.LoginRepositoryImpl
import com.daepiro.numberoneproject.data.repositoryimpl.OnBoardingRepositoryImpl
import com.daepiro.numberoneproject.data.repositoryimpl.ShelterRepositoryImpl
import com.daepiro.numberoneproject.domain.repository.DisasterRepository
import com.daepiro.numberoneproject.domain.repository.FamilyRepository
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import com.daepiro.numberoneproject.domain.repository.GetShelterRepository
import com.daepiro.numberoneproject.domain.repository.LoginRepository
import com.daepiro.numberoneproject.domain.repository.OnBoardingRepository
import com.daepiro.numberoneproject.domain.repository.ShelterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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

    @Singleton
    @Binds
    abstract fun bindDisasterRepository(
        disasterRepositoryImpl: DisasterRepositoryImpl
    ): DisasterRepository
  
    //shelter url
    @Singleton
    @Binds
    abstract fun bindGetShelterRepository(
        getShelterRepositoryImpl: GetShelterRepositoryImpl
    ): GetShelterRepository

    @Singleton
    @Binds
    abstract fun bindFundingRepository(
        fundingRepositoryImpl: FundingRepositoryImpl
    ): FundingRepository

    @Singleton
    @Binds
    abstract fun bindFamilyRepository(
        familyRepositoryImpl: FamilyRepositoryImpl
    ): FamilyRepository

    @Singleton
    @Binds
    abstract fun bindOnBoardingRepository(
        onBoardingRepositoryImpl: OnBoardingRepositoryImpl
    ):OnBoardingRepository

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

    @Singleton
    @Provides
    fun provideFilesDir(@ApplicationContext context: Context): File {
        return context.filesDir
    }

}