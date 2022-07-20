package com.example.bgrem.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.R
import com.example.bgrem.domain.models.TaskStatus
import com.example.bgrem.presentation.fragments.loadingFragment.LoadingFragmentViewModel
import com.example.bgrem.presentation.getImageType
import com.example.bgrem.presentation.isOnline


class FinalLoadingFragment : Fragment() {
    private val args by navArgs<FinalLoadingFragmentArgs>()
    private val viewModel: LoadingFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_final_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkDirection()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.task.observe(viewLifecycleOwner) {
            downloadUrl = it.download_url
            if (it.status == TaskStatus.done.toString()) {
                launchReadyFragment(it.download_url)
            } else {
                viewModel.getTask(it.id)
            }
        }
    }

    private fun checkDirection() {
        if (args.direction == ReadyFragment.READY_FRAGMENT) {
            launchReadyFragment(downloadUrl)
        } else {
            if (requireContext().isOnline()) createTask()
            else launchNoConnectionFragment()
        }
    }

    private fun launchReadyFragment(downloadUrl: String) {
        findNavController().navigate(
            FinalLoadingFragmentDirections.actionFinalLoadingFragmentToReadyFragment(
                downloadUrl, args.imageUri, FINAL_LOADING_FRAGMENT
            )
        )
    }

    private fun createTask() {
        viewModel.createTask(
            args.job,
            String().getImageType(requireContext(), args.imageUri),
            args.bgId
        )
    }

    private fun launchNoConnectionFragment() {
        findNavController().navigate(
            FinalLoadingFragmentDirections.actionFinalLoadingFragmentToNoConnectionFragment(
                args.bgId, args.job, args.imageUri, FINAL_LOADING_FRAGMENT
            )
        )
    }

    companion object {
        const val FINAL_LOADING_FRAGMENT = "FINAL_LOADING_FRAGMENT"
        private var downloadUrl = String()
    }
}