package com.example.travelbudget.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDao {

    // --- Cost --- //
    @Insert
    fun setCost(cost: Cost)

    @Query("SELECT * FROM cost_table")
    fun getAllCosts(): LiveData<List<Cost>>

    @Query("UPDATE cost_table SET costID = :costID")
    fun setCostID(costID: Int)

    @Query("SELECT costID FROM cost_table")
    fun getCostID(): Int

    @Query("UPDATE cost_table SET date = :costDate")
    fun setCostDate(costDate: Long)

    @Query("SELECT date FROM cost_table")
    fun getCostDate(): Long

    @Query("UPDATE cost_table SET sum = :costSum")
    fun setCostSum(costSum: Int)

    @Query("SELECT sum FROM cost_table")
    fun getCostSum(): Int

    // --- Trip --- //
    @Insert
    fun setTrip(trip: Trip)

    @Query("SELECT * FROM trip_table WHERE dateFrom >= :date OR dateTo <= :date")
    fun getTripWithDate(date: Long): LiveData<Trip>

    @Query("SELECT * FROM trip_table")
    fun getAllTrips(): LiveData<List<Trip>>

    @Query("SELECT * FROM trip_table WHERE name = :name")
    fun getTripWithName(name: String): LiveData<Trip>

    @Query("SELECT * FROM trip_table WHERE dateFrom >= :dateFrom AND dateTo <= :dateTo")
    fun getTripBetweenDates(dateFrom: Long, dateTo: Long): LiveData<Trip>

    @Query("SELECT * FROM trip_table WHERE dateFrom <= :dateTo")
    fun getTripWithDateFromLessThan(dateTo: Long): LiveData<Trip>

    @Query("SELECT * FROM trip_table WHERE dateTo >= :dateFrom")
    fun getTripWithDateToMoreThan(dateFrom: Long): LiveData<Trip>

}