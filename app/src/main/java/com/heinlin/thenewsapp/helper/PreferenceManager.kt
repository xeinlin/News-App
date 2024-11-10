@file:Suppress("DEPRECATION")

package com.heinlin.thenewsapp.helper

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.heinlin.thenewsapp.R
import java.util.Locale

@Suppress("unused")
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
        val mode =
            if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
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

    val nightColorBackground: String
        get() = remoteConfig.getString("default_night_color_background")

    val nightColorText: String
        get() = remoteConfig.getString("default_night_color_text")

    // Retrieve toolbar color from Remote Config
    val toolbarColor: String
        get() = remoteConfig.getString("toolbar_background_color")

    // Initialize Firebase Remote Config
    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance().apply {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0) // 1 hour, adjust as needed
            .build()
        setConfigSettingsAsync(configSettings)
        setDefaultsAsync(R.xml.remote_config_defaults) // Default values in XML
    }

    // Fetch and apply Remote Config values
    fun fetchRemoteConfigValues(onComplete: (() -> Unit)? = null) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Update other preferences from Remote Config if needed
                    isDarkModeEnabled = remoteConfig.getBoolean("default_dark_mode")
                    applyDarkMode()
                    // Apply the toolbar color if needed
                    onComplete?.invoke()
                }
            }
    }


}
