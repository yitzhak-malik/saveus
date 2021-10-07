package com.example.saveus.classes

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class RecyclerviewParentItem(var ParentItemTitle: Long, var ChildItemList: ArrayList<RecyclerviewChildItem>):Comparable<RecyclerviewParentItem> {
    override fun compareTo(other: RecyclerviewParentItem): Int {
        if(ParentItemTitle < other.ParentItemTitle){
            return -1
        }
         if(ParentItemTitle > other.ParentItemTitle){
             return  1
         }
         else{
             return 0
         }
    }

}
class RecyclerviewChildItem(  var location:String, val startAt:Long, val sumTime:Long):Comparable<RecyclerviewChildItem>{
    lateinit var childItemTitleTimeFromTo:String
    lateinit var childItemTime:String
    init{
        conversionToText()
    }
    override fun compareTo(other: RecyclerviewChildItem): Int {

        if(startAt < other.startAt){
                return -1
        }
        if(startAt < other.startAt){
            return 1
        }
        else{
            return 0
        }

    }
        fun conversionToText(){
          val untilAt = Date(startAt+sumTime)
          val startAt = Date(startAt)
          val format = SimpleDateFormat("HH:mm")
          childItemTitleTimeFromTo ="${format.format(startAt)} - ${format.format(untilAt)}"

            //conversion from milliseconds for sum time 00:00:00
           val format2 = SimpleDateFormat("HH:mm:ss")
           format2.timeZone= TimeZone.getTimeZone("GMT+0")
           childItemTime=format2.format(sumTime)
        }


}


class MyPlacesList( private val list:MutableList<RecyclerviewParentItem>) {


    fun setNewPlaceInList(newPlace:RecyclerviewChildItem){
        val date = newPlace.startAt?.minus((newPlace.startAt?.rem(24*60*60*1000)!!))
        val theDay =list.find { it.ParentItemTitle==date}
        if (theDay==null){
          list.add(RecyclerviewParentItem(date, ArrayList<RecyclerviewChildItem>() ))
          setNewPlaceInList(newPlace)
        }
        theDay?.ChildItemList?.add(newPlace)
        theDay?.ChildItemList?.sort()
        list.sort()
     }
    }

    class ListViewModel : ViewModel() {
        val list=ArrayList<RecyclerviewParentItem>()

        }




