package uk.ac.tees.mad.W9641667

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private var authenticationFirebase: FirebaseAuth = Firebase.auth;
    private lateinit var bP: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.cardCalorieCounter).setOnClickListener {
            startActivity(Intent(this, CaloriesCounter::class.java))
        }

        findViewById<View>(R.id.cardWorkoutPlans).setOnClickListener {
            startActivity(Intent(this, Workout::class.java))
        }

        findViewById<View>(R.id.cardDietPlans).setOnClickListener {
            startActivity(Intent(this, DietPlan::class.java))
        }
        findViewById<View>(R.id.cardAskTrainer).setOnClickListener {
            startActivity(Intent(this, AskATrainer::class.java))
        }
        findViewById<View>(R.id.cardCommunityForum).setOnClickListener {
            startActivity(Intent(this, Gym::class.java))
        }
        findViewById<Button>(R.id.button_signout).setOnClickListener {
            authenticationFirebase.signOut();
            startActivity(Intent(this, Login::class.java))
        }

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStart() {
        super.onStart()
        val currentUser = authenticationFirebase.currentUser
        if (currentUser==null) {
            startActivity(Intent(this,Login::class.java))
            finish()
        }else{
            showBiometricPrompt()
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun showBiometricPrompt() {
        val executor: Executor = ContextCompat.getMainExecutor(this)

        bP = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {


            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                authenticationFirebase.signOut()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Auth")
            .setSubtitle("Log in")
            .setNegativeButtonText("Use password")
            .build()

        bP.authenticate(promptInfo)

    }

}