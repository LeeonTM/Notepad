package com.LeonTimmerman.notepad.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.LeonTimmerman.notepad.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotepadRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "NOTEPAD_DATABASE"

        @Volatile
        private var instance: NotepadRoomDatabase? = null

        fun getDatabase(context: Context): NotepadRoomDatabase? {
            if (instance == null) {
                synchronized(NotepadRoomDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            NotepadRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    instance?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.noteDao().insertNote(Note("Title", Date(), ""))
                                        }
                                    }
                                }
                            })
                            .build()
                    }
                }
            }
            return instance
        }
    }
}