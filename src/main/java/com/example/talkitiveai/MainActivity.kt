package com.example.talkitiveai

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.talkitiveai.ui.ChatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var themeGroup: RadioGroup
    private lateinit var systemBtn: RadioButton
    private lateinit var lightBtn: RadioButton
    private lateinit var darkBtn: RadioButton
    private lateinit var continueBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        themeGroup = findViewById(R.id.themeOptions)
        systemBtn = findViewById(R.id.themeSystem)
        lightBtn = findViewById(R.id.themeLight)
        darkBtn = findViewById(R.id.themeDark)
        continueBtn = findViewById(R.id.continueButton)

        continueBtn.setOnClickListener {
            // Apply selected theme
            when (themeGroup.checkedRadioButtonId) {
                R.id.themeSystem -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                R.id.themeLight -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.themeDark -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            // Show disclaimer popup
            showDisclaimerDialog()
        }
    }

    private fun showDisclaimerDialog() {
        AlertDialog.Builder(this)
            .setTitle("Disclaimer")
            .setMessage("This is an AI chatbot. All conversations are fictional and for entertainment only. Must be 18+.")
            .setCancelable(false)
            .setPositiveButton("I Understand") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()

                // Launch ChatActivity
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)
            }
            .show()
    }

}
