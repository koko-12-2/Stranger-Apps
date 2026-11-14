package com.strangerapps

import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private lateinit var itemInput: EditText
    private lateinit var saveItemButton: Button
    private lateinit var challengeButton: Button
    private lateinit var mealButton: Button
    private lateinit var excuseButton: Button
    private lateinit var plantButton: Button
    private lateinit var moodButton: Button
    private lateinit var soundButton: Button

    private lateinit var tts: TextToSpeech
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.resultText)
        itemInput = findViewById(R.id.itemInput)
        saveItemButton = findViewById(R.id.saveItemButton)
        challengeButton = findViewById(R.id.challengeButton)
        mealButton = findViewById(R.id.mealButton)
        excuseButton = findViewById(R.id.excuseButton)
        plantButton = findViewById(R.id.plantButton)
        moodButton = findViewById(R.id.moodButton)
        soundButton = findViewById(R.id.soundButton)

        prefs = getSharedPreferences("stranger_apps", MODE_PRIVATE)

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.US
            }
        }

        challengeButton.setOnClickListener { showRandomChallenge() }
        mealButton.setOnClickListener { showRandomMeal() }
        excuseButton.setOnClickListener { showRandomExcuse() }
        plantButton.setOnClickListener { showPlantReminder() }
        moodButton.setOnClickListener { showMoodStatus() }
        soundButton.setOnClickListener { sayHello() }
        saveItemButton.setOnClickListener { saveLostItem() }

        val savedItem = prefs.getString("lost_item", "No item saved yet")
        resultText.text = "Saved item: $savedItem"
    }

    private fun showRandomChallenge() {
        val challenges = listOf(
            "Walk 10 minutes without your phone.",
            "Say thank you to someone today.",
            "Take a silly selfie and share it.",
            "Learn one new English word.",
            "Clean one small area in your room."
        )
        val message = "Challenge: ${challenges.random()}"
        resultText.text = message
    }

    private fun showRandomMeal() {
        val meals = listOf(
            "Spicy noodles",
            "Crispy fried rice",
            "Chicken sandwich",
            "Fruit smoothie",
            "Rice and curry",
            "Toast with eggs"
        )
        val message = "Meal idea: ${meals.random()}"
        resultText.text = message
    }

    private fun showRandomExcuse() {
        val excuses = listOf(
            "My laptop was doing a drama show.",
            "The universe stole my focus.",
            "My alarm clock betrayed me.",
            "I was trapped in a productivity storm.",
            "The Wi-Fi was on vacation."
        )
        val message = "Excuse: ${excuses.random()}"
        resultText.text = message
    }

    private fun showPlantReminder() {
        val message = "Plant reminder: Water the plant and give it sunlight."
        resultText.text = message
        Toast.makeText(this, "Your plant needs water!", Toast.LENGTH_SHORT).show()
    }

    private fun showMoodStatus() {
        val moods = listOf(
            "Happy",
            "Calm",
            "Bored",
            "Tired",
            "Excited"
        )
        val selected = moods.random()
        resultText.text = "Mood today: $selected"
        Toast.makeText(this, "Mood set to $selected", Toast.LENGTH_SHORT).show()
    }

    private fun sayHello() {
        if (::tts.isInitialized) {
            tts.speak(
                "Hello stranger! Welcome to Stranger Apps.",
                TextToSpeech.QUEUE_FLUSH,
                null,
                "stranger_app"
            )
        }
        resultText.text = "Voice message: Hello stranger!"
    }

    private fun saveLostItem() {
        val item = itemInput.text.toString().trim()
        if (item.isEmpty()) {
            Toast.makeText(this, "Please enter an item name.", Toast.LENGTH_SHORT).show()
            return
        }

        prefs.edit().putString("lost_item", item).apply()
        resultText.text = "Saved item: $item"
        itemInput.text?.clear()
        Toast.makeText(this, "Item saved successfully.", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
