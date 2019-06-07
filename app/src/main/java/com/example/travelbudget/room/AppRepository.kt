package com.example.travelbudget.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class AppRepository(private val appDao: AppDao) {

    companion object {

        @Volatile private var instance: AppRepository? = null

        fun getInstance(appDao: AppDao) =
            instance ?: synchronized(this) {
                instance ?: AppRepository(appDao).also { instance = it }
            }
    }

    val allTrips: LiveData<List<Trip>> = appDao.getAllTrips()
    val allCosts: LiveData<List<Cost>> = appDao.getAllCosts()

    @WorkerThread
    fun setTrip(trip: Trip) {
        appDao.setTrip(trip)
    }

    @WorkerThread
    fun setCost(cost: Cost) {
        appDao.setCost(cost)
    }

    fun getTripWithDate(date: Long): LiveData<Trip> {
        return appDao.getTripWithDate(date)
    }

    fun getTripWithName(name: String): LiveData<Trip> {
        return appDao.getTripWithName(name)
    }

    fun getTripBetweenDates(dateFrom: Long, dateTo: Long): LiveData<Trip> {
        return appDao.getTripBetweenDates(dateFrom, dateTo)
    }

    fun getTripWithDateFromLessThan(dateTo: Long): LiveData<Trip> {
        return appDao.getTripWithDateFromLessThan(dateTo)

    }

    fun getTripWithDateToMoreThan(dateFrom: Long): LiveData<Trip> {
        return appDao.getTripWithDateToMoreThan(dateFrom)
    }

}