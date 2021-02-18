package com.abdurakhmonoff.covidhelper.models.datatransferobjects

import com.abdurakhmonoff.covidhelper.models.databaseentities.CountryDb

data class CountryDTO(
    val active: Int,
    val activePerOneMillion: Double,
    val cases: Int,
    val casesPerOneMillion: Double,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Int,
    val criticalPerOneMillion: Double,
    val deaths: Int,
    val deathsPerOneMillion: Double,
    val oneCasePerPeople: Double,
    val oneDeathPerPeople: Double,
    val oneTestPerPeople: Double,
    val population: Int,
    val recovered: Int,
    val recoveredPerOneMillion: Double,
    val tests: Int,
    val testsPerOneMillion: Double,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long
)

fun List<CountryDTO>.asCountryDb(): Array<CountryDb> {
    return map {
        CountryDb(
            country = it.country,
            cases = it.cases,
            recovered = it.recovered,
            deaths = it.deaths,
            imgUrl = it.countryInfo.flag,
            todayCases = it.todayCases,
            todayDeaths = it.todayDeaths,
            todayRecovered = it.todayRecovered,
            updated = it.updated,
            population = it.population,
            tests = it.tests,
            active = it.active
        )
    }.toTypedArray()
}