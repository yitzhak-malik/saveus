package com.example.saveus.classes


import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saveus.R






class RecyclerviewChildAdapter (private val childItemList: List<RecyclerviewChildItem>) :
    RecyclerView.Adapter<RecyclerviewChildAdapter.ChildViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerviewChildAdapter.ChildViewHolder {
        // Here we inflate the corresponding
        // layout of the child item
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_child_item,
                parent, false
            )
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerviewChildAdapter.ChildViewHolder, position: Int) {

        holder.childViewHolder(childItemList[position])
        // Create an instance of the ChildItem
//        // class for the given position
//        val childItem = childItemList[position]
////
////        // For the created instance, set title.
////        // No need to set the image for
////        // the ImageViews because we have
////        // provided the source for the images
////        // in the layout file itself
//        holder.ChildItemTitle
//            .setText(childItem.ChildItemTitle)
    }

    override fun getItemCount(): Int {
                // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return childItemList.size
    }


    // This class is to initialize
    // the Views present
    // in the child RecyclerView
   inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun childViewHolder(item:RecyclerviewChildItem) {
           val childItemTitle = itemView.findViewById<TextView>(R.id.child_item_title)
           val childItemTime = itemView.findViewById<TextView>(R.id.child_item_time)
           val location = itemView.findViewById<TextView>(R.id.location)

           childItemTitle.text = item.childItemTitleTimeFromTo
           location.text = item.textLocation
           childItemTime.text = item.childItemTime

        }
    }


}