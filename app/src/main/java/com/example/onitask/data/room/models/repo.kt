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
    fun getAllTasks(userId:Int)=db.taskDAO().getAllTasks(userId)

    suspend fun updateTask(task: Task){
        db.taskDAO().updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        db.taskDAO().deleteTask(task)
    }

    fun getTask(id:Int)=db.taskDAO().getTask(id)

    fun getTaskTime(time:String,date:String,userId: Int)=db.taskDAO().getTaskByTime(time,date,userId)
}