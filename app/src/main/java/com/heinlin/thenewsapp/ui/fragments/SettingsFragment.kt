@file:Suppress("DEPRECATION")

package com.heinlin.thenewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.heinlin.thenewsapp.R
import java.util.Locale

class SettingsFragment : Fragment() {

    private lateinit var languageButton: Button
    private lateinit var darkModeSwitch: SwitchCompat
    private lateinit var versionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyDarkModeFromPreferences() // Apply preference on creation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languageButton = view.findViewById(R.id.language_button)
        darkModeSwitch = view.findViewById(R.id.dark_mode_switch)
        versionTextView = view.findViewById(R.id.version_text_view)

        versionTextView.text = "1.0.0"

        // Load dark mode state from SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        darkModeSwitch.isChecked = sharedPreferences.getBoolean("dark_mode", false)

        languageButton.setOnClickListener {
            showLanguageDialog()
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            setDarkMode(isChecked)
            saveDarkModePreference(isChecked)
        }
    }

    private fun applyDarkModeFromPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val isDarkModeEnabled = sharedPreferences.getBoolean("dark_mode", false)
        setDarkMode(isDarkModeEnabled)
    }

    private fun setDarkMode(isEnabled: Boolean) {
        val mode = if (isEnabled) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)

        // Recreate activity to apply theme changes immediately
        requireActivity().recreate()
    }

    private fun saveDarkModePreference(isEnabled: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedPreferences.edit().putBoolean("dark_mode", isEnabled).apply()
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("English", "Burmese")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Language")
            .setItems(languages) { _, which ->
                when (which) {
                    0 -> changeLanguage("en")
                    1 -> changeLanguage("my")
                }
            }
            .show()
    }

    private fun changeLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        requireActivity().recreate() // Recreate activity to apply language change
    }
}


