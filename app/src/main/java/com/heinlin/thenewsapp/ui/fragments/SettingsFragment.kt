package com.heinlin.thenewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.heinlin.thenewsapp.R
import com.heinlin.thenewsapp.databinding.FragmentSettingBinding
import com.heinlin.thenewsapp.util.PreferencesManager

class SettingsFragment : Fragment() {

    private lateinit var languageButton: Button
    private lateinit var darkModeSwitch: SwitchCompat
    private lateinit var versionTextView: TextView
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())
        preferencesManager.applyDarkMode() // Apply on creation
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

        darkModeSwitch.isChecked = preferencesManager.isDarkModeEnabled
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            preferencesManager.isDarkModeEnabled = isChecked
            preferencesManager.applyDarkMode()
            requireActivity().recreate() // Refresh UI
        }

        languageButton.setOnClickListener {
            showLanguageDialog()
        }

        versionTextView.text = "1.0.0"
    }

    override fun onResume() {
        super.onResume()
        // Reapply language when returning to the SettingsFragment
        PreferencesManager(requireContext()).applyLanguage(requireContext())
    }


    private fun updateLanguageText(language: String) {
        val languageText = when (language) {
            "en" -> getString(R.string.english)
            "my" -> getString(R.string.myanmar)
            "th" -> getString(R.string.thai)
            else -> getString(R.string.english) // Default
        }
        binding.languageButton.text = languageText // Assuming you have a TextView to show the language
    }

    private fun showLanguageDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_language, null)
        val languageRadioGroup = dialogView.findViewById<RadioGroup>(R.id.language_radio_group)
        val radioEnglish = dialogView.findViewById<RadioButton>(R.id.radio_english)
        val radioBurmese = dialogView.findViewById<RadioButton>(R.id.radio_myanmar)
        val radioThai = dialogView.findViewById<RadioButton>(R.id.radio_thai)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)

        // Set current language
        when (preferencesManager.language) {
            "en" -> radioEnglish.isChecked = true
            "my" -> radioBurmese.isChecked = true
            "th" -> radioThai.isChecked = true
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        languageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedLanguage = when (checkedId) {
                R.id.radio_english -> "en"
                R.id.radio_myanmar -> "my"
                else -> "th"
            }
            preferencesManager.language = selectedLanguage
            preferencesManager.applyLanguage(requireContext())
            requireActivity().recreate()
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss() // Close the dialog when cancel button is clicked
        }

        dialog.show()
    }

}


