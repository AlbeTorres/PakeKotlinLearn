package com.example.pakekotlinlearn

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pakekotlinlearn.databinding.ActivityMainBinding
import com.example.pakekotlinlearn.fragment.Contenedor
import com.example.pakekotlinlearn.fragment.OptionsFragment
import com.example.pakekotlinlearn.fragment.crud_disco.Discos
import com.example.pakekotlinlearn.fragment.crud_usuario.Clientes
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: Fragment
    private val intervalo = 2000
    private var tiempoprimerclic: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setDefaultFragment()

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item: MenuItem ->

            when(item.itemId){

                R.id.entregasItem ->{
                    fragment= Contenedor()
                    supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.clientesItem-> {

                    fragment= Clientes()
                    supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.discoItem-> {

                    fragment= Discos()
                    supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.ajusteItem->{

                    fragment= OptionsFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()

                    return@OnNavigationItemSelectedListener true

                }

            }

            false

        }


        binding.bottonmenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


            }

        private fun setDefaultFragment(){
            fragment= Contenedor()
            supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()

        }

    @Override
    override fun onBackPressed() {
        if (tiempoprimerclic + intervalo > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show()
        }
        tiempoprimerclic = System.currentTimeMillis()
    }






    }
