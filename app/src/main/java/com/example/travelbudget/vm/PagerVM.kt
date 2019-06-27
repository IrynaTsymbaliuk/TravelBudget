package com.example.travelbudget.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.travelbudget.room.AppRepository

class PagerVM : ViewModel() {

    private var tripIDs: LiveData<List<Long>>

    init {
        val appRepository = AppRepository.getInstance(application = Application())
        tripIDs = appRepository.getListOfTripIDs()
    }

    fun getTripIDs(): LiveData<List<Long>> {
        return tripIDs
    }

}

