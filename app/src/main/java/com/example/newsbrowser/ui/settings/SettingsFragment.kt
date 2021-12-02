package com.example.newsbrowser.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsbrowser.R
import com.example.newsbrowser.databinding.SettingsFragmentBinding
import com.example.newsbrowser.model.NewsRequest
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var lang: NewsRequest.Language
    private val sharedPref by lazy {
        requireActivity().getSharedPreferences(
        getString(R.string.preference_language_key),
        Context.MODE_PRIVATE)  }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SettingsFragmentBinding.inflate(inflater)
        lang = NewsRequest.Language.valueOf(
            sharedPref.getString(getString(R.string.chosen_language_key),
            NewsRequest.Language.ENGLISH.name)!!)
        val checkedId = sharedPref.getInt(getString(R.string.checked_radio_key), R.id.english_option)
        binding.languageRadioGroup.check(checkedId)
        binding.lifecycleOwner = viewLifecycleOwner
        setUpAction()
        return binding.root
    }

    fun setUpAction(){
        binding.languageRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            lang = when (checkedId) {
                R.id.arabic_option -> NewsRequest.Language.ARABIC
                R.id.german_option -> NewsRequest.Language.GERMAN
                R.id.english_option -> NewsRequest.Language.ENGLISH
                R.id.spanish_option -> NewsRequest.Language.SPANISH
                R.id.french_option -> NewsRequest.Language.FRENCH
                R.id.hebrew_option -> NewsRequest.Language.HEBREW
                R.id.italian_option -> NewsRequest.Language.ITALIAN
                R.id.dutch_option -> NewsRequest.Language.DUTCH
                R.id.norwegian_option -> NewsRequest.Language.NORWEGIAN
                R.id.portuguese_option -> NewsRequest.Language.PORTUGUESE
                R.id.polish_option -> NewsRequest.Language.POLISH
                R.id.russian_option -> NewsRequest.Language.RUSSIAN
                else -> NewsRequest.Language.ENGLISH
            }
        }

        binding.saveButton.setOnClickListener {
            with(sharedPref.edit()){
                putString(getString(R.string.chosen_language_key), lang.name)
                apply()
            }
            val checkedRadioButton = binding.languageRadioGroup.checkedRadioButtonId
            with(sharedPref.edit()){
                putInt(getString(R.string.checked_radio_key), checkedRadioButton)
                apply()
            }
            Snackbar.make(it, "${lang.lang} is set as search language", Snackbar.LENGTH_LONG).show()
        }
    }
}