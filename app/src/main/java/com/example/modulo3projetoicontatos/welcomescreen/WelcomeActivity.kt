package com.example.modulo3projetoicontatos.welcomescreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.example.modulo3projetoicontatos.R
import com.example.modulo3projetoicontatos.databinding.ActivityWelcomeBinding
import com.example.modulo3projetoicontatos.mainscreen.MainActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // se o user tiver visto a tela de boas vindas, passamos direto pro MainActivity
        // Lembrando que AndroidMAnisfest.xml defini que a tela principal/tela que inicializa, será a principal, ou seja, a que irá iniciliazar, e não a MAinActivity
        if (isAlreadyWelcomeded()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // a flag "FLAG_ACTIVITY_CLEAR_TOP" não cria uma activity nova caso já exista uma, apenas usa a existente
            startActivity(intent)
            finish()
        }

        val fragmentList = arrayListOf(Welcome1Fragment(), Welcome2Fragment())

        val viewPagerAdapter = WelcomeAdapter(supportFragmentManager, lifecycle, fragmentList)

        binding.welcomeViewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(binding.welcomeTabLayout, binding.welcomeViewPager2){ tab, position ->

        }.attach() // precisamos passar estes parâmetros para que o tabLayout, os dots/pontosDeNavegação, saiba em que página ele está

    }

    private fun isAlreadyWelcomeded(): Boolean {
        val sharedPreference = getSharedPreferences(welcome_file_key, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean( already_welcomeded_Key, false ) // se não tiver a chave "already_welcomeded_Key", o método retorna false pra mostrar a tela de boas vindas
    }

}
