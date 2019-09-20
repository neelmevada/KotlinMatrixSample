package com.neel.matrixapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var row_int_global: Int = 5
    var column_int_global: Int = 5
    var getrandomPos: Int = 0
    var getrandomNumber: Int = 0


    val sharedPrefName = "MatrixPracticle"
    val SELECTED_LIST = "SelectedList"
    val COLUMNS_COUNT = "ColumnsCount"
    val ROWS_COUNT = "RownCount"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val generate_matrx_btn = findViewById<Button>(R.id.generate_matrx_btn)
        val generate_randomnumber_btn = findViewById<Button>(R.id.generate_randomnumber_btn)
        val columns_edt = findViewById<EditText>(R.id.columns_edt)
        val rows_edt = findViewById<EditText>(R.id.rows_edt)
        val gridview = findViewById<GridView>(R.id.gridview)


        //check for already saved matrix
        var lastgeneratedList: ArrayList<GridItem> = ArrayList()//define empty hashmap
        if (getFromSharedpreference() == null) {
            lastgeneratedList = getNumbersList(column_int_global, row_int_global)
            savetoSharedPreference(lastgeneratedList)
        } else {
            lastgeneratedList = getFromSharedpreference() as ArrayList<GridItem>
        }

        //Check for already enereed coluns & rows
        if (getIntData(ROWS_COUNT) != -1)
            row_int_global = getIntData(ROWS_COUNT)

        if (getIntData(COLUMNS_COUNT) != -1)
            column_int_global = getIntData(COLUMNS_COUNT)

        columns_edt.setText(column_int_global.toString())
        rows_edt.setText(row_int_global.toString())

        gridview.numColumns = column_int_global
        var adapter = GridViewAdapter(this, this@MainActivity, lastgeneratedList)
        gridview.adapter = adapter

        gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
            Toast.makeText(
                this@MainActivity, " Clicked Position: " + (position + 1),
                Toast.LENGTH_SHORT
            ).show()
        }

        //Generate random number click & change bg color
        generate_randomnumber_btn.setOnClickListener {
            getrandomPos = getRandomNonRepeatedPos(lastgeneratedList)
            getrandomNumber = lastgeneratedList.get(getrandomPos).number
            lastgeneratedList.get(getrandomPos).color = getRandomColorCode()
            adapter.notifyDataSetChanged()
            savetoSharedPreference(lastgeneratedList)
        }

        //Generate matrix button click
        generate_matrx_btn.setOnClickListener {
            try {
                //Validations
                if (validateDetails(row_int_global, column_int_global, rows_edt, columns_edt)) {
                    val column_str: String = columns_edt.text.toString().trim()
                    val row_str: String = rows_edt.text.toString().trim()
                    row_int_global = row_str.toInt()
                    column_int_global = column_str.toInt()
                    saveEnteredData(column_int_global, row_int_global)
                    //SET GRIDVIEW ADAPTER
                    lastgeneratedList = getNumbersList(column_int_global, row_int_global)
                    savetoSharedPreference(lastgeneratedList)

                    gridview.numColumns = column_int_global
                    adapter = GridViewAdapter(this, this@MainActivity, lastgeneratedList)
                    gridview.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
        }
    }


    private fun getRandomNonRepeatedPos(lastgeneratedList: ArrayList<GridItem>): Int {
        val rnd = (1..(row_int_global * column_int_global) - 1).random()
        if (lastgeneratedList.get(rnd).color != 0) {
            getRandomNonRepeatedPos(lastgeneratedList)
        }
        return rnd
    }


    private fun getRandomColorCode(): Int {
        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        return color
    }

    fun validateDetails(row_int: Int, column_int: Int, row_edt: EditText, col_edt: EditText): Boolean {
        val column_str: String = col_edt.text.toString().trim()
        val row_str: String = row_edt.text.toString().trim()

        if (TextUtils.isEmpty(row_str)) {
            row_edt.requestFocus()
            row_edt.error = "Please enter value."
            return false
        } else if (TextUtils.isEmpty(column_str)) {
            col_edt.requestFocus()
            col_edt.error = "Please enter value."
            return false
        } else if (row_int == 0) {
            row_edt.requestFocus()
            row_edt.error = "Please enter value more than 0."
            return false
        } else if (column_int == 0) {
            col_edt.requestFocus()
            col_edt.error = "Please enter value more than 0."
            return false
        } else {
            return true
        }
    }

    private fun getNumbersList(columns: Int, row: Int): ArrayList<GridItem> {
        var list: ArrayList<GridItem> = ArrayList()//define empty hashmap
        var total_count = columns * row

        for (i in 1..total_count) {
            println(i) // 0,1,2,3,4,5   --> upto 5
            list = generateList(total_count, list)
        }
        return list
    }

    private fun generateList(total_count: Int, arrayListtemp: ArrayList<GridItem>): ArrayList<GridItem> {
        var generaterandom = (1..total_count).random()
        var insertedcheck = arrayListtemp.contains(GridItem(generaterandom, 0))

        if (!insertedcheck) {
            arrayListtemp.add(GridItem(generaterandom, 0))
        } else {
            generateList(total_count, arrayListtemp)
        }
        return arrayListtemp
    }

    private fun savetoSharedPreference(lastgeneratedList: ArrayList<GridItem>) {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        //editor.putString("SelectedList", listOf(lastgeneratedList).toString())
        //editor.putString("SelectedList", lastgeneratedList.joinToString(separator = "-"))
        //editor.putString("SelectedList", arrayListToArray(lastgeneratedList).joinToString(separator = "-"))
        var gson = Gson()
        var listAsString = gson.toJson(lastgeneratedList)
        editor.putString(SELECTED_LIST, listAsString)
        editor.apply()
        editor.commit()
    }

    private fun saveEnteredData(columns: Int, rows: Int) {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(COLUMNS_COUNT, columns)
        editor.putInt(ROWS_COUNT, rows)
        editor.apply()
        editor.commit()
    }

    private fun getIntData(key: String): Int {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val arrayInstring = sharedPreferences.getInt(key, -1)
        return arrayInstring
    }

    private fun getFromSharedpreference(): ArrayList<GridItem>? {
        var gson = Gson()
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val arrayInstring = sharedPreferences.getString(SELECTED_LIST, "")
        if (!TextUtils.isEmpty(arrayInstring)) {
            val objectList = gson.fromJson(arrayInstring, Array<GridItem>::class.java).asList()
            //return listOf(sss)

            var tempList: ArrayList<GridItem> = ArrayList()//define empty hashmap
            for (i in 0..objectList.size - 1) {
                tempList.add(GridItem(objectList[i].number, objectList[i].color))
            }
            return tempList
        } else {
            return null
        }
    }

}
