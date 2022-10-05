package com.frag.noteapp.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frag.noteapp.Database.AppDatabase
import com.frag.noteapp.Database.DatabaseDAO
import com.frag.noteapp.Database.Usernotemodel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var appDatabase: AppDatabase

    private var mutableLiveData : MutableLiveData<MutableList<Usernotemodel>?> = MutableLiveData()
    var noteLiveData : MutableLiveData<MutableList<Usernotemodel>?> = mutableLiveData
    fun getUserNote(){
        viewModelScope.launch{
            mutableLiveData.value = appDatabase.noteDao().getAllNotes()
        }
    }

    fun searchUserNote(title : String){
        viewModelScope.async {
            mutableLiveData.value = appDatabase.noteDao().searchNote(title)
        }
    }
}