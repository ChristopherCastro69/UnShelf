package com.example.unshelf.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.example.unshelf.R
import com.example.unshelf.model.entities.FireUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class fireprac : AppCompatActivity() {

    companion object {
        const val TAG = "fireprac"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fireprac)

        val submitBtn = findViewById<Button>(R.id.fire_submit)

        // Get a reference to the Firestore database
        val db = FirebaseFirestore.getInstance()

        // Create a new user object
        val user = FireUser("John Doe", "john@example.com")

        // Assume "firePractice" is a collection in your Firestore database
        val usersCollection = db.collection("firePractice")

        // Add the new user to the "firePractice" collection
        submitBtn.setOnClickListener {
            // Add the new user to the "firePractice" collection
            usersCollection.add(user)
                .addOnSuccessListener { documentReference ->
                    // Document added successfully
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    // Handle errors
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }
}
