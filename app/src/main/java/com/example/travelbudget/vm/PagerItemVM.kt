package com.example.travelbudget.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.travelbudget.room.AppRepository
import com.example.travelbudget.room.Cost
import com.example.travelbudget.room.Day
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class PagerItemVM : ViewModel() {

    private val appRepository: AppRepository = AppRepository.getInstance(application = Application())

    fun getTripName(pagerItem: Long): LiveData<String> {
        return appRepository.getTripName(pagerItem)
    }

    fun getPercentageOfSavings(pagerItem: Long): LiveData<Int> {
        return appRepository.getPercentageOfSavings(pagerItem)
    }

    fun getIncome(pagerItem: Long): LiveData<Int> {
        return appRepository.getIncome(pagerItem)
    }

    fun getTripDates(pagerItem: Long): LiveData<String> {

        val dateFromLiveData = appRepository.getTripDateFrom(pagerItem)
        val dateToLiveData = appRepository.getTripDateTo(pagerItem)

        val result = MediatorLiveData<String>()

        result.addSource(dateFromLiveData) {
            result.value = combineForDates(dateFromLiveData, dateToLiveData)
        }
        result.addSource(dateToLiveData) {
            result.value = combineForDates(dateFromLiveData, dateToLiveData)
        }

        return result
    }

    private fun combineForDates(
        dateFromLiveData: LiveData<Long>,
        dateToLiveData: LiveData<Long>
    ): String {
        val dateFrom = dateFromLiveData.value
        val dateTo = dateToLiveData.value
        val sdf = SimpleDateFormat("MMM, d", Locale.ENGLISH)
        return if (dateFrom != null && dateTo != null) {
            (sdf.format(dateFrom) + " - " + sdf.format(dateTo))
        } else {
            "no data"
        }
    }

    fun getDailyBudgetSum(pagerItem: Long): LiveData<String> {

        val dateFromLiveData = appRepository.getTripDateFrom(pagerItem)
        val dateToLiveData = appRepository.getTripDateTo(pagerItem)
        val incomeLiveData = appRepository.getIncome(pagerItem)

        val result = MediatorLiveData<String>()

        result.addSource(dateFromLiveData) {
            result.value = combineForDailyBudgetSum(dateFromLiveData, dateToLiveData, incomeLiveData)
        }
        result.addSource(dateToLiveData) {
            result.value = combineForDailyBudgetSum(dateFromLiveData, dateToLiveData, incomeLiveData)
        }
        result.addSource(incomeLiveData) {
            result.value = combineForDailyBudgetSum(dateFromLiveData, dateToLiveData, incomeLiveData)
        }

        return result
    }

    private fun combineForDailyBudgetSum(
        dateFromLiveData: LiveData<Long>,
        dateToLiveData: LiveData<Long>,
        incomeLiveData: LiveData<Int>
    ): String {
        val dateFrom = dateFromLiveData.value
        val dateTo = dateToLiveData.value
        val income = incomeLiveData.value
        return if (dateFrom != null && dateTo != null && income != null) {
            val tripDuration = TimeUnit.MILLISECONDS.toDays(dateTo - dateFrom)
            val dailyBudgetSum: Int = (income / tripDuration).toInt()
            dailyBudgetSum.toString()
        } else {
            "no data"
        }
    }

    fun getSavingSum(pagerItem: Long): LiveData<String> {

        val incomeLiveData = appRepository.getIncome(pagerItem)
        val percentageOfSavingsLiveData = appRepository.getPercentageOfSavings(pagerItem)

        val result = MediatorLiveData<String>()

        result.addSource(incomeLiveData) {
            result.value = combineForSavingSum(incomeLiveData, percentageOfSavingsLiveData)
        }
        result.addSource(percentageOfSavingsLiveData) {
            result.value = combineForSavingSum(incomeLiveData, percentageOfSavingsLiveData)
        }

        return result
    }

    private fun combineForSavingSum(
        incomeLiveData: LiveData<Int>,
        percentageOfSavingsLiveData: LiveData<Int>
    ): String {
        val income = incomeLiveData.value
        val percentageOfSavings = percentageOfSavingsLiveData.value
        return if (income != null && percentageOfSavings != null) {
            (income - income * percentageOfSavings).toString()
        } else {
            "no data"
        }
    }

    fun getMandatoryExpensesSum(pagerItem: Long): String {
        return "no data"
    }

    fun getEverydayExpensesSum(pagerItem: Long): String {
        return "no data"
    }

    fun getDaysOfTrip(pagerItem: Long): LiveData<List<Day>> {

        val dateFromLiveData = appRepository.getTripDateFrom(pagerItem)
        val dateToLiveData = appRepository.getTripDateTo(pagerItem)
        val allCostsLiveData = appRepository.getAllCosts()

        val result = MediatorLiveData<List<Day>>()

        result.addSource(dateFromLiveData) {
            result.value = combineForCostsOfDay(dateFromLiveData, dateToLiveData, allCostsLiveData)
        }
        result.addSource(dateToLiveData) {
            result.value = combineForCostsOfDay(dateFromLiveData, dateToLiveData, allCostsLiveData)
        }

        result.addSource(allCostsLiveData) {
            result.value = combineForCostsOfDay(dateFromLiveData, dateToLiveData, allCostsLiveData)
        }

        return result
    }

    private fun combineForCostsOfDay(
        dateFromLiveData: LiveData<Long>,
        dateToLiveData: LiveData<Long>,
        allCostsLiveData: LiveData<List<Cost>>
    ): List<Day> {
        var dateFrom = dateFromLiveData.value
        val dateTo = dateToLiveData.value
        val allCosts = allCostsLiveData.value

        val listOfDays = ArrayList<Day>()

        if (dateFrom != null && dateTo != null && allCosts != null) {

            while (dateFrom <= dateTo) {
                val listOfCostsOfDay = ArrayList<Cost>()
                allCosts.forEach {

                    if (TimeUnit.MILLISECONDS.toDays(it.date) == TimeUnit.MILLISECONDS.toDays(dateFrom)) {
                        listOfCostsOfDay.add(it)
                    }
                }
                listOfDays.add(Day(dateFrom, listOfCostsOfDay))
                dateFrom += TimeUnit.DAYS.toMillis(1)
            }

        }
        return listOfDays
    }

}