package com.example.pakekotlinlearn.fragment.crud_disco


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.validarNombre
import com.example.pakekotlinlearn.databinding.FragmentModificarDiscoBinding
import java.util.*


class ModificarDisco : Fragment() {
    private var _binding: FragmentModificarDiscoBinding? = null
    val binding  get()= _binding!!
    private lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentModificarDiscoBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        repository= Repository(requireActivity().baseContext)
        var disco= Disco(0,"",0, Calendar.getInstance(),0,0)
        var vidatext = binding.textporcientoModi
        val seekbar = binding.seekBarModi
        val nombre = binding.EditextnombrediscoModi
        var vida= 0
        var id= 0

        setFragmentResultListener("requestmodikey"){
            requestkey, bundle->
            val result = bundle.getString("bundleKey")

            if (result== "ok"){

                id = bundle.getInt("id")
                disco= repository.getDiscoById(id)
                nombre.setText(disco.nombre)
                vida= disco.vida
                seekbar.progress= vida
                vidatext.text= vida.toString()

            }
        }



         
        seekbar.max = 100
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                vidatext.text= p1.toString()
                vida=p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }


        })


        binding.buttonCrearDiscoModi.setOnClickListener(){


            val nombre2=nombre.text.toString()

            if (validarNombre(nombre2) != null){

                binding.layouttextmodi.error= validarNombre(nombre2)

            }else{

                disco.nombre= nombre2
                disco.vida=vida
                repository.modificarDisco(disco)

            setFragmentResult("requestKey", bundleOf("bundleKey" to "ok"))
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, Discos()).commit()
        }

        }

        binding.imageButtonCancelarModi.setOnClickListener(){

            setFragmentResult("requestKey", bundleOf("bundleKey" to "ok"))
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, Discos()).commit()
        }

        binding.imageButtonEliminarDisco.setOnClickListener(){


            val dialog = AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar Disco")
                    .setMessage("Está seguro de eliminar el Disco")
                    .setCancelable(false)
                    .setNegativeButton("Cancelar"){view,_->
                        view.dismiss()
                    }
                    .setPositiveButton("Aceptar"){view,_->

                        repository.deleteDisco(id)
                        setFragmentResult("requestKey", bundleOf("bundleKey" to "ok"))
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, Discos()).commit()

                    }.create()

                    dialog.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.curvo15white,null))
                    dialog.show()


        }


    }
}