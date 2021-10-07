package com.dxn.composenotes.features.notes.domain.usecases

data class NotesUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
)