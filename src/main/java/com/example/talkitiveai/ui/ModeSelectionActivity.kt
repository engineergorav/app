package com.example.talkitiveai.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.talkitiveai.R

class ModeSelectionActivity : AppCompatActivity(), ModeAdapter.OnModeClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_selection)

        val modeList = listOf("Loving", "Funny", "Serious", "Romantic", "Supportive", "Friendly")
        val recyclerView = findViewById<RecyclerView>(R.id.modeRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ModeAdapter(modeList, this)
    }

    override fun onModeClick(mode: String) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chat_mode", mode)
        startActivity(intent)
    }
}
