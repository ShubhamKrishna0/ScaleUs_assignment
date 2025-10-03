package com.example.interntaskapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val signInBtn = findViewById<Button>(R.id.btnSignIn)
        val signUpBtn = findViewById<Button>(R.id.btnSignUp)

        // SIGN IN
        signInBtn.setOnClickListener {
            Log.d("SignIn", "Sign In button clicked")
            val userEmail = email.text.toString()
            val userPass = password.text.toString()
            Log.d("SignIn", "Email: $userEmail, Password length: ${userPass.length}")

            if (userEmail.isEmpty() || userPass.isEmpty()) {
                Log.d("SignIn", "Empty fields detected")
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("SignIn", "Starting Firebase authentication")
            auth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("SignIn", "Authentication successful, navigating to Dashboard")
                    Toast.makeText(this, "✅ Logged in successfully!", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, DashboardActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Log.e("SignIn", "Authentication failed: ${it.exception?.message}")
                    val errorMessage = when {
                        it.exception?.message?.contains("no user record", true) == true ||
                        it.exception?.message?.contains("user not found", true) == true -> 
                            "🤔 No user found with this email! 📧"
                        it.exception?.message?.contains("wrong-password", true) == true ||
                        it.exception?.message?.contains("invalid-credential", true) == true -> 
                            "🔐 Wrong password! Please try again! 💪"
                        it.exception?.message?.contains("invalid-email", true) == true -> 
                            "📧 Invalid email format! Please check! ✉️"
                        it.exception?.message?.contains("network", true) == true -> 
                            "📡 No internet connection! Check network! 🌐"
                        it.exception?.message?.contains("too-many-requests", true) == true -> 
                            "⏰ Too many attempts! Try again later! 🕐"
                        else -> "❌ Login failed: ${it.exception?.message ?: "Unknown error"}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        // NAVIGATE TO SIGN UP
        signUpBtn.setOnClickListener {
            Log.d("SignIn", "Sign Up button clicked, navigating to SignUp")
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }
    }
}
