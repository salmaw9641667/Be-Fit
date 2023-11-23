package uk.ac.tees.mad.W9641667

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistationsActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_registations)

        auth = FirebaseAuth.getInstance()

        val registerButton: Button = findViewById(R.id.reg_button)
        val nameEditText: EditText = findViewById(R.id.reg_name)
        val mobileEditText: EditText = findViewById(R.id.reg_mobile)
        val emailEditText: EditText = findViewById(R.id.reg_username)
        val passwordEditText: EditText = findViewById(R.id.reg_password)
        val loginTextView: TextView = findViewById(R.id.reg_loginText)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val mobile = mobileEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(name, mobile, email, password)
            } else {
                showSnackbar("Email and Password cannot be empty")
            }
        }

        loginTextView.setOnClickListener {
            // Redirect to LoginActivity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(name: String, mobile: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Save additional user details to Firebase Realtime Database
                    saveUserToDatabase(name, mobile, email)
                } else {
                    showSnackbar("Registration failed: ${task.exception?.message}")
                }
            }
    }

    private fun saveUserToDatabase(name: String, mobile: String, email: String) {
        val userId = auth.currentUser?.uid ?: return
        val user = User(name, mobile, email)

        FirebaseDatabase.getInstance().getReference("Users")
            .child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, Login::class.java)
                    intent.putExtra("registration_success", "Registration successful. Please log in.")
                    startActivity(intent)
                    finish()
                } else {
                    showSnackbar("Failed to save user data: ${task.exception?.message}")
                }
            }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    // User data model
    data class User(val name: String, val mobile: String, val email: String)
}