package com.purang.myluckmeasuringapp.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.dao.DrawLotsData
import com.purang.myluckmeasuringapp.databinding.DrawLotsItemLayoutBinding

class DrawLotsAdapter(private var items: List<DrawLotsData>) : RecyclerView.Adapter<DrawLotsAdapter.ViewHolder>() {

    private var highlightText: String = ""
    private lateinit var binding : DrawLotsItemLayoutBinding
    private var context : Context? = null
    private var selectPos = -1

    init {
        setHasStableIds(true)
    }

    // 아이템 클릭을 위한 인터페이스 정의
    interface ItemClickListener {
        fun onClick(view: View, position: Int, itemId: Int?)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    // 강조할 텍스트를 함께 전달
    fun updateData(newItems: List<DrawLotsData>, highlightText: String) {
        items = newItems
        this.highlightText = highlightText
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: DrawLotsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DrawLotsData, position: Int) {
            if (item.number % 2 != 0) {
                binding.drawItemIv.setImageResource(R.drawable.note_icon)
                binding.drawItemTv.text = item.number.toString()
            } else {
                binding.drawItemIv.setImageResource(R.drawable.note2_icon)
                binding.drawItemTv.text = item.number.toString()
                binding.drawItemTv.setTextColor(ContextCompat.getColor(context!! ,R.color.purang_gray3))
            }

            /*itemView.setOnClickListener {
                var beforePos = selectPos
                selectPos = position

                notifyItemChanged(beforePos)
                notifyItemChanged(selectPos)
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*
        * context = parent.context
        sharedPref = SharedPref(context)
        binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        * */
        context = parent.context
        binding = DrawLotsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)

        val currentItem = items[position]
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, holder.adapterPosition, items[holder.adapterPosition].number)
            setSelection(position)
            holder.itemView.isClickable = false
        }

        if (currentItem.isSelected) {
            if (currentItem.content == "win") {
                //val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this@AccountSelectBankActivity , R.color.mio_gray_6)) //승인
                holder.itemView.setBackgroundColor(Color.parseColor("#FFD700"))
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#B7B7B7"))
            }
        }
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun setSelection(pos : Int) {
        items[pos].isSelected = !items[pos].isSelected
        notifyItemChanged(pos)
    }
}
