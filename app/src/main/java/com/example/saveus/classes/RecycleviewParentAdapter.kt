package com.example.saveus.classes

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saveus.R
import java.util.*

class RecycleviewParentAdapter(val parentItemList : List<ListPlacesOfOneDay>):
RecyclerView.Adapter<RecycleviewParentAdapter.ParentViewHolder>(){

    val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecycleviewParentAdapter.ParentViewHolder {
      val view =LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_parent_item,parent,false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecycleviewParentAdapter.ParentViewHolder,
        position: Int
    ) {
        val parentItem =parentItemList[position]

        val date =Date(parentItem.ParentItemTitle)
        val format = SimpleDateFormat("dd.MM.yyyy")

        holder.parentItemTitle.setText(format.format(date) )
        holder.cardParentRecycelview.setOnClickListener {
            if( holder.childRecyclerView.visibility == View.VISIBLE){
                holder.childRecyclerView.visibility = View.GONE
            }else{
                holder.childRecyclerView.visibility = View.VISIBLE
            }
        }

        if(position < 2){
            holder.childRecyclerView.visibility = View.VISIBLE
        }else{
            holder.childRecyclerView.visibility = View.GONE
        }

        val layoutManager = LinearLayoutManager(holder.childRecyclerView.context,LinearLayoutManager.VERTICAL,false)
        layoutManager.initialPrefetchItemCount= parentItem.ChildItemList!!.size

        val childItemAdapter = RecyclerviewChildAdapter(parentItem.ChildItemList!!)
        holder.childRecyclerView.layoutManager=layoutManager
        holder.childRecyclerView.adapter=childItemAdapter
        holder.childRecyclerView.setRecycledViewPool(viewPool)



    }

    override fun getItemCount(): Int {
      return parentItemList.size
    }
    inner class ParentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

         val cardParentRecycelview = itemView.findViewById<LinearLayout>(R.id.card_parent_recycelview)
         val parentItemTitle = itemView.findViewById<TextView>(R.id.parent_item_title)
         val childRecyclerView = itemView.findViewById<RecyclerView>(R.id.child_recyclerview)
    }



}