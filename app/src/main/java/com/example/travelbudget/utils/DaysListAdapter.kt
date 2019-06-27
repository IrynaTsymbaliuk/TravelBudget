package com.example.travelbudget.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbudget.R
import com.example.travelbudget.room.Day
import kotlinx.android.synthetic.main.day_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class DaysListAdapter(private val listOfDays: List<Day>) : RecyclerView.Adapter<DaysViewHolder>(),
    View.OnClickListener {
    override fun onClick(v: View?) {
        if (dailyCosts.visibility == View.GONE) {
            dailyCosts.visibility = View.VISIBLE
        } else { dailyCosts.visibility = View.GONE }
    }

    private lateinit var dailyCosts: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.day_item, parent, false)
        row.day_item.setOnClickListener(this)
        dailyCosts = row.findViewById<TextView>(R.id.daily_costs)
        return DaysViewHolder(row)
    }

    override fun getItemCount(): Int {
        return listOfDays.size
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        if (listOfDays.isNotEmpty()) {
            val sdf = SimpleDateFormat("MMM, d", Locale.ENGLISH)
            val dayNumber = sdf.format(listOfDays[position].date)

            var sumOfCosts: Int = 0
            listOfDays[position].dailyCosts!!.forEach {
                sumOfCosts += it.sum
            }
            val dailyCosts = sumOfCosts.toString()

            if (sumOfCosts == 0) {
                holder.itemView.visibility = View.GONE
            } else {
                holder.dayOfWeekAndMonthView.text = dayNumber
                holder.dailyBalanceView.text = dailyCosts
            }
        } else {
            holder.dayOfWeekAndMonthView.text = "no data"
            holder.dailyBalanceView.text = "no data"
            holder.dailyCostsView.text = "no data"
        }
    }

}

class DaysViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val dayOfWeekAndMonthView: TextView = view.day_of_weeek_and_month
    val dailyBalanceView: TextView = view.daily_balance
    val dailyCostsView: TextView = view.daily_costs

}
