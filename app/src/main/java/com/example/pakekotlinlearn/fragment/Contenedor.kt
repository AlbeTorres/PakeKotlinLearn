package com.example.pakekotlinlearn.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.example.pakekotlinlearn.Adapters.ViewPagerAdapter
import com.example.pakekotlinlearn.Notificaciones.freeAll
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.databinding.FragmentContenedorBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class Contenedor : Fragment() {

    private var _binding: FragmentContenedorBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentContenedorBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewpageAdapter = ViewPagerAdapter(this)

        binding.toolbarpen.inflateMenu(R.menu.menucliente)
        binding.toolbarpen.setOnMenuItemClickListener {

            if (it.itemId == R.id.clientesreactivar){

                freeAll(requireContext())

                childFragmentManager.setFragmentResult("requestkeypend", bundleOf("bundleKeypend" to "okpend"))


            }

            return@setOnMenuItemClickListener true}

        fullViewPager(binding.viewPager2,viewpageAdapter)

       TabLayoutMediator(binding.tabs,binding.viewPager2){
           tab: TabLayout.Tab, position: Int ->  tab.text = viewpageAdapter.getPageTitle(position) }.attach()




    }

     private fun fullViewPager(viewPager: ViewPager2, viewpageAdapter: ViewPagerAdapter){


        viewpageAdapter.addFragment(Pendientes(), "Pendientes")
        viewpageAdapter.addFragment(Copiando(), "Copiando")

        viewPager.adapter = viewpageAdapter



    }


}