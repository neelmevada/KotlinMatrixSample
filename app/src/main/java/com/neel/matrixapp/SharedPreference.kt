package com.neel.matrixapp

import android.content.Context
import android.content.SharedPreferences
import java.io.Serializable

class SharedPreference(context: Context) {

    val sharedPrefName = "MatrixPracticle"
    val mContext: Context = context;

    val sharedPreferences: SharedPreferences = mContext.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)

    public fun setList(lists: ArrayList<GridItem>) {
        val id: Int = Integer.parseInt("2")
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("id_key", id)
        editor.putString("name_key", listOf(lists).toString())
        editor.apply()
        editor.commit()
    }


}