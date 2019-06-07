package com.example.travelbudget.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cost_table")

data class Cost(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "costID") var costID: Long,
    @ColumnInfo(name = "date") var date: Long,
    @ColumnInfo(name = "sum")  var sum: Int
)
