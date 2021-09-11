package com.demirli.a41flashcards.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.demirli.a41flashcards.model.Question

@Database(entities = [Question::class], version = 1)
abstract class QuizDatabase: RoomDatabase() {
    abstract fun quizDao(): QuizDao

    companion object{
        @Volatile
        var INSTANCE: QuizDatabase? = null

        @Synchronized
        fun getInstance(context: Context): QuizDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz.db"
                    ).build()
            }

            return INSTANCE as QuizDatabase
        }

        private val roomDbCallback = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateAsyncTask(INSTANCE).execute()
            }
        }

        class PopulateAsyncTask(private val db: QuizDatabase?): AsyncTask<Void, Void, Void>(){
            private val dao: QuizDao? by lazy { db?.quizDao() }
            override fun doInBackground(vararg params: Void?): Void? {

                return null
            }
        }
    }
}