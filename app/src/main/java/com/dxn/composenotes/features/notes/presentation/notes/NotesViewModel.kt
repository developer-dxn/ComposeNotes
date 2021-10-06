package com.dxn.composenotes.features.notes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.composenotes.features.notes.data.models.Note
import com.dxn.composenotes.features.notes.domain.usecases.NotesUseCases
import com.dxn.composenotes.features.notes.domain.utils.NotesOrder
import com.dxn.composenotes.features.notes.domain.utils.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel
@Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    private val state : State<NotesState> = _state
    private  var recentlyDeletedNote : Note? = null
    private var getNotesJob : Job? = null

    init {
        getNotes(NotesOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                val isOrderTypeSame = state.value.notesOrder::class == event.notesOrder::class
                val isOrderSame = state.value.notesOrder.orderType::class == event.notesOrder.orderType::class
                if(!(isOrderTypeSame && isOrderSame)) {
                    getNotes(event.notesOrder)
                }
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCases.addNote(recentlyDeletedNote?:return@launch)
                    recentlyDeletedNote=null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
        }
    }

    fun getNotes(notesOrder: NotesOrder) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotes(notesOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    notesOrder = notesOrder
                )
            }
            .launchIn(viewModelScope)
    }

}


data class NotesState(
    val notes: List<Note> = emptyList(),
    val notesOrder: NotesOrder = NotesOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)

sealed class NotesEvent {
    data class Order(val notesOrder: NotesOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}