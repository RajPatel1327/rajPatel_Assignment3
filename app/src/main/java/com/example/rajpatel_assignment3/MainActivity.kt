package com.example.rajpatel_assignment3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSend = findViewById<Button>(R.id.buttonSend)
        val editTextCarDetails = findViewById<EditText>(R.id.editTextCarDetails)

        buttonSend.setOnClickListener {
            val carDetails = editTextCarDetails.text.toString()

            val intent = Intent(this, CarDetailActivity::class.java).apply {
                putExtra("CAR_DETAILS", carDetails)
            }
            startActivity(intent)
        }
    }
}
