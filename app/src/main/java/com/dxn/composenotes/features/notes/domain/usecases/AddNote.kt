package com.dxn.composenotes.features.notes.domain.usecases

import com.dxn.composenotes.features.notes.data.models.InvalidNoteException
import com.dxn.composenotes.features.notes.data.models.Note
import com.dxn.composenotes.features.notes.domain.repositories.NoteRepository

class AddNote(private val notesRepository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title cannot be empty")
        }
        notesRepository.insertNote(note)
    }
}