package com.example.onitask.data.room.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

@Dao
interface taskDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTask(task: Task)

    @Query("select * FROM task WHERE userIdFk=:userId")
    fun getAllTasks(userId:Int):Flow<List<Task>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("select * FROM task WHERE task_id=:taskId")
    fun getTask(taskId:Int):Flow<List<Task>>
}