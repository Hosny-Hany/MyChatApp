package com.route.mychatapp.model

data class User (
    val id:String?=null,
    val UserName:String?=null,
    val Email:String?=null
        ){
    companion object{
        const val collectionName = "Users"
    }

}
