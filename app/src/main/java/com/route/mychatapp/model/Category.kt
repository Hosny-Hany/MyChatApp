package com.route.mychatapp.model

import com.route.mychatapp.R

data class Category (
    val id : Int,
    val name : String ,
    val imageResId:Int,
        ){
    companion object{
        fun categories()= listOf<Category>(
            Category(1,"Sport", R.drawable.sports),
            Category(2,"Music",R.drawable.music),
            Category(3,"Movies",R.drawable.movies)
        )
    }
}