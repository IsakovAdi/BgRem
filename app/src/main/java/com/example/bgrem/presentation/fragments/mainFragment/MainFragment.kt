package com.example.bgrem.presentation.fragments.mainFragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bgrem.R
import com.example.bgrem.databinding.BottomSheetDialogBinding
import com.example.bgrem.databinding.FragmentMainBinding
import com.example.bgrem.domain.models.JobResponse
import com.example.bgrem.domain.models.TaskResponse
import com.example.bgrem.presentation.UriPathHelper
import com.example.bgrem.presentation.fragments.LoadingFragment
import com.example.bgrem.presentation.isOnline
import com.example.bgrem.presentation.makeView
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MainFragment : Fragment() {
    var cameraUri: Uri? = null
    private var imageUri: Uri? = null
    private var pickImageIntent: Intent? = null
    private val values: ContentValues by lazy {
        ContentValues()
    }
    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasPermissions(requireContext(), *PERMISSIONS)) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, PERMISSION_ALL);
        }
        binding.photoCardView.setOnClickListener {
            openBottomSheet()
        }
        binding.videoCardView.setOnClickListener {
            Toast.makeText(requireContext(), "Еще не обработано", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            imageUri = cameraUri
            launchNextFragment()
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            imageUri = data.data
            launchNextFragment()
        }
    }

    private fun launchLoadingFragment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToLoadingFragment(
                imageUri!!, LoadingFragment.FROM_MAIN_FRAGMENT
            )
        )
    }

    private fun launchNotConnectionFragment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToNoConnectionFragment(
                imageUri!!
            )
        )
    }

    private fun launchNextFragment() {
        if (requireContext().isOnline()) launchLoadingFragment()
        else launchNotConnectionFragment()

    }

    @SuppressLint("IntentReset")
    private fun pickFromGallery() {
        pickImageIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        pickImageIntent?.type = "image/*"
        pickImageIntent?.putExtra(Intent.EXTRA_MIME_TYPES, MIME_TYPES)
        startActivityForResult(pickImageIntent, IMAGE_PICK_CODE)
    }

    private fun takePicture() {
        values.put(MediaStore.Images.Media.TITLE, "MyPicture")
        values.put(
            MediaStore.Images.Media.DESCRIPTION,
            "Photo taken on " + System.currentTimeMillis()
        )
        cameraUri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val binding =
            BottomSheetDialogBinding.bind(R.layout.bottom_sheet_dialog.makeView(binding.root))
        binding.apply {
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
            takeCameraCardView.setOnClickListener {
                takePicture()
                dialog.dismiss()
            }
            pickGalleryCardView.setOnClickListener {
                pickFromGallery()
                dialog.dismiss()
            }
        }
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        dialog.show()
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val REQUEST_CODE = 200
        var PERMISSION_ALL = 1
        const val IMAGE_PICK_CODE = 100
        var PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        val MIME_TYPES = arrayOf("image/jpeg", "image/png")
    }

}