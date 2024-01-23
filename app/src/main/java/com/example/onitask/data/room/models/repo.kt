package com.example.onitask.data.room.models

class repo(val db: db) {
    suspend fun accManager(account: Account){
        db.accDAO().createUser(account)
        db.accDAO().getUser(0,"")
    }
}