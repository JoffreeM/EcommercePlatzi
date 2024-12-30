package com.jop.marketjp.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jop.webs.ApiConstance
import com.jop.webs.ApiService
import com.jop.webs.Token
import com.jop.webs.serializers.BooleanDeserializer
import com.jop.webs.serializers.BooleanSerializer
import com.jop.webs.serializers.DateDeserializaer
import com.jop.webs.serializers.DateSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    fun provideRetrofit(): ApiService {
        Token.retrofitInstance = Retrofit.Builder()
            .baseUrl(ApiConstance.getServerPath())
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .client(provideHttpClient())
            .build()
            .create(ApiService::class.java)

        return Token.retrofitInstance!!
    }

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor()) // Logs
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val refererUrl = originalRequest.url.toString()
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + Token.token)
                    .addHeader("Referer", refererUrl)
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun gson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, BooleanSerializer())
            .registerTypeAdapter(Boolean::class.java, BooleanDeserializer())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanSerializer())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanDeserializer())
            .registerTypeAdapter(Date::class.java, DateSerializer())
            .registerTypeAdapter(Date::class.java, DateDeserializaer())
            .serializeNulls()
            .create()
    }
}
