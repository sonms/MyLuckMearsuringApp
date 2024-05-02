package com.purang.myluckmeasuringapp.Helper

import android.content.Context
import com.purang.myluckmeasuringapp.R

object ThemeHelper {
    const val PREFS_NAME = "ThemePrefs"
    const val PREF_SELECTED_THEME = "SelectedTheme"

    fun applyTheme(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        when (sharedPreferences.getInt(PREF_SELECTED_THEME, 0)) {
            0 -> context.setTheme(R.style.Theme_MyLuckMeasuringApp)
            1 -> context.setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_DarkActionBar)
        }
    }

    fun saveThemeSelection(context: Context, themeIndex: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(PREF_SELECTED_THEME, themeIndex).apply()
    }


}