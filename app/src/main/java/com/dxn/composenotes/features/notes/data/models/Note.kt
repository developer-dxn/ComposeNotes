package com.dxn.composenotes.features.notes.data.models

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.room.PrimaryKey
import com.dxn.composenotes.ui.theme.*
import java.lang.Exception

data class Note(
    val title:String,
    val content:String,
    val timestamp: Long,
    val color:Int
) {
    @PrimaryKey(autoGenerate = true) val id:Int =0
    companion object {
        val colors  = listOf(
           RedOrange, LightGreen, Violet, BabyBlue, RedPink
        )
    }
}


class InvalidNoteException (message:String) : Exception(message)