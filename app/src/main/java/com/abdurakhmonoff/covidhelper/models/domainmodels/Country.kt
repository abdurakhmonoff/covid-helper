package com.abdurakhmonoff.covidhelper.models.domainmodels

data class Country(
    val country: String,
    val cases: Int,
    val recovered: Int,
    val deaths: Int,
    val imgUrl: String,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long,
    val population: Int,
    val tests: Int,
    val active: Int
)