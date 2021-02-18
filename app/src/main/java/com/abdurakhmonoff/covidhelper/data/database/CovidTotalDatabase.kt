package com.abdurakhmonoff.covidhelper.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.abdurakhmonoff.covidhelper.models.databaseentities.CovidTotalDb

@Dao
interface CovidTotalDao {
    @Query("SELECT * FROM CovidTotalDb")
    fun getCovidTotal(): LiveData<CovidTotalDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCovidTotal(covidTotalDb: CovidTotalDb)
}

@Database(entities = [CovidTotalDb::class], version = 1)
abstract class CovidTotalDatabase : RoomDatabase() {
    abstract val covidTotalDao: CovidTotalDao
}

private lateinit var INSTANCE: CovidTotalDatabase

fun getInstanceCovidTotal(context: Context):CovidTotalDatabase {
    synchronized(CovidTotalDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CovidTotalDatabase::class.java,
                "covidTotal"
            )
                .build()
        }
        return INSTANCE
    }
}