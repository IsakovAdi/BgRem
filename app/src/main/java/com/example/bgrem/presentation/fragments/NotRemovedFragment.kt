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


class NotRemovedFragment : Fragment() {

    private val args by navArgs<NotRemovedFragmentArgs>()
    private val binding: FragmentNotRemovedBinding by lazy {
        FragmentNotRemovedBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.updateJobBtn.setOnClickListener {
            findNavController().navigate(
                NotRemovedFragmentDirections.actionNotRemovedFragmentToLoadingFragment(
                    args.imageUri,
                    LoadingFragment.FROM_NOT_REMOVED_FRAGMENT
                )
            )
        }
    }
}