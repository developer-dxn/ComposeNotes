package com.dxn.composenotes.features.notes.domain.usecases

import com.dxn.composenotes.features.notes.data.models.Note
import com.dxn.composenotes.features.notes.domain.repositories.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id:Int): Note? {
        return repository.getNoteById(id)
    }

}