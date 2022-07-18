package com.example.bgrem.presentation.ViewHolders

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.bgrem.databinding.BgItemBinding
import com.example.bgrem.databinding.ItemUserBgBinding
import com.example.bgrem.domain.models.BackgroundResponse
import com.squareup.picasso.Picasso

abstract class GenericViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: BackgroundResponse)
}

class BgViewHolder(
    private val binding: BgItemBinding,
    private val inflateType: Boolean
) : GenericViewHolder(binding) {
    override fun bind(bgItem: BackgroundResponse) {
        binding.apply {
            if (inflateType) bgImg.setBackgroundColor(Color.parseColor(bgItem.color))
            else Picasso.get().load(bgItem.file_url).into(bgImg)
        }
    }
}

class UserItemViewHolder(
    private val binding: ItemUserBgBinding,
) : GenericViewHolder(binding) {
    override fun bind(bgItem: BackgroundResponse) {
        binding.apply {

        }
    }
}