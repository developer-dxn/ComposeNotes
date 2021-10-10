package com.dxn.composenotes.features.notes.presentation.screens.addnotes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.composenotes.features.notes.data.models.InvalidNoteException
import com.dxn.composenotes.features.notes.data.models.Note
import com.dxn.composenotes.features.notes.domain.usecases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAndEditNoteViewModel
@Inject
constructor(
    private val notesUseCases: NotesUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter a title.."))
    val noteTitle = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter some content"))
    val noteContent = _noteContent

    private val _noteColor = mutableStateOf(Note.colors.random().toArgb())
    val noteColor = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    notesUseCases.getNote(noteId) ?. also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = _noteTitle.value.copy(text = note.title,isHintVisible = false)
                        _noteContent.value = _noteContent.value.copy(text = note.content, isHintVisible = false)
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddAndEditNoteEvent) {
        when (event) {
            is AddAndEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = _noteTitle.value.copy(text = event.value)
            }
            is AddAndEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(text = event.value)
            }
            is AddAndEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value =
                    _noteTitle.value.copy(isHintVisible = !event.focusState.isFocused && _noteTitle.value.text.isBlank())
            }
            is AddAndEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value =
                    _noteContent.value.copy(isHintVisible = !event.focusState.isFocused && _noteTitle.value.text.isBlank())
            }
            is AddAndEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddAndEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        notesUseCases.addNote(
                            Note(
                                noteTitle.value.text,
                                noteContent.value.text,
                                System.currentTimeMillis(),
                                noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: ""
                            )
                        )
                    }
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }

}


data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = false
)

sealed class AddAndEditNoteEvent {
    data class EnteredTitle(val value: String) : AddAndEditNoteEvent()
    data class EnteredContent(val value: String) : AddAndEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddAndEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddAndEditNoteEvent()
    data class ChangeColor(val color: Int) : AddAndEditNoteEvent()
    object SaveNote : AddAndEditNoteEvent()
}