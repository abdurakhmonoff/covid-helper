package com.abdurakhmonoff.covidhelper.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.abdurakhmonoff.covidhelper.models.databaseentities.CountryDb

@Dao
interface CountryDao {
    @Query("SELECT * FROM CountryDb ORDER BY cases DESC")
    fun getAllCountries(): LiveData<List<CountryDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCountries(vararg countries: CountryDb)
}

@Database(entities = [CountryDb::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract val countryDao: CountryDao
}

private lateinit var INSTANCE: CountryDatabase

fun getInstanceCountry(context: Context): CountryDatabase {
    synchronized(CountryDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CountryDatabase::class.java,
                "countries"
            )
                .build()
        }
        return INSTANCE
    }
}