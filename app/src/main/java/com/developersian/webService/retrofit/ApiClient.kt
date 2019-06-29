package com.developersian.webService.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
	private val okHttpBuilder = OkHttpClient.Builder()
	
	private val client: Retrofit
		get() {
			okHttpBuilder.addInterceptor { chain ->
				val newRequest = chain.request().newBuilder().header("Accept", "application/json").header("Authorization", "Bearer ")
				chain.proceed(newRequest.build())
			}
			return Retrofit.Builder().baseUrl("http://185.15.247.156:5005/api/").client(okHttpBuilder.readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.DAYS).build()).addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd hh:mm:ss").create())).build()
		}
	
	fun getApiService(): ApiService = ApiClient.client.create(ApiService::class.java)
	
}