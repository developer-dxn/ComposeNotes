package com.dxn.composenotes.features.notes.domain.usecases

import com.dxn.composenotes.features.notes.data.models.Note
import com.dxn.composenotes.features.notes.domain.repositories.NoteRepository
import com.dxn.composenotes.features.notes.presentation.screens.notes.OrderBy
import com.dxn.composenotes.features.notes.presentation.screens.notes.OrderType

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {
    operator fun invoke(notesOrder: OrderBy = OrderBy.Date(OrderType.Descending)): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (notesOrder.orderType) {
                is OrderType.Ascending -> {
                    when (notesOrder) {
                        is OrderBy.Title -> notes.sortedBy { it.title.lowercase() }
                        is OrderBy.Date -> notes.sortedBy { it.timestamp }
                        is OrderBy.Color -> notes.sortedBy { it.color }

                    }
                }
                else -> {
                    when (notesOrder) {
                        is OrderBy.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is OrderBy.Date -> notes.sortedByDescending { it.timestamp }
                        is OrderBy.Color -> notes.sortedByDescending { it.color }
                    }

                }
            }
        }
    }
}