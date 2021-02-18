package com.abdurakhmonoff.covidhelper.models.databaseentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdurakhmonoff.covidhelper.models.domainmodels.Country
import com.abdurakhmonoff.covidhelper.models.domainmodels.CovidTotal

@Entity
data class CountryDb(
    @PrimaryKey
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

@Entity
data class CovidTotalDb(
    @PrimaryKey
    val id: Long = 539845L,
    val cases:Int,
    val recovered:Int,
    val death:Int,
    val update: Long
)

fun List<CountryDb>.asDomainModel(): List<Country> {
    return map {
        Country(
            it.country,
            it.cases,
            it.recovered,
            it.deaths,
            it.imgUrl,
            it.todayCases,
            it.todayDeaths,
            it.todayRecovered,
            it.updated,
            it.population,
            it.tests,
            it.active
        )
    }
}

fun CovidTotalDb.asDomainModel():CovidTotal{
    return CovidTotal(this.cases,this.recovered,this.death,this.update)
}