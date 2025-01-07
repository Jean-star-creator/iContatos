package com.example.modulo3projetoicontatos.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.modulo3projetoicontatos.ContactModel
import com.example.modulo3projetoicontatos.R
import com.example.modulo3projetoicontatos.databinding.DialogFragmentContactDetailBinding
import com.bumptech.glide.Glide

/* vamos usar o DialogFragment porque a "ContactDetailDialogFragment" não está associada a nenhuma stack, a não ser a stack principal e para ganhar as propriedades de um AlertDialog
    Ele também é fácil de criar e matar pois ele fica por cima das outras telas
*/
class ContactDetailDialogFragment(
    private val contactModel: ContactModel, // Do jean  funcionou aqui
    private val onInputListener: OnInputListener
):DialogFragment() {
    //    private lateinit var contactModel: ContactModel
    private lateinit var binding: DialogFragmentContactDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        println(" dentro da ContactDialogFragment ")

        binding = DialogFragmentContactDetailBinding.inflate(inflater,container, false)
        // colocar o dialogfragment em fullscreen
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)

        // Seta a visibilidade dos botões de ações para os contatos, isso economiza processamento porque não cria o ClicKListener para quem não precisa
        if (contactModel.instagram.isNullOrBlank()){
            binding.buttonInstagram.isVisible = false
        }else{
            // Só usamos a function "onclickListener" nos buttons customizados que criamos
            binding.buttonInstagram.onclickListener{
                onInputListener.openInstagram(contactModel.instagram)
            }
        }

        if (contactModel.email.isNullOrBlank()){
            binding.buttonMail.isVisible = false
        }else{
            binding.buttonMail.onclickListener{
                onInputListener.openMail(
                    contactModel.email
                )
            }
        }

        if (contactModel.phoneNumber.isNullOrBlank()){
            binding.buttonCall.isVisible = false
        }else{
            binding.buttonCall.onclickListener{
                onInputListener.dialPhone( contactModel.phoneNumber )
            }
        }

        binding.contactName.text =
            contactModel.name

        binding.contactRelationship.text = contactModel.relationship
        binding.infoFacebook.setInfoValue(contactModel.facebook)
        binding.infoPhone.setInfoValue(contactModel.phoneNumber)
        binding.infoInstagram.setInfoValue(contactModel.instagram)
        binding.infoMail.setInfoValue(
            contactModel.email
        )


        binding.closeButton.setOnClickListener{
            dismiss()
        }

        binding.editButton.setOnClickListener { // modificadoPelaIA
            // onInputListener.openEditDialog(contactModel) // modificadoPelaIA
            (activity as? AddEditContactDialogFragment.OnInputListener)?.openEditDialog(contactModel)
            dismiss() // modificadoPelaIA
        }

        // o buttonDelete é um botão normal, só usamos a function "onclickListener" nos buttons customizados que criamos
        binding.buttonDelete.setOnClickListener{
            onInputListener.deleteContact(contactModel)
            dismiss()
        }

        Glide.with(this).load(contactModel.contactImage).into(binding.contactImage)

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    fun updateContact( updatedContact: ContactModel) {
        binding.contactName.text =
            updatedContact.name

        binding.contactRelationship.text = updatedContact.relationship
        binding.infoFacebook.setInfoValue(updatedContact.facebook)
        binding.infoPhone.setInfoValue(updatedContact.phoneNumber)
        binding.infoInstagram.setInfoValue(updatedContact.instagram)
        binding.infoMail.setInfoValue(
            updatedContact.email
        )

        Glide.with(this).load(updatedContact.contactImage).into(binding.contactImage) // modificadoPelaIA
    }

    interface OnInputListener{
        fun openContactDialog(contactModel: ContactModel)
        fun deleteContact(contactModel: ContactModel)
        fun openMail(link: String?) // Botão pra abrir e-mail
        fun dialPhone(phone: String?)
        fun openInstagram(userName: String?)
    }

}