package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saveus.R
import com.example.saveus.classes.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

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
private var fromDate = System.currentTimeMillis()
private var toDate = System.currentTimeMillis()
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
        val viewButton =view.findViewById<TextView>(R.id.button)
        textToDate = view.findViewById(R.id.textToDate)


        calendarToDate = calendar(textToDate, view.context, false)
        val calendarFromDate = calendar(textFromDate, view.context, true)


        val recyclerview = view.findViewById<RecyclerView>(R.id.parent_recyclerview)
        val layoutManager = LinearLayoutManager(view.context)
        val adapter = list.getList()?.let { RecycleviewParentAdapter(it) }
        recyclerview.adapter = adapter
        recyclerview.layoutManager = layoutManager
        viewModel.adapterNotifyDataSetChanged = { adapter.notifyDataSetChanged() }

        fromDate.setOnClickListener {
            calendarFromDate.show()
        }
        toDate.setOnClickListener {
            calendarToDate.show()
        }
        viewButton.setOnClickListener{
            list.compareFromToDate(this.fromDate,this.toDate)
            viewModel.adapterNotifyDataSetChanged?.let { it1 -> it1() }
        }




        return view
    }
   private fun calendar(view:TextView, context:Context,isFirstCalendar:Boolean):DatePickerDialog{
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(context,  { p, yearIs, monthOfYear, dayOfMonth ->


            // Display Selected date in textbox
         view.setText("" + dayOfMonth + "." + (monthOfYear+1) + "." + yearIs)

            // show dialog to date
            if(isFirstCalendar){
                this.calendarToDate = calendar(textToDate,context,false)
            c.set(yearIs,monthOfYear,dayOfMonth,0,0,0)

                this.fromDate = c.timeInMillis/1000*1000
                this.calendarToDate.datePicker.minDate = this.fromDate
                this.calendarToDate.datePicker.updateDate(yearIs,monthOfYear,dayOfMonth)
                this.calendarToDate.show()
            }
            else{
                c.set(yearIs,monthOfYear,dayOfMonth,0,0,0)
             this.toDate = c.timeInMillis/1000*1000
            }
        }, year, month, day)
       dpd.datePicker.maxDate = c.timeInMillis
       if (isFirstCalendar){
       // text default
        view.setText(SimpleDateFormat("dd.MM.yyyy").format(this.fromDate))
       }else{
           view.setText(SimpleDateFormat("dd.MM.yyyy").format(this.toDate))
           dpd.datePicker.minDate = this.fromDate
       }

        return dpd
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