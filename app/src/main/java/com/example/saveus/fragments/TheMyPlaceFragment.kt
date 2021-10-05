package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.saveus.R
import java.util.*

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


        val fromDate= view.findViewById<TextView>(R.id.fromDate)
        val toDate= view.findViewById<TextView>(R.id.toDate)


        val calendarToDate = calendar(toDate,view.context,null)
        val calendarFromDate = calendar(fromDate,view.context,calendarToDate)

//        val dpd = DatePickerDialog(view.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//
//            // Display Selected date in textbox
//            fromDate.setText("" + dayOfMonth + "." + monthOfYear + "." + year)
//
//        }, year, month, day)

        fromDate.setOnClickListener {
        calendarFromDate.show()
        }
        toDate.setOnClickListener {
            calendarToDate.show()
        }




        return view

    }
   private fun calendar(view:TextView, context:Context, dpdToDate:DatePickerDialog?):DatePickerDialog{
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
            if(dpdToDate!=null){
            c.set(yearIs,monthOfYear,dayOfMonth)
            dpdToDate.datePicker.minDate = c.timeInMillis
            dpdToDate.datePicker.updateDate(yearIs,monthOfYear,dayOfMonth)
            dpdToDate?.show()
            }

        }, year, month, day)
       dpd.datePicker.maxDate=c.timeInMillis
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