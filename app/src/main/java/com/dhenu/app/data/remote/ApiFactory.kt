package com.dhenu.app.data.remote

import com.dhenu.app.BuildConfig
import com.dhenu.app.data.local.AppPreference
import com.dhenu.app.data.local.PreferenceKeys
import com.dhenu.app.util.AppConstants
import com.dhenu.app.util.Logger
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object ApiFactory {

    private var retrofitWithHeader: Retrofit? = null
    private var retrofit: Retrofit? = null
    private var retrofitGoogleApi: Retrofit? = null

    val clientWithHeader: Retrofit
        get() {
            Logger.e(
                AppConstants.LOG_CAT,
                "Response of Application are Language " + AppPreference.getValue(
                    PreferenceKeys.ACCESS_TOKEN
                )
            )

            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .addHeader(
                        "UserAuthToken",
                        AppPreference.getValue(PreferenceKeys.ACCESS_TOKEN)!!
                    )
                    .addHeader("timezone", TimeZone.getDefault().id)
                    .addHeader("device-version", BuildConfig.VERSION_NAME)

                val request = requestBuilder.build()
                chain.proceed(request)

            }
            httpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
            httpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
            httpClientBuilder.writeTimeout(5, TimeUnit.MINUTES)
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)

            if (retrofitWithHeader == null) {
                val gson = GsonBuilder().setLenient().create()
                retrofitWithHeader = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofitWithHeader!!
        }


    fun getClientWithoutHeader(): Retrofit {

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor { chain ->

            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .addHeader("timezone", TimeZone.getDefault().id)
                //.addHeader("device-version", BuildConfig.VERSION_NAME)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        httpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.writeTimeout(5, TimeUnit.MINUTES)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(loggingInterceptor)

        if (retrofit == null) {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun getGoogleApiClient(): Retrofit {

        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.addInterceptor { chain ->

            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Accept", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        httpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.writeTimeout(5, TimeUnit.MINUTES)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(loggingInterceptor)

        if (retrofitGoogleApi == null) {
            val gson = GsonBuilder().setLenient().create()
            retrofitGoogleApi = Retrofit.Builder()
                .baseUrl(BuildConfig.GOOGLE_API_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofitGoogleApi!!
    }
}