package com.example.onitask.data.room.models

class repo(val db: db) {
    suspend fun add1(account: Account){
        db.accDAO().createUser(account)
        db.accDAO().getUser(0,"")
    }
}