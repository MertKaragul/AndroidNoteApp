package com.frag.noteapp.View

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.frag.noteapp.Database.AppDatabase
import com.frag.noteapp.Database.Usernotemodel
import com.frag.noteapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_create_page.*
import kotlinx.coroutines.*
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class NoteCreatePage : Fragment(R.layout.fragment_note_create_page) {
    @Inject
    lateinit var database : AppDatabase
    lateinit var navigationController : NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navC = Navigation.findNavController(view)
        main_page_to_create_note_page_button.setOnClickListener {
            checkTextAndSave()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun checkTextAndSave(){
        if(!create_note_title.text.toString().equals("") && !create_note_description.text.toString().equals("")){
            try {
                runBlocking{
                    database.noteDao().insertAll(Usernotemodel(title = create_note_title.text.toString() , description = create_note_description.text.toString() , date = "${LocalDateTime.now()}"))
                    database.noteDao().searchByTitle(create_note_title.text.toString()).let {
                        Toast.makeText(requireContext() , "Data has been added" , Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e : Exception){
                Toast.makeText(context , "Database error , please try again" , Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(context , "It can not be added" , Toast.LENGTH_LONG).show()
        }
    }
}