package com.dxn.composenotes.features.notes.data.models

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dxn.composenotes.ui.theme.*
import java.lang.Exception

@Entity
data class Note(
    val title:String,
    val content:String,
    val timestamp: Long,
    val color:Int
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int=1
    companion object {
        val colors  = listOf(
           RedOrange, LightGreen, Violet, BabyBlue, RedPink
        )
    }
}


class InvalidNoteException (message:String) : Exception(message)