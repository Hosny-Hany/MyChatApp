package com.route.mychatapp.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.route.mychatapp.model.User

object  usersDao{
    fun getusercollection(): CollectionReference {
        val database = Firebase.firestore
        return database.collection("Users")
    }
    fun createUser(user: User , onCompleteListener: OnCompleteListener<Void>){
        val docRef = getusercollection()
            .document(user.id?:"")
        docRef.set(user)
            .addOnCompleteListener(onCompleteListener)
    }

    fun getUser(uid:String?,onCompleteListener: OnCompleteListener<DocumentSnapshot>) {
        getusercollection()
            .document(uid?:"")
            .get()
            .addOnCompleteListener(onCompleteListener)
    }
}