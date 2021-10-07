package com.dxn.composenotes.features.notes.presentation.screens.addnotes

import androidx.lifecycle.ViewModel
import com.dxn.composenotes.features.notes.domain.repositories.NoteRepository
import com.dxn.composenotes.features.notes.domain.usecases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAndEditNoteViewModel
@Inject
constructor(
   private val notesUseCases: NotesUseCases
) : ViewModel() {

}