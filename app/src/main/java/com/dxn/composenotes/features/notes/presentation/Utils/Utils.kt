package com.dxn.composenotes.features.notes.presentation.Utils

sealed class Screen(val route:String) {
    object Notes : Screen("notes_screen")
    object AddAndEditNote : Screen("add_edit_note_screen")
}