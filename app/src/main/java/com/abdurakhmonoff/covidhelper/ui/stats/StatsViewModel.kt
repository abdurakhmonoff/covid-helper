package com.abdurakhmonoff.covidhelper.ui.stats

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.abdurakhmonoff.covidhelper.data.CountriesRepository
import com.abdurakhmonoff.covidhelper.data.api.CovidApi
import com.abdurakhmonoff.covidhelper.data.api.LocationApi
import com.abdurakhmonoff.covidhelper.data.database.getInstanceCountry
import com.abdurakhmonoff.covidhelper.data.database.getInstanceCovidTotal
import com.abdurakhmonoff.covidhelper.models.datatransferobjects.CountryDTO
import kotlinx.coroutines.launch

enum class CurrentCountryStatus { DONE, LOADING, ERROR }

class StatsViewModel(app: Application) : AndroidViewModel(app) {

    private val databaseCountry = getInstanceCountry(app.applicationContext)
    private val databaseCovidTotal = getInstanceCovidTotal(app.applicationContext)
    private val repository = CountriesRepository(databaseCountry, databaseCovidTotal)

    private var _country = MutableLiveData<CountryDTO>()
    val country: LiveData<CountryDTO>
        get() = _country

    private var _status = MutableLiveData<CurrentCountryStatus>()
    val statusCurrent: LiveData<CurrentCountryStatus>
        get() = _status

    init {
        _status.value = CurrentCountryStatus.DONE
        viewModelScope.launch {
            repository.refreshCountries()
        }
    }

    fun getCountryName(lat: Double, lng: Double) {
        viewModelScope.launch {
            _status.value = CurrentCountryStatus.LOADING
            try {
                val countryCode = LocationApi.locationService.getCountryInfo(lat, lng)
                _country.value = CovidApi.retrofitService.getCountry(countryCode.countryCode)
                _status.value = CurrentCountryStatus.DONE
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "getCountryName: ", e)
                _status.value = CurrentCountryStatus.ERROR
            }
        }
    }

    val countries = repository.allCountries
}

class StatsViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            return StatsViewModel(app) as T
        }
        throw Exception("Cannot find view model class")
    }
}

private const val TAG = "StatsViewModel"