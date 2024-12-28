package com.example.hiringcodetest.di

import com.example.hiringcodetest.data.repository.ItemRepositoryImpl
import com.example.hiringcodetest.data.source.remote.AuthInterceptor
import com.example.hiringcodetest.data.source.remote.ItemApi
import com.example.hiringcodetest.data.source.remote.RetrofitService
import com.example.hiringcodetest.domain.repository.ItemsRepository
import com.example.hiringcodetest.domain.usecase.GetItemListUseCase
import com.example.hiringcodetest.utils.WebConstants
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
object RemoteModule {

    @Provides
    fun injectInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun injectOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()
    }

    @Provides
    fun injectRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(WebConstants.hiringUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): RetrofitService = retrofit.create(RetrofitService::class.java)

    @Provides
    @Singleton
    fun provideItemRepository(api: ItemApi): ItemsRepository {
        return ItemRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetItemUseCase(repository: ItemsRepository): GetItemListUseCase {
        return GetItemListUseCase(repository)
    }
}