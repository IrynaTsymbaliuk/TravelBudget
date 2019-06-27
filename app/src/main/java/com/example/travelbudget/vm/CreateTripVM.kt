package com.example.travelbudget.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.travelbudget.room.AppRepository
import com.example.travelbudget.room.Trip

class CreateTripVM : ViewModel() {

    private val appRepository = AppRepository.getInstance(application = Application())
    private lateinit var tripName: String
    private lateinit var tripCurrency: String
    private var tripIncome: Int = 0
    private var tripSum: Int = 0
    private var tripPercent: Int = 0
    private var tripDateFrom: Long = 0L
    private var tripDateTo: Long = 0L

    fun setTrip() {
        appRepository.setTrip(Trip(0, tripName, tripCurrency, tripIncome, tripPercent, tripDateFrom, tripDateTo))
    }

    fun setTripName(name: String) {
        this.tripName = name
    }

    fun setTripCurrency(currency: String) {
        this.tripCurrency = currency
    }

    fun setTripIncome(income: Int) {
        this.tripIncome = income
    }

    fun setTripPercent(percent: Int) {
        this.tripPercent = percent
    }

    fun setTripSum(sum: Int) {
        this.tripSum = sum
    }

    fun setTripDateFrom(dateFrom: Long) {
        this.tripDateFrom = dateFrom
    }

    fun setTripDateTo(dateTo: Long) {
        this.tripDateTo = dateTo
    }


}
/*public void setTrip() {
        repository.setTrip(new Trip(name, dateFrom.getValue(), dateTo.getValue(), income, sum));
    }*//*


    fun getTripWithName(name: String): LiveData<Trip> {
        return appRepository.getTripWithName(name)
    }

    fun getDateFromString(): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        return if (getDateFrom().value != null) {
            simpleDateFormat.format(getDateFrom().value)
        } else
            simpleDateFormat.format(Calendar.getInstance().timeInMillis)
    }

    fun getDateToString(): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        if (getDateTo().value != null) {
            return simpleDateFormat.format(getDateTo().value)
        } else {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, 1)
            return simpleDateFormat.format(calendar.timeInMillis)
        }
    }

    fun getAllTrips(): LiveData<List<Trip>> {
        return appRepository.allTrips
    }

    fun getTripWithDatesBetween(): LiveData<Trip> {
        return appRepository.getTripBetweenDates(getDateFrom().value!!, getDateTo().value!!)
    }

    fun getTripWithDateFromLessThan(): LiveData<Trip> {
        return appRepository.getTripWithDateFromLessThan(getDateTo().value!!)
    }

    fun getTripWithDateToMoreThan(): LiveData<Trip> {
        return appRepository.getTripWithDateToMoreThan(getDateFrom().value!!)
    }

}*/
