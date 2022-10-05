package com.frag.noteapp.View

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frag.noteapp.Database.AppDatabase
import com.frag.noteapp.Database.Usernotemodel
import com.frag.noteapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_details_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoteDetailsPage : Fragment() {
    @Inject
    lateinit var appDatabase : AppDatabase
    private val TAG = "NoteDetailsPage"
    private var clikableEdit = false
    private lateinit var usernotemodel: MutableList<Usernotemodel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_details_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userNote : MutableList<Usernotemodel> = arguments?.get("user_note") as MutableList<Usernotemodel>
        note_details_title.setText(userNote[0].title)
        note_details_description.setText(userNote[0].description)
        textSizeIncreaseAndMinus()
        editButton()
        usernotemodel = userNote
        super.onViewCreated(view, savedInstanceState)
    }

    fun textSizeIncreaseAndMinus(){
        var textSizeDescription = note_details_description.textSize
        var textSizeTitle = note_details_title.textSize
        text_size_increase_button.setOnClickListener {
            textSizeDescription += 4
            textSizeTitle += 4
            note_details_description.setTextSize(TypedValue.COMPLEX_UNIT_SP , textSizeDescription )
            note_details_title.setTextSize(TypedValue.COMPLEX_UNIT_SP , textSizeTitle )
        }

        text_size_minus_button.setOnClickListener {
            textSizeDescription -= 4
            textSizeTitle -= 4
            note_details_description.setTextSize(TypedValue.COMPLEX_UNIT_SP , textSizeDescription )
            note_details_title.setTextSize(TypedValue.COMPLEX_UNIT_SP , textSizeTitle )
        }
    }

    fun editButton(){
        note_details_edit_button.setOnClickListener {
            if(clikableEdit){
                clikableEdit = false
                note_details_edit_button.setImageResource(R.drawable.ic_baseline_save_24)
                note_details_title.isClickable = true
                note_details_title.isEnabled = true
                note_details_description.isClickable = true
                note_details_description.isEnabled = true
            }else {
                clikableEdit = true
                note_details_edit_button.setImageResource(R.drawable.ic_baseline_edit_24)
                note_details_title.isClickable = false
                note_details_title.isEnabled = false
                note_details_description.isClickable = false
                note_details_description.isEnabled = false
                usernotemodel[0].title = note_details_title.text.toString()
                usernotemodel[0].description = note_details_description.text.toString()
                updateDatabase(usernotemodel[0])
            }

        }
    }


    fun updateDatabase(usernotemodel: Usernotemodel){
        CoroutineScope(Dispatchers.Main).launch {
            appDatabase.noteDao().updateNote(usernotemodel)
        }
    }
}