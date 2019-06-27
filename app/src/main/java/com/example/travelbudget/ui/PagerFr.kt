package com.example.travelbudget.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.travelbudget.R
import com.example.travelbudget.vm.PagerVM
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pager.view.*

class PagerFr : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pagerVM: PagerVM
    private var listOfTripIDs: List<Long> = ArrayList<Long>()
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: FragmentStatePagerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pager, container, false)

        activity!!.toolbar.title = "Travel budget"

        tabLayout = view.findViewById(R.id.tab_layout)
        view.add_cost_fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.createCostFr))

        viewPager = view.findViewById(R.id.view_pager)
        initTabLayout()
        pagerVM = ViewModelProviders.of(this).get(PagerVM::class.java)

        pagerVM.getTripIDs().observe(this, Observer {
            listOfTripIDs = it
            pagerAdapter = MyFragmentPagerAdapter(activity!!.supportFragmentManager)
            viewPager.adapter = pagerAdapter

        })

        return view
    }

    override fun onResume() {
        super.onResume()
        if (::pagerAdapter.isInitialized) {
            pagerAdapter = MyFragmentPagerAdapter(activity!!.supportFragmentManager)
            viewPager.adapter = pagerAdapter
        }
    }

    private inner class MyFragmentPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return listOfTripIDs.size
        }

        override fun getItem(position: Int): Fragment {
            return PagerItemFr.newInstance(listOfTripIDs[position])
        }

    }

    private fun initTabLayout() {
        tabLayout.setupWithViewPager(viewPager, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.outlineProvider = null
        }
    }

}