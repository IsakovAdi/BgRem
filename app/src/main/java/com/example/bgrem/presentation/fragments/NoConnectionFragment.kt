package com.example.bgrem.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.databinding.FragmentNoConnetionBinding

class NoConnectionFragment : Fragment() {

    private val args by navArgs<NoConnectionFragmentArgs>()

    private val binding: FragmentNoConnetionBinding by lazy {
        FragmentNoConnetionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateJobBtn.setOnClickListener {
            findNavController().navigate(
                NoConnectionFragmentDirections.actionNoConnectionFragmentToLoadingFragment(
                    args.imageUri, LoadingFragment.FROM_NO_CONNECT_FRAGMENT
                )
            )
        }
    }
}