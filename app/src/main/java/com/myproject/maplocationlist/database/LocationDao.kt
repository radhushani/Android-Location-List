package com.myproject.maplocationlist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myproject.maplocationlist.model.Location

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun getAll(): List<Location>

    @Insert
    fun insert(vararg location: Location)

    @Query("SELECT * FROM location WHERE name = :locationName")
    fun getLocationByName(locationName: String): Location
}