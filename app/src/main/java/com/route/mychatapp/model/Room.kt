package com.route.mychatapp.model

data class Room(
    val id:String?=null,
    val title:String?=null,
    val description:String?=null,
    val categoryId: Int?=null,
    val OwnerId : String?=null
){
    companion object{
        const val collectionName = "rooms"
    }

}