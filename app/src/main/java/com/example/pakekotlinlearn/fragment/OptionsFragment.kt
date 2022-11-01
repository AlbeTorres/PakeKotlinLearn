package com.example.pakekotlinlearn.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.pakekotlinlearn.Aplication.Companion.prefs
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.validarHora
import com.example.pakekotlinlearn.Validaciones.validarMonto
import com.example.pakekotlinlearn.Validaciones.validarNombreCliente
import com.example.pakekotlinlearn.databinding.FragmentOptionsBinding
import com.example.pakekotlinlearn.databinding.FragmentPendientesBinding
import java.util.ArrayList


class OptionsFragment : Fragment() {
    private var _binding: FragmentOptionsBinding? = null
    private val binding get()= _binding!!




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentOptionsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.textmontoclienteopciones.setText(prefs.getMonto().toString())
        binding.texttiempoclienteopciones.setText(prefs.getTiempo().toString())

        binding.button.setOnClickListener{

            var monto = binding.textmontoclienteopciones.text.toString()
            var hora = binding.texttiempoclienteopciones.text.toString()

            if( validarHora(hora)!= null || validarMonto(monto) != null){

                if(validarHora(hora) != null){ binding.layouttextclientetiempoopciones.error = validarHora(hora) }
                else {binding.layouttextclientetiempoopciones.error = null}

                if(validarMonto(monto) != null){ binding.layouttextclientemontoopciones.error = validarMonto(monto) }
                else {binding.layouttextclientemontoopciones.error = null}

            }else{

                prefs.saveMonto(binding.textmontoclienteopciones.text.toString().toInt() )
                prefs.saveTiempo(binding.texttiempoclienteopciones.text.toString().toInt())

                Toast.makeText(requireContext(), "Ajustes Guardados",Toast.LENGTH_LONG).show()
            }




        }

        binding.telegram.setOnClickListener {  telegramChannel()}
        binding.correo.setOnClickListener { sendEmail() }





    }

    fun telegramChannel(){
        val appname= "org.telegram.messenger"
        val urlgroup = "https://t.me/elpake"
        if( isAPPAvalible(requireContext(),appname)){

            val intent= Intent(Intent.ACTION_VIEW, Uri.parse(urlgroup))
            intent.setPackage(appname)
            requireActivity().startActivity(Intent.createChooser(intent,"Únete a nuestra Comunidad en Telegram"))



        }else{

            Toast.makeText(requireContext(),"Telegram no está instalado en el Móvil",Toast.LENGTH_LONG).show()


        }



    }

    fun sendEmail(){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("albertocorreoficial@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT,"sobre Pake")


        }
        requireActivity().startActivity(Intent.createChooser(intent,"Envíame un email"))

    }


}



    fun isAPPAvalible(context: Context, appname:String):Boolean{


        val pm = context.packageManager

        try {
            pm.getPackageInfo(appname,PackageManager.GET_ACTIVITIES)
            return true

        }catch (e:PackageManager.NameNotFoundException){

            return false
        }

    }