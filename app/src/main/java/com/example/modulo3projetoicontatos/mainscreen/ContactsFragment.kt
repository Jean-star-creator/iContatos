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
            "999485883",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/women/23.jpg",
            instagram = "instagram"
        ),
        ContactModel(
            1,
            "Ricardo",
            "Pai/Mãe",
            "999485883",
            email = "mail@gmail.com",
            contactImage = "https://randomuser.me/api/portraits/men/54.jpg",
            instagram = "brunogermano3"
        ),
        ContactModel(
            2,
            "Patrícia",
            "Pai/Mãe",
            "999485883",
            email = "mail@gmail.com",
                    contactImage = "https://randomuser.me/api/portraits/women/33.jpg"
        ),
        ContactModel(
            3,
            "Carla",
            "Pai/Mãe",
            "999485883",
            email = "mail@gmail.com",
                    contactImage = "https://randomuser.me/api/portraits/women/20.jpg"
        ),
        ContactModel(
            4,
            "Joaquim",
            "Amigo",
            "999485883",
            email = "mail@gmail.com",
                    contactImage = "https://randomuser.me/api/portraits/men/14.jpg"
        ),
        ContactModel(
            5,
            "Vanuza",
            "Pai/Mãe",
            "999485883",
            email = "mail@gmail.com",
                    contactImage = "https://randomuser.me/api/portraits/women/87.jpg"
        ),
        ContactModel(
            7,
            "Luiza",
            "Pai/Mãe",
            "999485883",
            email = "mail@gmail.com",
                    contactImage = "https://randomuser.me/api/portraits/women/50.jpg"
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater,container, false)

        originalContacts = contacts

        adapter = ContactsAdapter( contacts, Glide.with(this), originalContacts, activity as ContactDetailDialogFragment.OnInputListener )

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

        binding.floatingAddButton.setOnClickListener{
            (activity as? MainActivity)?.openAddContact( contacts.size ) // modificadoPelaIA
        }// ultima versoa dada pela iA

//        binding.floatingAddButton.setOnClickListener{
////            addToList() // eu bruno coloquei o método errado aqui primeiro camo o fragment para inserir o s dados., como na inha abaixo
//            (activity as? AddEditContactDialogFragment.OnInputListener)?.openAddContact(contacts.size) // modificadoPelaIA
//        } // versao dada pela IA que testei e funcionou

        return binding.root
    }

    fun deleteContact(contactModel: ContactModel) {
        contacts.remove(contactModel)
        adapter.notifyDataSetChanged()
    }

    // EU implementei para cria o novo contato
    fun addToList(contactModel: ContactModel) {
        contacts.add(contactModel)
        adapter.notifyDataSetChanged() // modificadoPelaIA
    }

    fun updateContact(contactModel: ContactModel) {
        // 1. Encontra o índice do objeto na lista baseado no ID
        val indexToUpdate = contacts.indexOfFirst {
            it.id== contactModel.id
        }

        // 2. Atualiza o objeto na lista, se encontrado
        if (indexToUpdate != -1) {
            contacts[indexToUpdate] = contactModel
            adapter.notifyItemChanged(indexToUpdate) // modificadoPelaIA
        }

        /*

        // Para verificar a atualização
        println("Lista de contatos após a atualização dos contacts.forEach { println(it) }

         */
    }
}