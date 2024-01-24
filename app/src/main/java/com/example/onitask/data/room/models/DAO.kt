package com.example.onitask.data.room.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface accountDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(account: Account)

    @Query("select * FROM account WHERE username=:userName")
    fun userExistance(userName: String):Flow<List<Account>>

    @Query("select * FROM account WHERE username =:userName AND password =:userPass")
    fun getUser(userName:String,userPass:String): Flow<List<Account>>
}