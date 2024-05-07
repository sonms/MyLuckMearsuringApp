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
import com.purang.myluckmeasuringapp.dao.GameResultEntity
import com.purang.myluckmeasuringapp.databinding.DrawLotsItemLayoutBinding
import com.purang.myluckmeasuringapp.databinding.MemorialItemLayoutBinding

class MemorialsAdapter(private var items: List<GameResultEntity>) : RecyclerView.Adapter<MemorialsAdapter.ViewHolder>() {

    private lateinit var binding : MemorialItemLayoutBinding
    private var context : Context? = null

    init {
        setHasStableIds(true)
    }

    // 아이템 클릭을 위한 인터페이스 정의
    interface ItemClickListener {
        fun onClick(view: View, position: Int, itemId: Int?)
    }

    private var itemClickListener: ItemClickListener? = null

    fun setItemClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }


    // 강조할 텍스트를 함께 전달
    /*fun updateData(newItems: List<DrawLotsData>, highlightText: String) {
        items = newItems
        this.highlightText = highlightText
        notifyDataSetChanged()
    }*/

    inner class ViewHolder(binding: MemorialItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameResultEntity, position: Int) {
            binding.memoDiceTv.text = item.gameDice

            if (item.gameRoulette == "win") {
                binding.memoRouletteIv.setImageResource(R.drawable.winning_icon)
            } else {
                binding.memoRouletteIv.setImageResource(R.drawable.lose_icon)
            }

            if (item.gameSniffling == "win") {
                binding.memoSnifflingIv.setImageResource(R.drawable.winning_icon)
            } else {
                binding.memoSnifflingIv.setImageResource(R.drawable.lose_icon)
            }

            if (item.gameDrawLots == "win") {
                binding.memoDrawlotsIv.setImageResource(R.drawable.winning_icon)
            } else {
                binding.memoDrawlotsIv.setImageResource(R.drawable.lose_icon)
            }

            when (item.gameJelly) {
                "gold" -> {
                    binding.memoJellyIv.setImageResource(R.drawable.gold_main_jelly)
                }
                "silver" -> {
                    binding.memoJellyIv.setImageResource(R.drawable.silver_main_jelly)
                }
                else -> {
                    binding.memoJellyIv.setImageResource(R.drawable.bronze_main_jelly)
                }
            }

            binding.memoDateTv.text = item.gameDate
            binding.memoPercentageTv.text = item.gamePercentage

            /*if (item.number % 2 != 0) {
                binding.drawItemIv.setImageResource(R.drawable.note_icon)
                binding.drawItemTv.text = item.number.toString()
            } else {
                binding.drawItemIv.setImageResource(R.drawable.note2_icon)
                binding.drawItemTv.text = item.number.toString()
                binding.drawItemTv.setTextColor(ContextCompat.getColor(context!! ,R.color.purang_gray3))
            }*/

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
        binding = MemorialItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)

        val currentItem = items[position]
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, holder.adapterPosition, items[holder.adapterPosition].gameCode)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /*private fun setSelection(pos : Int) {
        items[pos].isSelected = !items[pos].isSelected
        notifyItemChanged(pos)
    }*/
}
