package com.example.pakekotlinlearn.fragment.crud_disco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.validarNombre
import com.example.pakekotlinlearn.databinding.FragmentCrearDiscoBinding
import java.util.*


class CrearDisco : Fragment() {
    private var _binding : FragmentCrearDiscoBinding? = null
    private val binding get()=_binding!!
    private lateinit var repository: Repository



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentCrearDiscoBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var vida  = 0

        val c = Calendar.getInstance()
        repository= Repository(requireActivity().baseContext)

        val vidatext = binding.textporciento
        val seekbar = binding.seekBar
        seekbar.progress = 0
        seekbar.max = 100

        seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                vidatext.text= p1.toString()
                vida=p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }


        })




        binding.buttonCrearDisco.setOnClickListener(){

            val nombreDisco= binding.textnombredisco.text.toString()

            if (validarNombre(nombreDisco)!= null){

                binding.layouttext.error= validarNombre(nombreDisco)



            }else{

            repository.insertarDisco(Disco(0,nombreDisco.toString(),vida,c,0,1))
            setFragmentResult("requestKey", bundleOf("bundleKey" to "ok"))

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, Discos()).commit()

            }

        }

        binding.imageButtonCancelar.setOnClickListener(){
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, Discos()).commit()
        }



    }


}