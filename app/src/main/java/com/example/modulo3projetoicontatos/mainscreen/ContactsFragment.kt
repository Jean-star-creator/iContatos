package com.example.modulo3projetoicontatos.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.example.modulo3projetoicontatos.ContactModel
import com.example.modulo3projetoicontatos.R
import com.example.modulo3projetoicontatos.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var adapter: ContactsAdapter

    private lateinit var originalContacts: MutableList<ContactModel>

    private var contacts: MutableList<ContactModel> = mutableListOf(
        ContactModel(
            0,
            "Paula",
            "Imão/Irmã",
            "+353 83 1387243",
            email = "mail@gmail.com ",
            contactImage = "https://randomuser.me/api/portraits/women/23.jpg",
            instagram = "brunogermano3"
        ),

        ContactModel(
            3,
            "Jean",
            "Amigo",
            "999485883",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/men/14.jpg",
            instagram = "ibsenjean"

        ),
        ContactModel(
            4,
            "Joaquim",
            "Amigo",
            "999485883",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/men/34.jpg"
        ),

        ContactModel(
            5,
            "Ana",
            "Amigo",
            "999485883",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/women/44.jpg"
        ),

        ContactModel(
            6,
            "Jean Gray",
            "Amigo",
            "999485883",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/women/20.jpg"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater,container, false)

        originalContacts = contacts

        adapter = ContactsAdapter(contacts, Glide.with(this), originalContacts ,activity as ContactDetailDialogFragment.OnInputListener  )

        binding.contactsRecyclerView.adapter = adapter
        // adicionando o divisor de decoração na vertical
        binding.contactsRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        // implementando o searchView, a cada objeto que o usuário passar no searchView ele pesquisa
        binding.searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // a cada mudança, enquanto o usuário digita, a gente chama o filter do adapter e filtra os contatos de acordo com o novo texto pesquisado
                adapter.filter.filter(newText)
                return false
            }

        } )

        return binding.root
    }

    fun deleteContact(contactModel: ContactModel) {
        contacts.remove(contactModel)
        adapter.notifyDataSetChanged()
    }

}