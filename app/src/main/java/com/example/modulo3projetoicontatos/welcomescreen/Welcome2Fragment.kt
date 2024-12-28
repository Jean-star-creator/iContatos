package com.example.modulo3projetoicontatos.welcomescreen


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.modulo3projetoicontatos.mainscreen.MainActivity
import com.example.modulo3projetoicontatos.databinding.FragmentWelcome2Binding

const val welcome_file_key = "welcome_file_key"
const val already_welcomeded_Key = "already_welcomeded_Key"

class Welcome2Fragment : Fragment() {
    private lateinit var binding: FragmentWelcome2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcome2Binding.inflate(inflater, container, false)

        binding.initButton.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // a flag "FLAG_ACTIVITY_CLEAR_TOP" não cria uma activity nova caso já exista uma, apenas usa a existente
            startActivity(intent)
            saveWelcomed() // chama pra gravar que já mostrou a tela na primeira vez que o usuário entrar no app
            activity?.finish()
        }

        return binding.root
    }

    // função pra salvar que já mostramos a tela de bem-vindo para o usuário e não mostrar toda vez que fizermos o swipe
    private fun saveWelcomed(){
        val editor = activity?.getSharedPreferences(welcome_file_key, Context.MODE_PRIVATE)?.edit()// sharedPreferences é similar a um buffer do app, com pouco espaçao, mas armazena dados sensiveis pro app
        editor?.putBoolean( already_welcomeded_Key, true)
        editor?.apply()
    }
}