package com.example.travelbudget.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.travelbudget.room.AppRepository
import com.example.travelbudget.room.Cost
import java.util.*

class CreateCostVM : ViewModel() {

    private var costDate: Long = Calendar.getInstance().timeInMillis
    private var costSum: Int = 0
    private var appRepository: AppRepository = AppRepository.getInstance(application = Application())

    fun setCostSum(sum: Int) {
        this.costSum = sum
    }

    fun setCost() {
        appRepository.setCost(Cost(0, costDate, costSum))
    }

    fun getCostSum(): Int {
        return costSum
    }

    fun getCostDate(): Long {
        return costDate
    }

    fun setCostDate(date: Long) {
        this.costDate = date
    }

}
