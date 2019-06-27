package com.example.travelbudget.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.travelbudget.R
import com.example.travelbudget.vm.CreateTripVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_trip_fr.*
import kotlinx.android.synthetic.main.create_trip_fr.view.*
import kotlinx.android.synthetic.main.create_trip_fr.view.date_from_et
import kotlinx.android.synthetic.main.create_trip_fr.view.date_to_et
import java.text.SimpleDateFormat
import java.util.*

class CreateTripFr : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.save_trip_material_button -> {
                createTripVM.setTrip()
            }
            R.id.date_from_et -> {
                showDatePickerDialog()
            }
            R.id.date_to_et -> {
                showDatePickerDialog()
            }
        }
    }

    private lateinit var createTripVM: CreateTripVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.create_trip_fr, container, false)

        activity!!.toolbar.title = "Add trip"
        activity!!.toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)

        createTripVM = ViewModelProviders.of(this).get(CreateTripVM::class.java)

        rootView.save_trip_material_button.setOnClickListener(this)

        rootView.name_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (editable != null) {
                    createTripVM.setTripName(editable.toString())
                }
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        rootView.date_from_et.setOnClickListener(this)

        rootView.date_to_et.setOnClickListener(this)

        rootView.income_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                createTripVM.setTripIncome(editable.toString().toInt())
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        rootView.currency_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                createTripVM.setTripCurrency(editable.toString())
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        rootView.percentage_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                createTripVM.setTripPercent(editable.toString().toInt())
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        rootView.percentage_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                createTripVM.setTripSum(editable.toString().toInt())
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

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
        if (date_from_et.text.isNullOrEmpty()) {
            date_from_et.setText(formatter.format(calendar.timeInMillis))
            createTripVM.setTripDateFrom(calendar.timeInMillis)
        } else {
            date_to_et.setText(formatter.format(calendar.timeInMillis))
            createTripVM.setTripDateTo(calendar.timeInMillis)
        }
    }

}
