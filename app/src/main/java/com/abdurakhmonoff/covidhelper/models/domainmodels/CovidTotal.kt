package com.abdurakhmonoff.covidhelper.models.domainmodels

data class CovidTotal(
    val cases:Int,
    val recovered:Int,
    val deaths:Int,
    val update: Long
)