package com.example.travelbudget.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbudget.R
import com.example.travelbudget.room.Day
import com.example.travelbudget.utils.DaysListAdapter
import com.example.travelbudget.vm.PagerItemVM
import kotlinx.android.synthetic.main.pager_item.view.*
import kotlinx.android.synthetic.main.pager_item.view.trip_name

class PagerItemFr : Fragment(), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.drag_handle_icon -> {
                view!!.trip_settings.visibility = VISIBLE
            }
            R.id.cancel_trip_settings -> {
                view!!.trip_settings.visibility = GONE
            }
            R.id.save_trip_settings -> {
                view!!.trip_settings.visibility = GONE
            }
        }
    }

    private lateinit var pagerItemVM: PagerItemVM
    private var pagerItem = 0L

    companion object {

        @JvmStatic
        fun newInstance(pagerItem: Long) = PagerItemFr().apply {
            arguments = Bundle().apply {
                putLong("TripPositionInViewPager", pagerItem)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getLong("TripPositionInViewPager")?.let {
            pagerItem = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pager_item, container, false)

        view.drag_handle_icon.setOnClickListener(this)
        view.cancel_trip_settings.setOnClickListener(this)
        view.save_trip_settings.setOnClickListener(this)

        pagerItemVM = ViewModelProviders.of(this).get(PagerItemVM::class.java)

        getTripName().observe(this, Observer { name -> view.trip_name.text = name })
        getTripPercentageOfSavings().observe(
            this,
            Observer { percentage -> view.percentage_of_savings_input.setText(percentage.toString()) })
        getTripIncome().observe(this, Observer { income -> view.income_input.setText(income.toString()) })
        getTripDates().observe(this, Observer { dates -> view.trip_dates.text = dates })
        getSavingsSum().observe(this, Observer { savingsSum -> view.savings_sum.text = savingsSum })
        getDailyBudgetSum().observe(this, Observer { dailyBudgetSum -> view.daily_budget_sum.text = dailyBudgetSum })
        view.mandatory_expenses_sum.text = getMandatoryExpensesSum()
        view.everyday_expenses_sum.text = getEverydayExpensesSum()

        getDaysOfTrip().observe(this, Observer {
            view.rv_list_of_days.layoutManager = LinearLayoutManager(context)
            view.rv_list_of_days.adapter = DaysListAdapter(it)
        })

        return view

    }

    private fun getTripName(): LiveData<String> {
        return pagerItemVM.getTripName(pagerItem)
    }

    private fun getTripPercentageOfSavings(): LiveData<Int> {
        return pagerItemVM.getPercentageOfSavings(pagerItem)
    }

    private fun getTripIncome(): LiveData<Int> {
        return pagerItemVM.getIncome(pagerItem)
    }

    private fun getTripDates(): LiveData<String> {
        return pagerItemVM.getTripDates(pagerItem)
    }

    private fun getSavingsSum(): LiveData<String> {
        return pagerItemVM.getSavingSum(pagerItem)
    }

    private fun getDailyBudgetSum(): LiveData<String> {
        return pagerItemVM.getDailyBudgetSum(pagerItem)
    }

    private fun getMandatoryExpensesSum(): String {
        return pagerItemVM.getMandatoryExpensesSum(pagerItem)
    }

    private fun getEverydayExpensesSum(): String {
        return pagerItemVM.getEverydayExpensesSum(pagerItem)
    }

    private fun getDaysOfTrip(): LiveData<List<Day>> {
        return pagerItemVM.getDaysOfTrip(pagerItem)
    }

}