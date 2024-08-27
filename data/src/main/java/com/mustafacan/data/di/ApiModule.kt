package com.mustafacan.data.di

import android.content.Context
import com.mustafacan.data.BuildConfig
import com.mustafacan.data.remote.api.BirdsServices
import com.mustafacan.data.remote.api.CatsServices
import com.mustafacan.data.remote.api.DogsServices
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val CLIENT_TIME_OUT = 60L

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {


        //val sslPinning: SSLPinning = SSLPinningImpl()
        //sslPinning.initSSLWithCertificate(context,R.raw.)

        return OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            //.sslSocketFactory(sslPinning.getSSLSocketFactory(),sslPinning.getTrustManager())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideCatsService(retrofit: Retrofit): CatsServices {
        return retrofit.create(CatsServices::class.java)
    }

    @Provides
    @Singleton
    fun provideDogsService(retrofit: Retrofit): DogsServices {
        return retrofit.create(DogsServices::class.java)
    }

    @Provides
    @Singleton
    fun provideBirdsService(retrofit: Retrofit): BirdsServices {
        return retrofit.create(BirdsServices::class.java)
    }

}