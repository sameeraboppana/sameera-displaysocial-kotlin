package com.sameera.displaysocial.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sameera.displaysocial.activity.R
import com.sameera.displaysocial.fragment.CameraFragment
import com.sameera.displaysocial.fragment.HomeFragment
import com.sameera.displaysocial.fragment.SearchFragment

private val TAB_TITLES = arrayOf(
        R.string.home,
        R.string.camera,
        R.string.search
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when(position) {
            0 -> HomeFragment.newInstance(position + 1)
            1 -> CameraFragment.newInstance(position + 1)
            2 -> SearchFragment.newInstance()
            else -> HomeFragment.newInstance(position + 1)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}