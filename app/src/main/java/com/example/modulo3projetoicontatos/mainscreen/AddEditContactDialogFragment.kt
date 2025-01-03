package com.example.modulo3projetoicontatos.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.modulo3projetoicontatos.ContactModel
import com.example.modulo3projetoicontatos.R
import com.example.modulo3projetoicontatos.databinding.DialogFragmentAddEditContactBinding
import com.example.modulo3projetoicontatos.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide

class AddEditContactDialogFragment(
    private val nextIndex: Int? = null,
    private val contactToEdit: ContactModel? = null,
    private val isEditProfile: Boolean = false
)  : DialogFragment(){

    private val placeholder: String = "https://st3.depositphotos.com/6672868/13701/v/450/depositphotos_137014128-stock-illustration-user-profile-icon.jpg "
    private lateinit var binding: DialogFragmentAddEditContactBinding
    private var pickedImage: String? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            pickedImage = uri.toString()
            Glide.with(this).load(pickedImage).into(binding.contactImage)
        } else {
            Log.d("PickVisualMedia", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentAddEditContactBinding.inflate(inflater, container, false)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)

//        contactToEdit?.let {
//            binding.screenTitle.text = "Editar Contato"
//            binding.contactNameEditText.setText( it.name )
//
//binding.relationshipTextView.setText
//        (it.relationship)
//
//            // 2. Encontrar o índice do relacionamento no array
//            val selectedPosition = relationshipOptions.indexOf(it.relationship)
//
//            // 3. Definir a seleção no Spinner
//            if(selectedPosition != -1){
//                binding.relationshipTextView.setSelection(selectedPosition)
//            }
//
//            binding.contactPhoneEditText.setText(it.phoneNumber)
//            binding.contactInstagramEditText.setText(it.instagram)
//            binding.contactFacebookEditText.setText(it.facebook)
//            binding.contactMailEditText.setText( it.email)
//        }

        if(isEditProfile){
            binding.screenTitle.text = "Editar Perfil"
            binding.contactRelationshipInputLayout.isVisible = false
        }

        Glide.with( this).load( contactToEdit?.contactImage ?: placeholder).into(binding.contactImage)

        var relationshipTypes  = emptyArray<String>()
        try {
            relationshipTypes = resources.getStringArray(R.array.relationship)// cria um array do xml com os relacionamentos (pai, mãe, irmão, etc.)
            val arrayAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line ,
                relationshipTypes
            )
            binding.relationshipTextView.setAdapter(arrayAdapter)
        }catch (ex: Exception){
            println("Erro na criação do relacionamento")
            println(relationshipTypes)
        }

        contactToEdit?.let {
            binding.screenTitle.text = "Editar Contato"
            binding.contactNameEditText.setText(
                it.name
            )

//binding.relationshipTextView.setText
            (it.relationship)

            // 2. Encontrar o índice do relacionamento no array
            val selectedPosition = relationshipTypes.indexOf(it.relationship)

            // 3. Definir a seleção no Spinner
            if(selectedPosition != -1){
                binding.relationshipTextView.setSelection(selectedPosition)
            }

            binding.contactPhoneEditText.setText(it.phoneNumber)
            binding.contactInstagramEditText.setText(it.instagram)
            binding.contactFacebookEditText.setText(it.facebook)
            binding.contactMailEditText.setText(
                it.email
            )
        }

        binding.contactImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            nextIndex?.let {
                if (binding.contactPhoneEditText.text.isNullOrBlank() ||
                    binding.contactMailEditText.text.isNullOrBlank() ||
                    binding.contactMailEditText.text.isNullOrBlank()
                // || binding.relationshipTextView.text.isNullOrBlank()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Preencha todos os campos indicados como obrigatórios.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

//                (activity as? OnInputListener)?.addContact(
//                    ContactModel(
//                        nextIndex,
//                        binding.contactNameEditText.text.toString(),
//                        binding.relationshipTextView.text.toString(),
//                        binding.contactPhoneEditText.text.toString(),
//                        binding.contactInstagramEditText.text.toString(),
//                        binding.contactFacebookEditText.text.toString(),
//                        binding.contactMailEditText.text.toString(),
//                        pickedImage ?: it.contactImage
//
//                    )
//                )

            }?: run{
                contactToEdit?.let {
                    if(isEditProfile){

//                        (activity as? OnInputListener)?.updateProfile(
//                            ContactModel(
//
                  //      it.id,
//                                binding.contactNameEditText.text.toString(),
//                                binding.relationshipTextView.text.toString(),
//                                binding.contactPhoneEditText.text.toString(),
//                                binding.contactInstagramEditText.text.toString(),
//                                binding.contactFacebookEditText.text.toString(),
//                                binding.contactMailEditText.text.toString(),
//                                pickedImage ?: it.contactImage
//                            )
//                        )

                    }else {

//                        (activity as? OnInputListener)?.updateContact(
//                            ContactModel(
//
                      //  it.id,
//                                binding.contactNameEditText.text.toString(),
//                                binding.relationshipTextView.text.toString(),
//                                binding.contactPhoneEditText.text.toString(),
//                                binding.contactInstagramEditText.text.toString(),
//                                binding.contactFacebookEditText.text.toString(),
//                                binding.contactMailEditText.text.toString(),
//                                pickedImage ?: it.contactImage
//                            )
//                        )

                    }
                }
            }
            dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    interface OnInputListener {
        fun openAddContact(nextIndex: Int)
        //        fun addContact(contactModel: ContactModel)
        fun updateContact(contactModel: ContactModel)
        fun updateProfile(contactModel: ContactModel)
    }
}
