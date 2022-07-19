package com.example.bgrem.presentation.fragments.selectBgFragment

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bgrem.R
import com.example.bgrem.databinding.FragmentSelectBgBinding
import com.example.bgrem.domain.models.BackgroundGroup
import com.example.bgrem.domain.models.BackgroundResponse
import com.example.bgrem.domain.models.TaskStatus
import com.example.bgrem.presentation.adapters.BgItemAdapter
import com.example.bgrem.presentation.adapters.ChoiceAdapter
import com.example.bgrem.presentation.fragments.loadingFragment.LoadingFragment
import com.squareup.picasso.Picasso


class SelectBgFragment : Fragment(), ChoiceAdapter.ChoiceItemOnClickListener {
    private val args by navArgs<SelectBgFragmentArgs>()

    private val binding: FragmentSelectBgBinding by lazy {
        FragmentSelectBgBinding.inflate(layoutInflater)
    }
    private val adapter: BgItemAdapter by lazy {
        BgItemAdapter()
    }
    private val choiceAdapter: ChoiceAdapter by lazy {
        ChoiceAdapter(actionListener = this)
    }
    private var bgId: String = LoadingFragment.TRANSPARENT_BG_ID

    private val viewModel: SelectBgFragmentVM by viewModels()

    private val pictureBgList = mutableListOf<BackgroundResponse>()
    private val colorBgList = mutableListOf<BackgroundResponse>()
    private val transparentBgList = mutableListOf<BackgroundResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclers()
        viewModel.getTask(args.task)
        viewModel.getBgColors()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.bgList.observe(viewLifecycleOwner) { allList ->
            allList.forEach { backgroundResponse ->
                when (backgroundResponse.group) {
                    "image" -> pictureBgList.add(backgroundResponse)
                    "color" -> colorBgList.add(backgroundResponse)
                    "transparent" -> transparentBgList.add(backgroundResponse)
                }
            }
            adapter.setList(newList = transparentBgList)
        }
        viewModel.task.observe(viewLifecycleOwner) {
            Picasso.get().load(it.download_url).into(binding.mainImage)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclers() {
        with(binding) {
            userChoiceRV.adapter = choiceAdapter
            backgroundsRV.adapter = adapter
        }
        setupBgItemClickListener()
    }

    override fun onClick(type: ChoiceAdapter.ChoiceTypes) {
        when (type) {
            ChoiceAdapter.ChoiceTypes.COLOR_BG -> adapter.setList(newList = colorBgList)
            ChoiceAdapter.ChoiceTypes.PICTURE_BG -> adapter.setList(newList = pictureBgList)
            ChoiceAdapter.ChoiceTypes.NO_BG -> adapter.setList(newList = transparentBgList)
            ChoiceAdapter.ChoiceTypes.USER_BG -> adapter.setList(
                newList = mutableListOf(BackgroundResponse())
            )
        }
    }

    private fun setupBgItemClickListener() {
        adapter.onBgItemClickListener = {
            when (it.group) {
                BackgroundGroup.color.toString() -> changeToColor(it.color)
                BackgroundGroup.image.toString() -> Picasso.get().load(it.file_url)
                    .into(binding.bgImage)
                BackgroundGroup.transparent.toString() -> binding.bgImage.setImageResource(0)
            }
            bgId = it.id!!
        }
    }

    private fun changeToColor(color: String?) {
        val bitmap = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.parseColor(color))
        binding.bgImage.setImageBitmap(bitmap)
    }

    companion object {
        const val FROM_LOADING_FRAGMENT = "FROM_LOADING_FRAGMENT"
        const val FROM_READY_FRAGMENT = "FROM_READY_FRAGMENT"
    }

}