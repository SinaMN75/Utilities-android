package com.satya.utilites.extentions

import android.content.Context
import com.satya.utilites.utilities.Toolkit

private val sharedPreferences = Toolkit.getTopActivityOrApp().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

fun put(key: String, value: String) = sharedPreferences.edit().putString(key, value).apply()

fun put(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).apply()

fun put(key: String, value: Float) = sharedPreferences.edit().putFloat(key, value).apply()

fun put(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()

fun put(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()

fun getString(key: String, defaultValue: String? = null): String? = sharedPreferences.getString(key, defaultValue)

fun getInt(key: String, defaultValue: Int = -1): Int = sharedPreferences.getInt(key, defaultValue)

fun getFloat(key: String, defaultValue: Float = -1F): Float = sharedPreferences.getFloat(key, defaultValue)

fun getBoolean(key: String, defaultValue: Boolean = false): Boolean = sharedPreferences.getBoolean(key, defaultValue)

fun getLong(key: String, defaultValue: Long = -1L): Long = sharedPreferences.getLong(key, defaultValue)

fun clearAll() = sharedPreferences.edit().clear().apply()