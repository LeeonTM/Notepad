package com.LeonTimmerman.notepad.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.LeonTimmerman.notepad.database.NoteRepository

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)

    val note = noteRepository.getNotepad()
}