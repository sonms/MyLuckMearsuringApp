package com.purang.myluckmeasuringapp.adapter

import android.content.Context
import android.content.res.ColorStateList
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

        holder.itemView.setOnClickListener {
            if (items[position].content == "win") {
                val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context!! , R.color.winning_color1)) //승인
                // 배경색 변경
                holder.itemView.backgroundTintList = colorStateList

                notifyDataSetChanged()
            } else {
                val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context!! , R.color.purang_gray7)) //승인
                // 배경색 변경
                holder.itemView.backgroundTintList = colorStateList
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = items.size
}
