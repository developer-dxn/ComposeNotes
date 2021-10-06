package com.dxn.composenotes.dependency

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dxn.composenotes.features.notes.data.repositories.NoteRepositoryImpl
import com.dxn.composenotes.features.notes.data.source.NoteDatabase
import com.dxn.composenotes.features.notes.domain.repositories.NoteRepository
import com.dxn.composenotes.features.notes.domain.usecases.AddNote
import com.dxn.composenotes.features.notes.domain.usecases.DeleteNote
import com.dxn.composenotes.features.notes.domain.usecases.GetNotes
import com.dxn.composenotes.features.notes.domain.usecases.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDataBase(application: Application) : NoteDatabase =
        Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME)
            .build()


    @Provides
    @Singleton
    fun provideNoteRepository(db:NoteDatabase):NoteRepository = NoteRepositoryImpl(db.noteDao)

    @Provides
    @Singleton
    fun provideNotesUseCases(repository: NoteRepository) = NotesUseCases(
        getNotes = GetNotes(repository),
        deleteNote = DeleteNote(repository),
        addNote = AddNote(repository)
    )

}