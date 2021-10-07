package com.dxn.composenotes.features.notes.presentation.screens.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.composenotes.features.notes.data.models.Note
import com.dxn.composenotes.features.notes.domain.usecases.NotesUseCases
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
    val state : State<NotesState> = _state
    private  var recentlyDeletedNote : Note? = null
    private var getNotesJob : Job? = null

    init {
        getNotes(OrderBy.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                val isOrderTypeSame = state.value.orderBy::class == event.orderBy::class
                val isOrderSame = state.value.orderBy.orderType::class == event.orderBy.orderType::class
                if(!(isOrderTypeSame && isOrderSame)) {
                    getNotes(event.orderBy)
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

    fun getNotes(orderBy: OrderBy) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotes(orderBy)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    orderBy = orderBy
                )
            }
            .launchIn(viewModelScope)
    }

}



sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}

sealed class OrderBy(val orderType: OrderType) {
    class Title(orderType: OrderType) : OrderBy(orderType)
    class Date(orderType: OrderType) : OrderBy(orderType)
    class Color(orderType: OrderType) : OrderBy(orderType)
    fun copy(orderType: OrderType): OrderBy {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}

data class NotesState(
    val notes: List<Note> = emptyList(),
    val orderBy: OrderBy = OrderBy.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)

sealed class NotesEvent {
    data class Order(val orderBy: OrderBy) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}