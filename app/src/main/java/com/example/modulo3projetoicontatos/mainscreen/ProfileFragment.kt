package com.example.modulo3projetoicontatos.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.modulo3projetoicontatos.ContactModel
import com.example.modulo3projetoicontatos.R
import com.example.modulo3projetoicontatos.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var profile: ContactModel = ContactModel(
        -1,
        "",
        "",
        ""
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println(" dentro da ProfileFragment ")
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Recuperando o Bundle com o profile salvo
        if(savedInstanceState != null){
            val data: ContactModel = savedInstanceState.getSerializable("profile")  as ContactModel // "as" faz um casting/conversão para o tipo "ContactModel
            profile = data // faz isso aqui, pra que o bindProfile atualize os dados recuperados logo abaixo.
        }

        binding.editButton.setOnClickListener{
            activity?.supportFragmentManager?.let{
                val newFragment = AddEditContactDialogFragment(contactToEdit = profile, isEditProfile = true)

                newFragment.show( it, "AddEditContactDialogFragment" )
            }
        }
        bindProfile(profile )

        return binding.root
    }

    fun updateProfile(contactModel: ContactModel) {
        profile = contactModel
        bindProfile(contactModel)
        println(" dentro da updateProfile na AddEditContactDialogFragment ")
    }

    private fun bindProfile(contactModel: ContactModel) {
        // integra os dados com as Views
        binding.contactName.text =
            contactModel.name

        binding.infoMail.setInfoValue(
            contactModel.email
        )
        binding.infoFacebook.setInfoValue(contactModel.facebook)
        binding.infoPhone.setInfoValue(contactModel.phoneNumber)

        Glide.with(this).load(contactModel.contactImage).into(binding.contactImage)
    }

    //    Quando o profileFragment estiver sendo destruído salvar toda a informação do profile, como Serializable, no Bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("profile", profile)
    }

}