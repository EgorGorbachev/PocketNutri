package com.example.pocketnutri.ui.di

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.pocketnutri.data.repository.ApiRepositoryImpl
import com.example.pocketnutri.data.repository.PrefsRepositoryImpl
import com.example.pocketnutri.data.source.local.data_sources.PrefsDataSource
import com.example.pocketnutri.data.source.rest.api.SpoonacularApi
import com.example.pocketnutri.data.source.rest.data_sources.ApiDataSources
import com.example.pocketnutri.domain.repository.ApiRepository
import com.example.pocketnutri.domain.repository.PrefsRepository
import com.example.pocketnutri.domain.usecase.ApiUseCase
import com.example.pocketnutri.domain.usecase.PrefsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePrefsDataSource(app:Application) =
        PrefsDataSource(app)

    @Singleton
    @Provides
    fun providePrefsRepository(prefsDataSource: PrefsDataSource):PrefsRepository =
        PrefsRepositoryImpl(prefsDataSource)

    @Singleton
    @Provides
    fun providesPrefsUseCase(prefsRepository:PrefsRepository) =
        PrefsUseCase(prefsRepository)

    @Singleton
    @Provides
    fun provideApiDataSource(api: SpoonacularApi) =
        ApiDataSources(api)

    @Singleton
    @Provides
    fun provideApiRepository(apiDataSources: ApiDataSources):ApiRepository =
        ApiRepositoryImpl(apiDataSources)

    @Singleton
    @Provides
    fun provideApiUseCase(apiRepository: ApiRepository) =
        ApiUseCase(apiRepository)

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOkkHttpClient(@ApplicationContext context:Context): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().setLenient().create()


    @Provides
    @Singleton
    fun getService(retrofit: Retrofit): SpoonacularApi = retrofit.create(SpoonacularApi::class.java)
}