package com.dxn.composenotes.features.notes.domain.repositories

import com.dxn.composenotes.features.notes.data.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes() : Flow<List<Note>>
    suspend fun getNoteById(id:Int):Note?
    suspend fun insertNote(note:Note)
    suspend fun deleteNote(note:Note)
}