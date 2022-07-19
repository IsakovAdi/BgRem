package com.example.bgrem.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.R
import com.example.bgrem.databinding.FragmentNotRemovedBinding
import com.example.bgrem.databinding.FragmentReadyBinding
import com.example.bgrem.presentation.fragments.selectBgFragment.SelectBgFragment

class ReadyFragment : Fragment() {

    private val binding: FragmentReadyBinding by lazy {
        FragmentReadyBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private fun launchSelectBgFragment() {
        findNavController().navigate(
            ReadyFragmentDirections.actionReadyFragmentToSelectBgFragment(
                "",
                "",
                SelectBgFragment.FROM_READY_FRAGMENT
            )
        )
    }
}