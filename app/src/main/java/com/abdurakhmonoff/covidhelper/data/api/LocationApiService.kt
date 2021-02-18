package com.abdurakhmonoff.covidhelper.data.api

import com.abdurakhmonoff.covidhelper.models.datatransferobjects.CountryCode
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL_LOCATION = "http://api.geonames.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_LOCATION)
    .build()

interface LocationApiService {
    @GET("countryCodeJSON")
    suspend fun getCountryInfo(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("username") username: String = "noname07075002"
    ):CountryCode
}

object LocationApi{
    val locationService:LocationApiService by lazy {
        retrofit.create(LocationApiService::class.java)
    }
}