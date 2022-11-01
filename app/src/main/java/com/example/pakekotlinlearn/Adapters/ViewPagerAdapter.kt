package com.example.pakekotlinlearn.Adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){

    private val mFragmentList = ArrayList<Fragment>()
    private val mfragmenteTitleList = ArrayList<String>()


    override fun getItemCount(): Int {
       return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun getPageTitle(position:Int):CharSequence?{
        return mfragmenteTitleList[position]
    }

    fun addFragment(fragment: Fragment,title:String){
        mFragmentList.add(fragment)
        mfragmenteTitleList.add(title)
    }

}