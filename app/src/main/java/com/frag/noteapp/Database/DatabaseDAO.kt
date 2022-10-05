package com.frag.noteapp.Database

import android.os.UserManager
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDAO {
    @Query("SELECT * FROM usernotemodel")
    suspend fun getAllNotes() : MutableList<Usernotemodel>
    @Query("SELECT * FROM usernotemodel WHERE id = (:noteId)")
    suspend fun getNotesById(noteId : Int) : List<Usernotemodel>
    @Query("SELECT * FROM usernotemodel WHERE title = (:title)")
    suspend fun searchByTitle(title : String) : MutableList<Usernotemodel>
    @Query("SELECT * FROM usernotemodel WHERE title LIKE '%' || :title || '%'")
    suspend fun searchNote(title : String) : MutableList<Usernotemodel>
    @Query("DELETE FROM usernotemodel")
    suspend fun deleteAllDatabase()
    @Update
    suspend fun updateNote(vararg usernotemodel: Usernotemodel)
    @Insert
    suspend fun insertAll(vararg usernotemodel: Usernotemodel)
    @Delete
    suspend fun delete(usernotemodel: Usernotemodel)
}