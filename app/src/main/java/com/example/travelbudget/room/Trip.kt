package com.example.travelbudget.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_table")


data class Trip(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "tripID") val tripID: Long = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "currency") var currency: String,
    @ColumnInfo(name = "income") var income: Int,
    @ColumnInfo(name = "percentOfSaving") var percentOfSaving: Int,
    @ColumnInfo(name = "dateFrom") var dateFrom: Long,
    @ColumnInfo(name = "dateTo") var dateTo: Long

)