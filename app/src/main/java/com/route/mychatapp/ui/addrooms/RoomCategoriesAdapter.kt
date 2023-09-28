package com.route.mychatapp.ui.addrooms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.mychatapp.databinding.ItemRoomCategoryBinding
import com.route.mychatapp.model.Category
import java.text.FieldPosition

class RoomCategoriesAdapter (val Items : List<Category>): BaseAdapter(){
    override fun getCount(): Int {
        return Items.size
    }

    override fun getItem(possition: Int): Any {
       return Items[possition].id
    }

    override fun getItemId(position: Int): Long {
        return Items[position].id.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewHolder : ViewHolder
        if (view==null){
            //create viewHolder
            val itemBinding = ItemRoomCategoryBinding
                .inflate(LayoutInflater.from(parent?.context),parent,false)
            viewHolder = ViewHolder(itemBinding)
            itemBinding.root.tag = viewHolder
        }else{
            viewHolder = view.tag as ViewHolder
        }
        //bind
        viewHolder.bind(Items[position])
        return viewHolder.itembinding.root
    }
    class ViewHolder(val itembinding : ItemRoomCategoryBinding){
        fun bind(item:Category){
            itembinding.image.setImageResource(item.imageResId)
            itembinding.title.text = item.name
        }
    }
}