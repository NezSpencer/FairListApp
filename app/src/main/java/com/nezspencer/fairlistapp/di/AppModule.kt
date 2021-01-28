package com.nezspencer.fairlistapp.di

import android.app.Application
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.nezspencer.fairlistapp.BuildConfig
import com.nezspencer.fairlistapp.datasource.LocalDataSource
import com.nezspencer.fairlistapp.datasource.LocalDataSourceImpl
import com.nezspencer.fairlistapp.datasource.RemoteDataSource
import com.nezspencer.fairlistapp.datasource.RemoteDataSourceImpl
import com.nezspencer.fairlistapp.db.FairListDb
import com.nezspencer.fairlistapp.db.JobDao
import com.nezspencer.fairlistapp.network.JobApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDb(app: Application): FairListDb {
        return FairListDb.create(app)
    }

    @Provides
    @Singleton
    fun provideJobDao(database: FairListDb): JobDao {
        return database.jobDao()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): JobApi = retrofit.create(
        JobApi::class.java
    )

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: JobApi): RemoteDataSource {
        return RemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(jobDao: JobDao): LocalDataSource {
        return LocalDataSourceImpl(jobDao)
    }
}