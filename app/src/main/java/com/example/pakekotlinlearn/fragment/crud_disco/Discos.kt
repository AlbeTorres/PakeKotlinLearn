package com.example.pakekotlinlearn.fragment.crud_disco

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pakekotlinlearn.Adapters.RecyclerDiscosAdapter
import com.example.pakekotlinlearn.Adapters.RecyclerPendAdapter
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.databinding.FragmentDiscosBinding
import com.example.pakekotlinlearn.fragment.Noclientes
import com.example.pakekotlinlearn.fragment.crud_usuario.CrearCliente
import java.util.*
import kotlin.collections.ArrayList


class Discos : Fragment() {
    private var _binding: FragmentDiscosBinding? = null
    val binding  get()= _binding!!
    private var discosList= ArrayList<Disco>()
    private lateinit var repository: Repository





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding= FragmentDiscosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository= Repository(requireActivity().baseContext)
        repository.cargarDiscos(discosList)

        binding.recyclerviewDisco.layoutManager = GridLayoutManager(requireActivity().baseContext,2,RecyclerView.VERTICAL,false)


        setFragmentResultListener("requestkey"){
            requestkey, bundle->
            val result = bundle.getString("bundleKey")

            if (result== "ok"){

                binding.recyclerviewDisco.adapter?.notifyDataSetChanged()
            }
        }


        if(!discosList.isEmpty()){

            binding.recyclerviewDisco.adapter = RecyclerDiscosAdapter(discosList){

                var result = Bundle()
                result.putInt("id",it.id)
                result.putString("nombre",it.nombre)
                result.putInt("vida",it.vida)
                result.putString("bundleKey","ok")



                setFragmentResult("requestmodikey",result )


                requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, ModificarDisco())
                        .commit()
            }

        }else{


        }




        binding.floatingActionButton.setOnClickListener(){


            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, CrearDisco())
                    .commit()
        }

        

    }



}