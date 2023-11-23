package uk.ac.tees.mad.W9641667

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {

    private var authFirebase: FirebaseAuth = Firebase.auth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val registrationMessage = intent.getStringExtra("registration_success")
        registrationMessage?.let {
            showSnackbar(it)
        }



        val loginButton: Button = findViewById(R.id.loginButton)
        val emailEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.password)

        val signUpTextView: TextView = findViewById(R.id.signupText)

        signUpTextView.setOnClickListener {
            // Start the RegisterActivity
            val intent = Intent(this, RegistationsActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInWithEmail(email, password)
            } else {
                var bar:Snackbar = Snackbar.make(findViewById(android.R.id.content)," Email and Password cannot be empty",Snackbar.LENGTH_SHORT);
                bar.show()
            }
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        authFirebase.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                 startActivity(Intent(this,MainActivity::class.java))
                    finish()
                } else {
                    var bar:Snackbar = Snackbar.make(findViewById(android.R.id.content),"Login Failed",Snackbar.LENGTH_SHORT)
                    bar.show()
                }
            }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}