package com.example.onitask.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onitask.data.room.models.Account
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

}