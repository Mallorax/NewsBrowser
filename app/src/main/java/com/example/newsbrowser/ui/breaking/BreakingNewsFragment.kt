package com.example.newsbrowser.ui.breaking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsbrowser.databinding.BreakingNewsFragmentBinding

class BreakingNewsFragment: Fragment() {

    private var _binding: BreakingNewsFragmentBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BreakingNewsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}