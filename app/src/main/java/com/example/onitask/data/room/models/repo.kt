package com.example.onitask.data.room.models

class repo(val db: db) {
    suspend fun createAcc(account: Account){
        db.accDAO().createUser(account)
    }
    fun userExistance(userName:String)= db.accDAO().userExistance(userName)

    fun getUser(userName: String,password:String)=db.accDAO().getUser(userName,password)

    suspend fun createTask(task: Task){
        db.taskDAO().createTask(task)
    }
}