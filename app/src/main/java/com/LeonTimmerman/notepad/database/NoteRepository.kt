package com.LeonTimmerman.notepad.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.LeonTimmerman.notepad.model.Note

class NoteRepository(context: Context) {

    private val noteDao: NoteDao

    init {
        val database = NotepadRoomDatabase.getDatabase(context)
        noteDao = database!!.noteDao()
    }

    fun getNotepad(): LiveData<Note?> = noteDao.getNotepad()

    suspend fun updateNotepad(note: Note) = noteDao.updateNote(note)
}