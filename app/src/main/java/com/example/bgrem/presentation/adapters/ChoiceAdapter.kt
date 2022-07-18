package com.example.bgrem.presentation.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrem.R
import com.example.bgrem.presentation.makeView

class ChoiceAdapter(
    private val actionListener:ChoiceItemOnClickListener
) : RecyclerView.Adapter<ChoiceAdapter.Holder>() {
    private val list = typesList()

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(type: ChoiceTypes) {
            itemView.findViewById<TextView>(R.id.choice_tv).text = when (type) {
                ChoiceTypes.NO_BG -> "НЕТ ФОНА"
                ChoiceTypes.USER_BG -> "ВАШ ФОН"
                ChoiceTypes.COLOR_BG -> "ЦВЕТ"
                ChoiceTypes.PICTURE_BG -> "КАРТИНКА"
            }
            itemView.setOnClickListener { actionListener.onClick(type = type) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(R.layout.choice_item.makeView(parent))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    private fun typesList(): List<ChoiceTypes> {
        val list = mutableListOf<ChoiceTypes>()
        list.add(ChoiceTypes.NO_BG)
        list.add(ChoiceTypes.USER_BG)
        list.add(ChoiceTypes.COLOR_BG)
        list.add(ChoiceTypes.PICTURE_BG)

        return list
    }
    interface ChoiceItemOnClickListener {
        fun onClick(type: ChoiceTypes)
    }

    enum class ChoiceTypes {
        NO_BG,
        USER_BG,
        COLOR_BG,
        PICTURE_BG
    }
}