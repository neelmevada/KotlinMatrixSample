package com.neel.matrixapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val generate_matrx_btn = findViewById(R.id.generate_matrx_btn) as Button
        val columns_edt = findViewById(R.id.columns_edt) as EditText
        val rows_edt = findViewById(R.id.rows_edt) as EditText


        generate_matrx_btn.setOnClickListener {
            // your code to perform when the user clicks on the button
            val column_str: String = columns_edt.text.toString().trim();
            val row_str: String = rows_edt.text.toString().trim();
            var row_int: Int = 0;
            var column_int: Int = 0;
            try {
                //Validate for emptry imputs
                if (TextUtils.isEmpty(row_str)) {
                    rows_edt.requestFocus()
                    rows_edt.error = "Please enter value."
                } else if (TextUtils.isEmpty(column_str)) {
                    columns_edt.requestFocus()
                    columns_edt.error = "Please enter value."
                } else {
                    row_int = row_str.toInt()
                    column_int = column_str.toInt()
                    if (validateDetails(row_int, column_int, rows_edt, columns_edt)) {


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

}

/*https://www.google.com/search?sxsrf=ACYBGNQ7uQbAT-A1uk5MSky88DxHI9irpQ%3A1568868334883&ei=7geDXYXENYXfz7sP9_GmoAg&q=generate+a+matrix+in+java&oq=generate+a+matrix+in+java&gs_l=psy-ab.3..0i5i30j0i8i30l2.44227.44227..44768...0.2..0.300.300.3-1......0....1..gws-wiz.......0i71.1Zb5yjWoAAc&ved=0ahUKEwiF87L-idzkAhWF73MBHfe4CYQQ4dUDCAs&uact=5
https://www.java-programs.thiyagaraaj.com/create_matrix_java_example_program.html
https://stackoverflow.com/questions/45339504/android-dynamically-create-matrix-of-edittext-based-on-user-input
https://stackoverflow.com/questions/43843855/how-to-create-a-33-matrix-grid-in-android*/

