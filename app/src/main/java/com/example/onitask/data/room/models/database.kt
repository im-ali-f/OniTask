package com.example.onitask.data.room.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Account::class , Task::class] , version = 1, exportSchema = false )
abstract class db:RoomDatabase(){
    abstract fun accDAO():accountDAO
    abstract fun taskDAO():taskDAO
    companion object{
        @Volatile
        private var INSTANCE :db?=null

        fun getInstance(context: Context):db{
            synchronized(this){
                var instance= INSTANCE
                if (instance == null){
                    instance= Room.databaseBuilder(context.applicationContext,db::class.java,"oniTaskDB").build()

                   INSTANCE= instance
                }
                return instance
            }

        }
    }
}
