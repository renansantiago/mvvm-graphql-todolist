package com.rs.todolist.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * extension function that make any view visible
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * extension function that hide any view (gone)
 */
fun View.hide() {
    visibility = View.GONE
}

/**
 * extension function for the Toast class that takes a string
 */
fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

/**
 * inline function to convert json string to a TypeToken generic type
 */
inline fun <reified T> Gson.fromJsonToObjectType(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)
