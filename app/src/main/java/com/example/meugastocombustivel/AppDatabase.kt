package com.example.meugastocombustivel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.meugastocombustivel.model.Abastecimento
import com.example.meugastocombustivel.model.AbastecimentoDao

@Database(entities = [Abastecimento::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun abastecimentoDao(): AbastecimentoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "abastecimento_db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}
