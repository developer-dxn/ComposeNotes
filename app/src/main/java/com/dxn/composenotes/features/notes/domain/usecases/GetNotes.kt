package com.dxn.composenotes.features.notes.domain.usecases

import com.dxn.composenotes.features.notes.data.models.Note
import com.dxn.composenotes.features.notes.domain.repositories.NoteRepository
import com.dxn.composenotes.features.notes.domain.utils.NotesOrder
import com.dxn.composenotes.features.notes.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {
    operator fun invoke(notesOrder: NotesOrder = NotesOrder.Date(OrderType.Descending)): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (notesOrder.orderType) {
                is OrderType.Ascending -> {
                    when (notesOrder) {
                        is NotesOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NotesOrder.Date -> notes.sortedBy { it.timestamp }
                        is NotesOrder.Color -> notes.sortedBy { it.color }

                    }
                }
                else -> {
                    when (notesOrder) {
                        is NotesOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NotesOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NotesOrder.Color -> notes.sortedByDescending { it.color }
                    }

                }
            }
        }
    }
}