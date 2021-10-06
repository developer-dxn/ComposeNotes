package com.dxn.composenotes.features.notes.domain.usecases

import com.dxn.composenotes.features.notes.data.models.Note
import com.dxn.composenotes.features.notes.domain.repositories.NoteRepository

class DeleteNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}