package com.example.onitask.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity(tableName = "account")
data class Account(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val username:String,
    val password:String
)

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name="task_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val text:String,
    val date:String,
    val time:String,
    val completed:Boolean=false,
    val userIdFk:Int


)