package com.example.saveus.classes

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.widget.Toast
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import android.app.Application
import android.icu.util.Calendar
import kotlin.time.milliseconds


class ListPlacesOfOneDay(var ParentItemTitle: Long, var ChildItemList: ArrayList<RecyclerviewChildItem>):Comparable<ListPlacesOfOneDay> {
    override fun compareTo(other: ListPlacesOfOneDay): Int {
        if(ParentItemTitle < other.ParentItemTitle){
            return 1
        }
         if(ParentItemTitle > other.ParentItemTitle){
             return  -1
         }
         else{
             return 0
         }
    }
    fun compareFromToDate(from:Long,to:Long):Int{
        val f =Date(from)
        val t = Date(to)
        val P = Date(ParentItemTitle)
        val format = SimpleDateFormat("dd.MM.yyyy")

        if(ParentItemTitle in from..to){
            return -1
        }
        return 1
    }

}
class RecyclerviewChildItem(  var location:Location, var startAt:Long, var sumTime:Long): Comparable<RecyclerviewChildItem>{
    lateinit var childItemTitleTimeFromTo:String
    lateinit var childItemTime:String
    var textLocation = ""
    init{
        conversionToText()

    }

    override fun compareTo(other: RecyclerviewChildItem): Int {

        if(startAt < other.startAt){
                return 1
        }
        if(startAt > other.startAt){
            return -1
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

            //conversion from location class to text address

            getAddress(MySuperAppApplication.context,location.latitude,location.longitude)?.let { textLocation= it }
            if (textLocation==null){
                textLocation="אין כתובת ספציפית"
            }

        }
    fun getAddress(context: Context?, lat: Double, lng: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            var add = "${obj.thoroughfare} ${obj.subThoroughfare} ${obj.locality}"

            add
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context,"אין כתובת", Toast.LENGTH_SHORT).show()
            null
        }
    }


}


class MyPlacesList(@JvmField private var list:ArrayList<ListPlacesOfOneDay> ) {

    fun getList() =list

    fun setNewPlaceInList(newPlace:RecyclerviewChildItem){
         val d = Date(newPlace.startAt)
        val c =Calendar.getInstance()
        c.set(SimpleDateFormat("yyyy").format(d).toInt(),(SimpleDateFormat("MM").format(d).toInt())-1,SimpleDateFormat("dd").format(d).toInt(),0,0,0)
      val b =(c.timeInMillis/1000)*1000
        val date = b //newPlace.startAt.minus((newPlace.startAt%(24*60*60*1000)))
        val theDay =list.find { it.ParentItemTitle==date}
        if (theDay==null){
          list.add(ListPlacesOfOneDay(date, ArrayList<RecyclerviewChildItem>() ))
            setNewPlaceInList(newPlace)
        }
        theDay?.ChildItemList?.add(newPlace)
        theDay?.ChildItemList?.sort()
        list.sort()
     }
    fun compareFromToDate(from: Long, to: Long){

     list.sort()
     list.sortBy{ it.compareFromToDate(from,to) }
    }
    fun compare(){
        list.sort()
    }

    }

 class ListViewModel : ViewModel() {
        val list = MyPlacesList(ArrayList<ListPlacesOfOneDay>())
        var adapterNotifyDataSetChanged: (() -> Unit)? =null
        }


class MySuperAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: Application? = null
        val context: Context
            get() = instance!!.applicationContext
    }
}
