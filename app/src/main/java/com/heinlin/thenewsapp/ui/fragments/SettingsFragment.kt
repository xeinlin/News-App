@file:Suppress("DEPRECATION")

package com.heinlin.thenewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        // Load dark mode state from SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        darkModeSwitch.isChecked = sharedPreferences.getBoolean("dark_mode", false)
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            setDarkMode(isChecked)
            saveDarkModePreference(isChecked)
        }


        languageButton.setOnClickListener {
            showLanguageDialog()
        }

        versionTextView.text = "1.0.0"

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
        val dialogView = layoutInflater.inflate(R.layout.dialog_language, null)

        val languageRadioGroup = dialogView.findViewById<RadioGroup>(R.id.language_radio_group)
        val radioEnglish = dialogView.findViewById<RadioButton>(R.id.radio_english)
        val radioBurmese = dialogView.findViewById<RadioButton>(R.id.radio_myanmar)
        val radioThai = dialogView.findViewById<RadioButton>(R.id.radio_thai)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)

        // Set default language based on saved preferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val savedLanguage = sharedPreferences.getString("language", "en")
        when (savedLanguage) {
            "en" -> radioEnglish.isChecked = true
            "my" -> radioBurmese.isChecked = true
            "th" -> radioThai.isChecked = true
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        // Set the cancel button listener
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        // Set the radio button selection listener
        languageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_english -> changeLanguage("en")
                R.id.radio_myanmar -> changeLanguage("my")
                R.id.radio_thai -> changeLanguage("th")
            }
            // Save the selected language in SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString(
                "language", when (checkedId) {
                    R.id.radio_english -> "en"
                    R.id.radio_myanmar -> "my"
                    else -> "th"
                }
            )
            editor.apply()
        }

        dialog.show()

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


