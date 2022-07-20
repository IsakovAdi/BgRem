package com.example.bgrem.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrem.R
import com.example.bgrem.databinding.BgItemBinding
import com.example.bgrem.databinding.ItemUserBgBinding
import com.example.bgrem.domain.models.BackgroundResponse
import com.example.bgrem.presentation.ViewHolders.BgViewHolder
import com.example.bgrem.presentation.ViewHolders.GenericViewHolder
import com.example.bgrem.presentation.ViewHolders.UserItemViewHolder
import com.example.bgrem.presentation.diffCallbacks.BgItemDiffCallBack
import com.example.bgrem.presentation.makeView

class BgItemAdapter : RecyclerView.Adapter<GenericViewHolder>() {
    var onBgItemClickListener: ((BackgroundResponse) -> Unit)? = null
    private val itemList = mutableListOf<BackgroundResponse>()

    fun setList(newList: List<BackgroundResponse>) {
        val diffUtilCallback = BgItemDiffCallBack(oldList = itemList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        itemList.clear()
        itemList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder =
        when (viewType) {
            COLOR_ITEM_TYPE -> BgViewHolder(
                binding = BgItemBinding.bind(
                    R.layout.bg_item.makeView(
                        parent
                    )
                ), inflateType = true
            )
            USER_BG_ITEM_TYPE -> UserItemViewHolder(
                binding = ItemUserBgBinding.bind(
                    R.layout.item_user_bg.makeView(
                        parent = parent
                    )
                )
            )
            IMAGE_ITEM_TYPE -> BgViewHolder(
                binding = BgItemBinding.bind(
                    R.layout.bg_item.makeView(
                        parent
                    )
                ), inflateType = false
            )
            else -> {
                BgViewHolder(
                    binding = BgItemBinding.bind(
                        R.layout.bg_item.makeView(
                            parent
                        )
                    ), inflateType = false
                )
            }
        }

    override fun getItemViewType(position: Int): Int = when (itemList[position].group) {
        "color" -> COLOR_ITEM_TYPE
        "image" -> IMAGE_ITEM_TYPE
        "transparent" -> NO_BG_ITEM_TYPE
        else -> USER_BG_ITEM_TYPE
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onBgItemClickListener?.invoke(itemList[position])
        }
        holder.bind(item = itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    companion object {
        const val COLOR_ITEM_TYPE = 1
        const val NO_BG_ITEM_TYPE = 2
        const val USER_BG_ITEM_TYPE = 3
        const val IMAGE_ITEM_TYPE = 4
    }

}


