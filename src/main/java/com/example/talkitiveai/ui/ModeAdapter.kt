package com.example.talkitiveai.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talkitiveai.R

class ModeAdapter(
    private val modes: List<String>,
    private val listener: OnModeClickListener
) : RecyclerView.Adapter<ModeAdapter.ModeViewHolder>() {

    interface OnModeClickListener {
        fun onModeClick(mode: String)
    }

    inner class ModeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modeText: TextView = itemView.findViewById(R.id.modeText)

        init {
            itemView.setOnClickListener {
                listener.onModeClick(modes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mode, parent, false)
        return ModeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModeViewHolder, position: Int) {
        holder.modeText.text = modes[position]
    }

    override fun getItemCount(): Int = modes.size
}
