package com.example.travelbudget.room

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask


class AppRepository(application: Application) {

    private val appDao: AppDao
    private val allTrips: LiveData<List<Trip>>
    private val allCosts: LiveData<List<Cost>>

    companion object {

        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: AppRepository(application).also { instance = it }
            }
    }

    init {
        val appDatabase = AppDatabase.getDatabase(application)
        appDao = appDatabase.appDao()
        allTrips = appDao.getAllTrips()
        allCosts = appDao.getAllCosts()
    }

    fun setCost(cost: Cost) {
        SetCostAsyncTask(appDao).execute(cost)
    }

    private class SetCostAsyncTask internal constructor(private val appDao: AppDao) :
        AsyncTask<Cost, Void, Void>() {

        override fun doInBackground(vararg costs: Cost): Void? {
            appDao.setCost(costs[0])
            return null
        }
    }

    fun setTrip(trip: Trip) {
        SetTripAsyncTask(appDao).execute(trip)
    }

    private class SetTripAsyncTask internal constructor(private val appDao: AppDao) :
        AsyncTask<Trip, Void, Void>() {

        override fun doInBackground(vararg trips: Trip): Void? {
            appDao.setTrip(trips[0])
            return null
        }
    }

    fun getTripName(pagerItem: Long): LiveData<String> {
        return appDao.getTripNameWithID(pagerItem)
    }

    fun getAllCosts(): LiveData<List<Cost>> {
        return allCosts
    }

    fun getCostsOfDay(date: Long): LiveData<List<Cost>> {
        return appDao.getCostsOfDay(date)
    }

    fun getCostsBetweenDates(dateFrom: Long, dateTo: Long): LiveData<List<Cost>> {
        return appDao.getCostsBetweenDates(dateFrom, dateTo)
    }

    fun getListOfTripIDs(): LiveData<List<Long>> {
        return appDao.getListOfTripIDs()
    }

    fun getTripDateFrom(pagerItem: Long): LiveData<Long> {
        return appDao.getTripDateFromWithID(pagerItem)
    }

    fun getTripDateTo(pagerItem: Long): LiveData<Long> {
        return appDao.getTripDateToWithID(pagerItem)
    }

    fun getIncome(pagerItem: Long): LiveData<Int> {
        return appDao.getTripIncomeWithID(pagerItem)
    }

    fun getPercentageOfSavings(pagerItem: Long): LiveData<Int> {
        return appDao.getTripPercentageOfSavingsWithID(pagerItem)
    }

    fun getCostsWithDate(date: Long): LiveData<List<Cost>> {
        return appDao.getCostsWithDate(date)
    }


    fun getTripWithID(tripID: Long): LiveData<Trip> {
        return appDao.getTripWithID(tripID)
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
