@file:Suppress("DEPRECATION")

package com.heinlin.thenewsapp.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    // Dark mode preference
    var isDarkModeEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_DARK_MODE, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_DARK_MODE, value).apply()

    // Language preference
    var language: String
        get() = sharedPreferences.getString(KEY_LANGUAGE, "en") ?: "en"
        set(value) = sharedPreferences.edit().putString(KEY_LANGUAGE, value).apply()

    // Apply the dark mode setting
    fun applyDarkMode() {
        val mode = if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    // Apply the language setting
    fun applyLanguage(context: Context) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_LANGUAGE = "language"
    }
}
