package com.abdurakhmonoff.covidhelper.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abdurakhmonoff.covidhelper.data.CountriesRepository
import com.abdurakhmonoff.covidhelper.data.database.getInstanceCountry
import com.abdurakhmonoff.covidhelper.data.database.getInstanceCovidTotal
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val databaseCountry = getInstanceCountry(app.applicationContext)
    private val databaseCovidTotal = getInstanceCovidTotal(app.applicationContext)
    private val repository = CountriesRepository(databaseCountry, databaseCovidTotal)

    init {
        viewModelScope.launch {
            repository.refreshCountries()
        }
    }

    val totalStats = repository.covidTotal

}

class MainViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(app) as T
        }
        throw Exception("Cannot find view model class")
    }
}