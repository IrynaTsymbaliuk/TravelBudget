package com.example.travelbudget.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.travelbudget.R
import kotlinx.android.synthetic.main.empty_trip_card_fragment.view.*


class AddTripFr : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.empty_trip_card_fragment, container, false)

        rootView.empty_trip_card.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.create_trip))

        return rootView

    }
}