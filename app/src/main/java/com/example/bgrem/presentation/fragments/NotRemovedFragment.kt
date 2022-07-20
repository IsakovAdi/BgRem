package com.example.bgrem.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.databinding.FragmentNotRemovedBinding
import com.example.bgrem.presentation.fragments.loadingFragment.LoadingFragment


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
                    NOT_REMOVED_FRAGMENT
                )
            )
        }
    }

    companion object{
        const val NOT_REMOVED_FRAGMENT = "NOT_REMOVED_FRAGMENT"
    }
}