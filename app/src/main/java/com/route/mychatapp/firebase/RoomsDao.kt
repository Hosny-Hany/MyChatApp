package com.route.mychatapp.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.route.mychatapp.model.Room

object RoomsDao {
    fun getRoomscollection(): CollectionReference {
        val database = Firebase.firestore
        return database.collection("rooms")
    }
    fun createRoom(
        title:String,
        desc:String,
        ownerId:String,
        categoryId:Int,
    onCompleteListener: OnCompleteListener<Void>
    ){
        val collection = getRoomscollection()
        val DocRef =collection.document()
        val room = Room(
            id = DocRef.id,
            title = title,
            description = desc,
            OwnerId = ownerId,
            categoryId = categoryId
        )
        DocRef.set(room)
            .addOnCompleteListener(onCompleteListener)
    }
}