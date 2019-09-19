package com.neel.matrixapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*

class MainActivity : AppCompatActivity() {

    private val itemList: Array<String>
        get() = arrayOf(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5",
            "Item 6",
            "Item 7",
            "Item 8",
            "Item 9",
            "Item 10",
            "Item 11",
            "Item 12",
            "Item 13",
            "Item 14",
            "Item 15",
            "Item 16",
            "Item 17",
            "Item 18",
            "Item 19",
            "Item 20",
            "Item 21",
            "Item 22"
        )

    var row_int_global: Int = 5;
    var column_int_global: Int = 5;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val generate_matrx_btn = findViewById(R.id.generate_matrx_btn) as Button
        val generate_randomnumber_btn = findViewById(R.id.generate_randomnumber_btn) as Button
        val columns_edt = findViewById(R.id.columns_edt) as EditText
        val rows_edt = findViewById(R.id.rows_edt) as EditText
        val gridview = findViewById(R.id.gridview) as GridView


        var lastgeneratedList: ArrayList<Int> = getNumbersList(column_int_global, row_int_global)
        //set default 5X5 matrix
        gridview.numColumns = 5;
        val adapter = GridViewAdapter(this, lastgeneratedList)
        gridview.adapter = adapter


        gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
            // Write code to perform action when item is clicked.
            Toast.makeText(
                this@MainActivity, " Clicked Position: " + (position + 1),
                Toast.LENGTH_SHORT
            ).show()
        }


        generate_randomnumber_btn.setOnClickListener {

            val getrandomPos = (0..row_int_global * column_int_global).random()
            val getrandomNumber = lastgeneratedList.get(getrandomPos)



        }


        generate_matrx_btn.setOnClickListener {
            // your code to perform when the user clicks on the button
            val column_str: String = columns_edt.text.toString().trim();
            val row_str: String = rows_edt.text.toString().trim();

            try {
                //Validate for emptry imputs
                if (TextUtils.isEmpty(row_str)) {
                    rows_edt.requestFocus()
                    rows_edt.error = "Please enter value."
                } else if (TextUtils.isEmpty(column_str)) {
                    columns_edt.requestFocus()
                    columns_edt.error = "Please enter value."
                } else {
                    row_int_global = row_str.toInt()
                    column_int_global = column_str.toInt()
                    if (validateDetails(row_int_global, column_int_global, rows_edt, columns_edt)) {

                        //SET GRIDVIEW ADAPTER

                        lastgeneratedList = getNumbersList(column_int_global, row_int_global);
                        // Get an instance of base adapter
                        gridview.numColumns = column_int_global;
                        val adapter = GridViewAdapter(this, lastgeneratedList)
                        gridview.adapter = adapter

                    }
                }
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace();
            }
        }
    }


    fun validateDetails(row_int: Int, column_int: Int, row_edt: EditText, col_edt: EditText): Boolean {

        if (row_int == 0) {
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

    // Custom method to generate list of color name value pair
    private fun getNumbersList(columns: Int, row: Int): ArrayList<Int> {
        /*return listOf(
            Pair("INDIANRED", Color.parseColor("#CD5C5C")),
            Pair("LIGHTCORAL", Color.parseColor("#F08080")),
            Pair("SALMON", Color.parseColor("#FA8072")),
            Pair("DARKSALMON", Color.parseColor("#E9967A")),
            Pair("LIGHTSALMON", Color.parseColor("#FFA07A")),
            Pair("CRIMSON", Color.parseColor("#DC143C")),
            Pair("RED", Color.parseColor("#FF0000")),
            Pair("FIREBRICK", Color.parseColor("#B22222")),
            Pair("DARKRED", Color.parseColor("#8B0000")),

            Pair("PINK", Color.parseColor("#FFC0CB")),
            Pair("LIGHTPINK", Color.parseColor("#FFB6C1")),
            Pair("HOTPINK", Color.parseColor("#FF69B4")),
            Pair("DEEPPINK", Color.parseColor("#FF1493")),
            Pair("MEDIUMVIOLETRED", Color.parseColor("#C71585")),
            Pair("PALEVIOLETRED", Color.parseColor("#DB7093"))
        )*/
        /*var hashMap: LinkedHashMap<Int, String> = LinkedHashMap<Int, String>() //define empty hashmap
        var total_count = columns * row;

        for (i in 0..total_count) {
            println(i) // 0,1,2,3,4,5   --> upto 5
            hashMap = generateHashmap(total_count, hashMap)
        }
        return hashMap;*/

        var list: ArrayList<Int> = ArrayList()//define empty hashmap
        var total_count = columns * row;

        for (i in 0..total_count - 1) {
            println(i) // 0,1,2,3,4,5   --> upto 5
            list = generateList(total_count, list)
        }
        return list;


    }

    /* private fun generateHashmap(total_count: Int, hashMap: LinkedHashMap<Int, String>): LinkedHashMap<Int, String> {
         var generaterandom = (0..total_count).random();
         var insertedcheck = hashMap.get(generaterandom);

         if (TextUtils.isEmpty(insertedcheck)) {
             hashMap.put(generaterandom, "DEFAULT");
         } else {
             generateHashmap(total_count, hashMap)
         }
         return hashMap;
     }*/

    private fun generateList(total_count: Int, arrayListtemp: ArrayList<Int>): ArrayList<Int> {
        var generaterandom = (0..total_count).random();
        var insertedcheck = arrayListtemp.contains(generaterandom);

        if (!insertedcheck) {
            arrayListtemp.add(generaterandom);
        } else {
            generateList(total_count, arrayListtemp)
        }
        return arrayListtemp;
    }


}

/*https://www.google.com/search?sxsrf=ACYBGNQ7uQbAT-A1uk5MSky88DxHI9irpQ%3A1568868334883&ei=7geDXYXENYXfz7sP9_GmoAg&q=generate+a+matrix+in+java&oq=generate+a+matrix+in+java&gs_l=psy-ab.3..0i5i30j0i8i30l2.44227.44227..44768...0.2..0.300.300.3-1......0....1..gws-wiz.......0i71.1Zb5yjWoAAc&ved=0ahUKEwiF87L-idzkAhWF73MBHfe4CYQQ4dUDCAs&uact=5
https://www.java-programs.thiyagaraaj.com/create_matrix_java_example_program.html
https://stackoverflow.com/questions/45339504/android-dynamically-create-matrix-of-edittext-based-on-user-input
https://stackoverflow.com/questions/43843855/how-to-create-a-33-matrix-grid-in-android*/

