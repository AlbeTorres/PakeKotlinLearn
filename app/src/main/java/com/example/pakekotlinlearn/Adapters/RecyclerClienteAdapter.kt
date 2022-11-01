package com.example.pakekotlinlearn.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.setDia
import com.example.pakekotlinlearn.databinding.ItemClientecrudBinding

class RecyclerClienteAdapter(private var clienteList:ArrayList<Cliente> = ArrayList(),private var listener:((Cliente) -> Unit))
    :RecyclerView.Adapter<RecyclerClienteAdapter.ClienteViewHolder>(), Filterable {


    private var clientestodos= ArrayList<Cliente>(clienteList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_clientecrud,parent,false)
        return ClienteViewHolder(view)
    }

    override fun getItemCount()= clienteList.size

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val itemCliente= clienteList[position]

        holder.bind(itemCliente)

        holder.itemView.setOnClickListener {listener(itemCliente)}
    }


    class ClienteViewHolder(view: View):RecyclerView.ViewHolder(view){
            val binding = ItemClientecrudBinding.bind(view)

        fun bind(itemCliente:Cliente){

            binding.nombreRecyClienteCrud.text= itemCliente.nombre
            binding.textdiaclienteCrud.text = setDia(itemCliente.dia)
        }

    }

    override fun getFilter(): Filter {
        return object: Filter(){

            override fun performFiltering(p0: CharSequence?): FilterResults {
                var aux = ArrayList<Cliente>()
                if(p0.toString().isEmpty()){

                    aux.addAll(clientestodos)

                }else{

                    for (cliente in clientestodos){
                        if( cliente.nombre.toLowerCase().contains(p0.toString().toLowerCase())){
                            aux.add(cliente)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values= aux
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                clienteList.clear()
                clienteList.addAll(p1?.values as Collection<Cliente>)
                notifyDataSetChanged()
            }
        }
    }





}