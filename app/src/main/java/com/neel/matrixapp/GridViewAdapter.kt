package com.neel.matrixapp

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class GridViewAdapter(
    private val mContext: Context,
    private val activty: AppCompatActivity,
    private val lists: ArrayList<GridItem>
) :
    BaseAdapter() {

    //private val list = colors()

    override fun getCount(): Int {
        return lists.size
    }

    override fun getItem(position: Int): Any? {
        return lists[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    // Create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.grid_item, null)
        val randomnumber_tv = view.findViewById(R.id.randomnumber_tv) as TextView
        randomnumber_tv.setText(lists[position].number.toString())

        if (lists[position].color != 0)
            view.setBackgroundColor(lists[position].color);
        /*randomnumber_tv.setOnClickListener {
            // Show selected color in a toast message
            Toast.makeText(
                parent.context,
                //"Clicked : ${lists[position].first}", Toast.LENGTH_SHORT
                "Clicked : ${lists[position]}", Toast.LENGTH_SHORT
            ).show()}*/
        //randomnumber_tv.setBackgroundColor(lists[position].second)
        //randomnumber_tv.setBackgroundColor(lists[position])

        /*// Get the activity reference from parent
         val activity = parent.context as Activity
         // Get the activity root view
         val viewGroup = activity.findViewById<ViewGroup>(android.R.id.content)
             .getChildAt(0)
         // Change the root layout background color
         viewGroup.setBackgroundColor(lists[position].second)*/

        return view
    }


}