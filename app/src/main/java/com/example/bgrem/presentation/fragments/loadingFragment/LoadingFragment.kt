package com.example.bgrem.presentation.fragments.loadingFragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.R
import com.example.bgrem.domain.models.TaskStatus
import com.example.bgrem.presentation.UriPathHelper
import com.example.bgrem.presentation.fragments.selectBgFragment.SelectBgFragment
import com.example.bgrem.presentation.isOnline
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class LoadingFragment : Fragment() {

    private val args by navArgs<LoadingFragmentArgs>()
    private val viewModel: LoadingFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_loading, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireContext().isOnline()) uploadImage()
        else launchNoConnectionFragment()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.job.observe(viewLifecycleOwner) {
            it.getValue()?.let {
                if (it.is_portrait) {
                    jobId = it.id
                    createTask(it.id, "image/${getImageType(args.imageUri).toString()}")
//                    createTask(it.id, "image/jpeg")
                } else {
                    launchNotRemovedFragment()
                    Toast.makeText(requireContext(), "Require portrait file", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        viewModel.task.observe(viewLifecycleOwner) {
            if (it.status == TaskStatus.done.toString()) launchSelectBgFragment(it.id)
            else if (!requireContext().isOnline()) launchNoConnectionFragment()
            else {
                launchNotRemovedFragment()
                taskId = it.id
            }
        }
    }

    private fun launchNotRemovedFragment() {
        findNavController().navigate(
            LoadingFragmentDirections.actionLoadingFragmentToNotRemovedFragment(
                args.imageUri
            )
        )
    }

    private fun launchNoConnectionFragment() {
        findNavController().navigate(
           LoadingFragmentDirections.actionLoadingFragmentToNoConnectionFragment(
                args.imageUri
            )
        )
    }

    private fun launchSelectBgFragment(task: String) {
        findNavController().navigate(
            LoadingFragmentDirections.actionLoadingFragmentToSelectBgFragment(
                jobId,
                task,
                SelectBgFragment.FROM_LOADING_FRAGMENT
            )
        )
    }

    private fun uploadImage() {
        val uriPathHelper = UriPathHelper()
        val filePath = uriPathHelper.getPath(requireContext(), args.imageUri)
        val file = File(filePath)
        val requestFile: RequestBody =
            RequestBody.create("multipart/form-data".toMediaType(), file)
        val multiPartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        viewModel.createJob(multiPartBody)
    }

    private fun createTask(job: String, type: String) {
        if (args.direction == FROM_NOT_REMOVED_FRAGMENT) viewModel.getTask(taskId)
        else viewModel.createTask(job, type, TRANSPARENT_BG_ID)
    }

    private fun getImageType(uri: Uri): String? {
        val cr = requireContext().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(uri))
    }

    companion object {
        const val TRANSPARENT_BG_ID = "db9b524f-2204-433e-a89b-8946bbe01893"
        const val FROM_NO_CONNECT_FRAGMENT = "FROM_NO_CONNECT_FRAGMENT"
        const val FROM_MAIN_FRAGMENT = "FROM_MAIN_FRAGMENT"
        const val FROM_NOT_REMOVED_FRAGMENT = "FROM_TASK_RESPONSE"
        const val FROM_SELECT_BG_FRAGMENT = "FROM_SELECT_BG_FRAGMENT"
        var taskId = String()
        var jobId = String()
    }
}