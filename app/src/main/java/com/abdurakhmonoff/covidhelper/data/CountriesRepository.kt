package com.abdurakhmonoff.covidhelper.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.abdurakhmonoff.covidhelper.data.api.CovidApi
import com.abdurakhmonoff.covidhelper.data.database.CountryDatabase
import com.abdurakhmonoff.covidhelper.data.database.CovidTotalDatabase
import com.abdurakhmonoff.covidhelper.models.databaseentities.asDomainModel
import com.abdurakhmonoff.covidhelper.models.datatransferobjects.asCountryDb
import com.abdurakhmonoff.covidhelper.models.datatransferobjects.asDbModel
import com.abdurakhmonoff.covidhelper.models.domainmodels.Country
import com.abdurakhmonoff.covidhelper.models.domainmodels.CovidTotal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountriesRepository(
    private val databaseCountry: CountryDatabase,
    private val databaseCovidTotal: CovidTotalDatabase
) {

    private val TAG = "CountriesRepository"

    val allCountries: LiveData<List<Country>> =
        Transformations.map(databaseCountry.countryDao.getAllCountries()) {
            it?.asDomainModel()
        }

    val covidTotal: LiveData<CovidTotal> =
        Transformations.map(databaseCovidTotal.covidTotalDao.getCovidTotal()) {
            it?.asDomainModel()
        }

    suspend fun refreshCountries() {
        withContext(Dispatchers.IO) {
            try {
                val countriesFromNetwork = CovidApi.retrofitService.getAllCountries("cases")
                databaseCountry.countryDao.insertAllCountries(*countriesFromNetwork.asCountryDb())
                val covidTotal = CovidApi.retrofitService.getTotalStats()
                databaseCovidTotal.covidTotalDao.insertCovidTotal(covidTotal.asDbModel())
            } catch (e: Exception) {
                Log.e(TAG, "refreshCountries: ${e.message}")
            }
        }
    }

}