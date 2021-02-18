package com.abdurakhmonoff.covidhelper.models.datatransferobjects

import com.abdurakhmonoff.covidhelper.models.databaseentities.CovidTotalDb

data class CovidTotalDTO(
    val active: Int,
    val activePerOneMillion: Double,
    val affectedCountries: Int,
    val cases: Int,
    val casesPerOneMillion: Int,
    val critical: Int,
    val criticalPerOneMillion: Double,
    val deaths: Int,
    val deathsPerOneMillion: Double,
    val oneCasePerPeople: Int,
    val oneDeathPerPeople: Int,
    val oneTestPerPeople: Int,
    val population: Long,
    val recovered: Int,
    val recoveredPerOneMillion: Double,
    val tests: Int,
    val testsPerOneMillion: Double,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long
)

fun CovidTotalDTO.asDbModel(): CovidTotalDb {
    return CovidTotalDb(
        cases = this.cases,
        recovered = this.recovered,
        death = this.deaths,
        update = this.updated
    )
}