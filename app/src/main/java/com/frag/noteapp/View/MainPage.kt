package com.frag.noteapp.View
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frag.noteapp.Database.AppDatabase
import com.frag.noteapp.Database.Usernotemodel
import com.frag.noteapp.R
import com.frag.noteapp.RecyclerViews.MainPageRecyclerView
import com.frag.noteapp.Viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main_page.*
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainPage : Fragment(R.layout.fragment_main_page) {
    @Inject lateinit var userDatabase : AppDatabase
    @Inject lateinit var customAlertDialog: CustomAlertDialog
    private val viewModel: MainPageViewModel by viewModels()
    private val TAG = "MainPage"
    private var selfRecyclerViewAdapter : MainPageRecyclerView? = null
    private var searchJob : Job? = null
    private lateinit var supportFragmentManager : NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        supportFragmentManager = Navigation.findNavController(view)

        viewModel.getUserNote()
        viewModel.noteLiveData.observe(viewLifecycleOwner , Observer {
            if(it.isNullOrEmpty()) {
                main_page_recycler_view.visibility = View.INVISIBLE
                main_page_error_page.visibility = View.VISIBLE
                main_page_error_image.setImageResource(R.drawable.ic_baseline_note_add_24)
                main_page_error_text.text = "Database empty, Let's come add note."
            }else{
                main_page_error_page.visibility = View.GONE
                main_page_recycler_view.visibility = 0
                setRecyclerView(it)
                searchNote()
            }
        })

        main_page_to_create_note_page.setOnClickListener {
            Thread.sleep(100)
            supportFragmentManager.navigate(R.id.action_mainPage2_to_noteCreatePage)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun searchNote(){
        main_page_search_note.addTextChangedListener {
            if(!it.isNullOrEmpty()){
                searchJob?.cancel()
                searchJob = CoroutineScope(Dispatchers.IO).async{
                    viewModel.searchUserNote(it.toString())
                    selfRecyclerViewAdapter?.notifyDataSetChanged()

                }
            }
            if(it.toString().equals("")) viewModel.getUserNote()
        }
    }

    private fun setRecyclerView(usernotemodels: MutableList<Usernotemodel>) {
        val widthDisplay = resources.displayMetrics.widthPixels
        val recyclerView = view?.findViewById<RecyclerView>(R.id.main_page_recycler_view)
        val recyclerViewAdapter = MainPageRecyclerView(usernotemodels , (widthDisplay / 100) , customAlertDialog , userDatabase , viewModel , supportFragmentManager)
        recyclerView?.layoutManager = GridLayoutManager(this.requireContext() , 2)
        recyclerView?.adapter = recyclerViewAdapter
        selfRecyclerViewAdapter = recyclerViewAdapter

    }
}