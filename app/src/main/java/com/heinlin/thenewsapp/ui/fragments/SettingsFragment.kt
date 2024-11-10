package com.heinlin.thenewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.heinlin.thenewsapp.R
import com.heinlin.thenewsapp.helper.PreferencesManager

class SettingsFragment : Fragment() {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var languageButton: Button
    private lateinit var darkModeSwitch: SwitchCompat
    private lateinit var versionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())
        preferencesManager.applyDarkMode() // Apply dark mode based on preferences
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

        // Load dark mode state from PreferencesManager
        darkModeSwitch.isChecked = preferencesManager.isDarkModeEnabled

        languageButton.setOnClickListener {
            showLanguageDialog()
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferencesManager.isDarkModeEnabled = isChecked
            preferencesManager.applyDarkMode()
            requireActivity().recreate() // Recreate activity to apply theme changes
        }
    }

    private fun showLanguageDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_language, null)

        val languageRadioGroup = dialogView.findViewById<RadioGroup>(R.id.language_radio_group)
        val radioEnglish = dialogView.findViewById<RadioButton>(R.id.radio_english)
        val radioBurmese = dialogView.findViewById<RadioButton>(R.id.radio_myanmar)
        val radioThai = dialogView.findViewById<RadioButton>(R.id.radio_thai)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)

        // Set default language based on saved preferences
        when (preferencesManager.language) {
            "en" -> radioEnglish.isChecked = true
            "my" -> radioBurmese.isChecked = true
            "th" -> radioThai.isChecked = true
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        languageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedLanguage = when (checkedId) {
                R.id.radio_english -> "en"
                R.id.radio_myanmar -> "my"
                else -> "th"
            }
            preferencesManager.language = selectedLanguage
            preferencesManager.applyLanguage(requireContext()) // Apply language change
            requireActivity().recreate() // Recreate activity to apply language change
            dialog.dismiss()
        }

        dialog.show()
    }


}
