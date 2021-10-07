package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saveus.R
import com.example.saveus.classes.*
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TheMyPlaceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TheMyPlaceFragment : Fragment() {

private lateinit var textToDate:TextView
private lateinit var calendarToDate:DatePickerDialog
    private val viewModel: ListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_the_my_place, container, false)
        val list = viewModel.list

        val fromDate = view.findViewById<LinearLayout>(R.id.fromDate)
        val toDate = view.findViewById<LinearLayout>(R.id.toDate)
        val textFromDate = view.findViewById<TextView>(R.id.textFromDate)
        textToDate = view.findViewById(R.id.textToDate)


        calendarToDate = calendar(textToDate, view.context, false)
        val calendarFromDate = calendar(textFromDate, view.context, true)



        fromDate.setOnClickListener {
            calendarFromDate.show()
        }
        toDate.setOnClickListener {
            calendarToDate.show()
        }
        val recyclerview = view.findViewById<RecyclerView>(R.id.parent_recyclerview)
        val layoutManager = LinearLayoutManager(view.context)
        //val adapter = ParentItemList()?.let { RecycleviewParentAdapter(it) }
        val adapter = list?.let { RecycleviewParentAdapter(it) }
        recyclerview.adapter = adapter
        recyclerview.layoutManager = layoutManager


        ParentItemList()?.let { MyPlacesList(it) }?.setNewPlaceInList(
            RecyclerviewChildItem(
                "בלוך 52 קריית יערים",
                55555555,
                66666666666
            )
        )


        return view
    }
   private fun calendar(view:TextView, context:Context,isFirstCalendar:Boolean):DatePickerDialog{
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
       // text default
        view.setText("" + day + "." + (month+1) + "." + year)

        val dpd = DatePickerDialog(context,  { p, yearIs, monthOfYear, dayOfMonth ->


            // Display Selected date in textbox
         view.setText("" + dayOfMonth + "." + (monthOfYear+1) + "." + yearIs)

            // show dialog to date
            if(isFirstCalendar){
                this.calendarToDate = calendar(textToDate,context,false)
            c.set(yearIs,monthOfYear,dayOfMonth)
                this.calendarToDate.datePicker.minDate = c.timeInMillis
                this.calendarToDate.datePicker.updateDate(yearIs,monthOfYear,dayOfMonth)
                this.calendarToDate.show()
            }

        }, year, month, day)
       dpd.datePicker.maxDate = c.timeInMillis
       if (!isFirstCalendar){
           dpd.datePicker.minDate = c.timeInMillis

       }

        return dpd
    }
    private fun ParentItemList(): ArrayList<RecyclerviewParentItem>? {
        val itemList: ArrayList<RecyclerviewParentItem> = ArrayList()
        val item = RecyclerviewParentItem(
            555555555,
            ChildItemList()
        )
        itemList.add(item)
        val item1 = RecyclerviewParentItem(
            55555555,
            ChildItemList()
        )
        itemList.add(item1)
        val item2 = RecyclerviewParentItem(
            55555555,
            ChildItemList()
        )
        itemList.add(item2)
        val item3 = RecyclerviewParentItem(
            55555555,
            ChildItemList()
        )
        itemList.add(item3)
        return itemList
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private fun ChildItemList():ArrayList<RecyclerviewChildItem> {
        val ChildItemList: ArrayList<RecyclerviewChildItem> = ArrayList()
        ChildItemList.add(RecyclerviewChildItem("בלוך 52 קריית יערים",55555554353,55551554353))
        ChildItemList.add(RecyclerviewChildItem("בלוך 525 קריית יערים",5555545345345,5555545315345))
        ChildItemList.add(RecyclerviewChildItem("בלוך 2 קריית יערים",555534561456,555514561456))
        ChildItemList.add(RecyclerviewChildItem("בלוך 32 קריית יערים",5555345345,5456446664))
        return ChildItemList
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            TheMyPlaceFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}