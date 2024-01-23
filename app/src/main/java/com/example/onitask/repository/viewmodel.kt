package com.example.onitask.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onitask.data.room.models.Account
import com.example.onitask.data.room.models.repo
import kotlinx.coroutines.launch

class viewmodel(val repo: repo):ViewModel() {
    fun userManage(data:Account){
        viewModelScope.launch{
            repo.accManager(data)
        }
    }
}