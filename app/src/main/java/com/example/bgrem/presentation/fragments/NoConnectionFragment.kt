package com.example.bgrem.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.databinding.FragmentNoConnetionBinding
import com.example.bgrem.presentation.fragments.loadingFragment.LoadingFragment

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
            if (args.direction == FinalLoadingFragment.FINAL_LOADING_FRAGMENT) {
                findNavController().navigate(
                    NoConnectionFragmentDirections.actionNoConnectionFragmentToFinalLoadingFragment(
                        args.bgId,
                        args.job,
                        args.imageUri,
                        NO_CONNECTION_FRAGMENT
                    )
                )
            }
            else{
                findNavController().navigate(
                    NoConnectionFragmentDirections.actionNoConnectionFragmentToLoadingFragment(
                        args.imageUri, NO_CONNECTION_FRAGMENT
                    )
                )
            }
//            if (args.direction == LoadingFragment.LOADING_FRAGMENT) {
//                findNavController().navigate(
//                    NoConnectionFragmentDirections.actionNoConnectionFragmentToLoadingFragment(
//                        args.imageUri, NO_CONNECTION_FRAGMENT
//                    )
//                )
//            } else if (args.direction == FinalLoadingFragment.FINAL_LOADING_FRAGMENT) {
//                findNavController().navigate(
//                    NoConnectionFragmentDirections.actionNoConnectionFragmentToFinalLoadingFragment(
//                        args.bgId,
//                        args.job,
//                        args.imageUri,
//                        NO_CONNECTION_FRAGMENT
//                    )
//                )
//            }
        }
    }

    companion object {
        const val NO_CONNECTION_FRAGMENT = "NO_CONNECTION_FRAGMENT"
    }
}