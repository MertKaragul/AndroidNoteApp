package com.frag.noteapp.RecyclerViews

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.recyclerview.widget.RecyclerView
import com.frag.noteapp.Database.AppDatabase
import com.frag.noteapp.Database.Usernotemodel
import com.frag.noteapp.R
import com.frag.noteapp.View.CustomAlertDialog
import com.frag.noteapp.Viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_alert_view.*
import kotlinx.android.synthetic.main.fragment_note_create_page.view.*
import kotlinx.coroutines.*
import javax.inject.Inject

class MainPageRecyclerView (private var usernotemodel: MutableList<Usernotemodel> , private val minusWidth : Int , var customAlertDialog: CustomAlertDialog? , val database : AppDatabase , var mainPageViewModel: MainPageViewModel , val mainPageNavHost : NavController) : RecyclerView.Adapter<MainPageRecyclerView.ViewHolder>() {

    private val TAG = "MainPageRecyclerView"
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val noteTitle = view.findViewById<TextView>(R.id.note_list_item_title)
        val noteDescription = view.findViewById<TextView>(R.id.note_list_item_description)
        val noteView = view.findViewById<LinearLayout>(R.id.note_create_linear_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.note_list_item_view , parent , false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getDAO = database.noteDao()
       if(usernotemodel[position].title != null && usernotemodel[position].description != null){
           holder.noteTitle.text = usernotemodel[position].title?.let { smallTextWrite(it) }
           holder.noteDescription.text = usernotemodel[position].description?.let {
               smallTextWrite(
                   it
               )
           }
       }else{
           holder.noteTitle.text = ""
           holder.noteDescription.text = ""
       }

        holder.noteView.setOnLongClickListener {
            customAlertDialog?.show()
            customAlertDialog?.dialog_ok_button?.setOnClickListener {
                CoroutineScope(Dispatchers.Main).async {
                    getDAO.delete(usernotemodel[position])
                    customAlertDialog?.dismiss()
                    usernotemodel.removeAt(position)
                    mainPageViewModel.getUserNote()
                    notifyDataSetChanged()
                    this.cancel()
                }
            }
            return@setOnLongClickListener true
        }

        holder.noteView.setOnClickListener {
            val bundle = bundleOf("user_note" to usernotemodel)
            mainPageNavHost.navigate(R.id.action_mainPage2_to_noteDetailsPage , bundle)
        }
    }


    private fun smallTextWrite(text : String) : String{
        val getCharSize = text.toCharArray()
        var totalText = ""
        val convertCharToString = mutableListOf<String>()
        if(getCharSize.size >= minusWidth){
            for (size in 0..getCharSize.size){
                if(size < minusWidth){
                    convertCharToString.add(getCharSize[size].toString())
                }
            }

            convertCharToString.forEach {
                totalText += it
            }

            totalText += "..."
        }else totalText = text

        return totalText
    }

    override fun getItemCount(): Int {
        return usernotemodel.size
    }


}