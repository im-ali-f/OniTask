package com.example.onitask.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onitask.data.room.models.Account
import com.example.onitask.data.room.models.Task
import com.example.onitask.data.room.models.repo
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class viewmodel(val repo: repo):ViewModel() {
    fun createAcc(data:Account){
        viewModelScope.launch{
            repo.createAcc(data)
        }
    }
    fun userExsitanceFromView(userName:String):Flow<List<Account>>{
        return repo.userExistance(userName)
    }

    fun getUser(userName: String,password:String):Flow<List<Account>>{
        return  repo.getUser(userName,password)
    }

    fun createTask(data:Task){
        viewModelScope.launch {
            repo.createTask(data)
        }
    }

    fun getAllTasks(id:Int):Flow<List<Task>>{
       return repo.getAllTasks(id)
    }

    fun updateTask(data: Task){
        viewModelScope.launch {
            repo.updateTask(data)
        }
    }

    fun deleteTask(data: Task){
        viewModelScope.launch {
            repo.deleteTask(data)
        }
    }

    fun getspecificTask(id:Int):Flow<List<Task>>{
        return repo.getTask(id)
    }

    fun getTaskByTime(time:String,date:String,userId: Int):Flow<List<Task>>{
        return  repo.getTaskTime(time,date,userId)
    }

}