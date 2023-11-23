package uk.ac.tees.mad.W9641667

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import android.view.View
import android.widget.Button
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private var authenticationFirebase: FirebaseAuth = Firebase.auth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.cardCalorieCounter).setOnClickListener{
            startActivity(Intent(this,CaloriesCounter::class.java))
        }

        findViewById<Button>(R.id.button_signout).setOnClickListener{
            authenticationFirebase.signOut();
            startActivity(Intent(this,Login::class.java))
        }


    }

    override fun onStart() {
        super.onStart()
        val currentUser = authenticationFirebase.currentUser
        if (currentUser==null)
        {
            startActivity(Intent(this,Login::class.java))
            finish()
        }
    }

}