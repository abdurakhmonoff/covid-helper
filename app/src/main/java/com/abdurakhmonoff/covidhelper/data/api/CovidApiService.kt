package com.abdurakhmonoff.covidhelper.data.api

import com.abdurakhmonoff.covidhelper.models.datatransferobjects.CountryDTO
import com.abdurakhmonoff.covidhelper.models.datatransferobjects.CovidTotalDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://disease.sh/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CovidApiService {
    @GET("v3/covid-19/countries")
    suspend fun getAllCountries(@Query("sort") sortBy: String): List<CountryDTO>

    @GET("v3/covid-19/all")
    suspend fun getTotalStats(): CovidTotalDTO

    @GET("v3/covid-19/countries/{countryCode}")
    suspend fun getCountry(@Path("countryCode") countryCode:String, @Query("allowNull") allowNull:Boolean = false):CountryDTO
}

object CovidApi {
    val retrofitService: CovidApiService by lazy { retrofit.create(CovidApiService::class.java) }
}