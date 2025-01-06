package com.example.modulo3projetoicontatos.mainscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.modulo3projetoicontatos.ContactModel
import com.example.modulo3projetoicontatos.R
import com.example.modulo3projetoicontatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() ,
                     ContactDetailDialogFragment.OnInputListener ,
                     AddEditContactDialogFragment.OnInputListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }

    override fun onStart() { // vinculando minha bottom bar com o containerView
        super.onStart()
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            findNavController(R.id.fragmentContainerView)
        )
    }

    /* implementa a interface para abrir a contactDetailDialogFragment e passar para
       dentro dela o valor do contactModel para setar os valores dos contatos nos
        componentes criados */
    override fun openContactDialog(contactModel: ContactModel) {

        // instanciando o contactDetailDialogFragment usando o supportFragmentManager
        val fragmentManager = supportFragmentManager
        val newFragment = ContactDetailDialogFragment(contactModel, this)

        newFragment.show(fragmentManager,"ContactDetailDialogFragment")
    }

    override fun openAddContact(nextIndex: Int){
        val fragmentManager = supportFragmentManager
        val newFragment = AddEditContactDialogFragment(nextIndex)

        newFragment.show (fragmentManager, "AddEditContactDialogFragment")
    }

    override fun addContact(contactModel: ContactModel) {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        val contactsFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is ContactsFragment } as? ContactsFragment
        contactsFragment?.addToList(contactModel) // criado por mim para complementar o que faltou da Anny
    }

    override fun deleteContact(contactModel: ContactModel) {
        // na MainActivity eu não tenho o FragmentContacts, logo, tenho que buscá-lo
        val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        // buscar na lista de framents a fragment do tipo ContactsFragment, pego ele e faço casting pro ContactsFragment
        val contactsFragment = navHostFragment?.childFragmentManager?.fragments?.find { it is ContactsFragment } as? ContactsFragment

        contactsFragment?.deleteContact( contactModel )
    }

    override fun openMail(link: String?) {
        // abrir intent para abrir o email, pra isso ele busca um app que corresponda a envio de emails
        try {
            val intent = Intent(Intent.ACTION_SENDTO)

            intent.data = Uri.parse("mailto:$link")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // isso é pra abrir em uma nova activity e não substituir a que estamos
            startActivity(intent)
        }catch (ex: ActivityNotFoundException){
            Toast.makeText(
                this@MainActivity,
                "There is no email client Installed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun dialPhone(phone: String?) {
        // Faz abertura da intent de discagem, onde através da "ACTION_DIAL" o android busca por um app que possua essa intent para fazer a discagem
        val number = Uri.parse("tel:$phone")
        val callIntent = Intent(Intent.ACTION_DIAL, number)
        startActivity(callIntent)
    }

    override fun openInstagram(userName: String?) {
        // Aqui abriremos uma ACTION_VIEW, procuraremos apps que tenham pacote com o nome "com.instagram.android" que é o nome do pacote do instagram no android

        val uri = Uri.parse(" https://instagram.com/_u/$userName")
        val instagramIntent = Intent(Intent.ACTION_VIEW, uri )

        instagramIntent.setPackage(" com.instagram.android")

        try {
            /* Vai tentar abrir para o perfil do user, não tendo ele, abre o padrão do Instagram
            Se o app do instagram não estiver instalado, abre em uma WEB VIEW mesmo
             */
            startActivity(instagramIntent)
        }catch (ex: ActivityNotFoundException){
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://instagram.com/$userName")
                    )
                )
        }

    }

}
