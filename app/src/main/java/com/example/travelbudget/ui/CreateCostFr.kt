package com.example.travelbudget.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.travelbudget.R
import com.example.travelbudget.vm.CreateCostVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_cost.*
import kotlinx.android.synthetic.main.create_cost.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.DatePicker

class CreateCostFr : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.save_cost_fab -> {
                createCostVM.setCost()
            }
            R.id.date_cost -> {
                showDatePickerDialog()
            }
        }
    }

    private lateinit var createCostVM: CreateCostVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View = inflater.inflate(R.layout.create_cost, container, false)

        activity!!.toolbar.title = "Add cost"
        activity!!.toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)

        val formatter = SimpleDateFormat("EEE, dd 'of' MMM", Locale.ENGLISH)

        createCostVM = ViewModelProviders.of(this).get(CreateCostVM::class.java)

        rootView.date_cost.text = formatter.format(createCostVM.getCostDate())

        rootView.date_cost.setOnClickListener(this)

        rootView.sum_cost.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                createCostVM.setCostSum(editable.toString().toInt())
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        rootView.save_cost_fab.setOnClickListener(this)

        return rootView
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            activity!!, this, year, month, dayOfMonth
        )
        datePickerDialog.show()
    }


    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val formatter = SimpleDateFormat("EEE, dd 'of' MMM", Locale.ENGLISH)
        date_cost.text = formatter.format(calendar.timeInMillis)
        createCostVM.setCostDate(calendar.timeInMillis)
    }

}
