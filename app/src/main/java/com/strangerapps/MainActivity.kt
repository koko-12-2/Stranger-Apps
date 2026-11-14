package com.strangerapps

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var resultText: TextView
    private lateinit var challengeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.resultText)
        challengeButton = findViewById(R.id.challengeButton)

        challengeButton.setOnClickListener {
            val challenges = listOf(
                "10 minutes walk without phone",
                "Say thank you to someone today",
                "Take a silly selfie and share it",
                "Clean one small area in your room",
                "Learn one new English word",
                "Drink water and stretch for 2 minutes"
            )

            val randomChallenge = challenges.random()
            resultText.text = "Challenge: $randomChallenge"
        }
    }
}
