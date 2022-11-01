package com.example.pakekotlinlearn.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.databinding.ItemDiscoBinding

class RecyclerDiscosAdapter(private var discos : ArrayList<Disco> = ArrayList(),private  var listener: (Disco)-> Unit):
        RecyclerView.Adapter<RecyclerDiscosAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_disco,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()= discos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = discos[position]

        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemDiscoBinding.bind(view)

        fun bind(item:Disco){

            binding.textViewNombreDisco.text= item.nombre
            binding.textViewVidaDisco.text= item.vida.toString()
            binding.textViewEntregasDisco.text= item.entregas.toString()




        }

    }


}