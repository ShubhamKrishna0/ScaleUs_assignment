package com.example.interntaskapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etEmail)
        val phone = findViewById<EditText>(R.id.etPhone)
        val password = findViewById<EditText>(R.id.etPassword)
        val signUpBtn = findViewById<Button>(R.id.btnSignUp)

        signUpBtn.setOnClickListener {
            Log.d("SignUp", "Sign Up button clicked")
            val userName = name.text.toString()
            val userEmail = email.text.toString()
            val userPhone = phone.text.toString()
            val userPass = password.text.toString()
            Log.d("SignUp", "Name: $userName, Email: $userEmail, Phone: $userPhone")

            if (userEmail.isEmpty() || userPass.isEmpty() || userPhone.isEmpty() || userName.isEmpty()) {
                Log.d("SignUp", "Empty fields detected")
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("SignUp", "Starting user creation")
            signUpBtn.isEnabled = false
            auth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener { task ->
                signUpBtn.isEnabled = true
                if (task.isSuccessful) {
                    Log.d("SignUp", "User created successfully, saving to database")
                    val uid = auth.currentUser?.uid!!
                    val ref = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                    val user = mapOf(
                        "name" to userName,
                        "phone" to userPhone,
                        "createdAt" to System.currentTimeMillis()
                    )
                    ref.setValue(user)

                    Toast.makeText(this, "✨ Account created! Check your email.", Toast.LENGTH_SHORT).show()

                    Log.d("SignUp", "Navigating to email OTP verification")
                    val i = Intent(this, EmailOtpActivity::class.java)
                    i.putExtra("email", userEmail)
                    i.putExtra("phone_number", userPhone)
                    startActivity(i)
                    finish()
                } else {
                    Log.e("SignUp", "User creation failed: ${task.exception?.message}")
                    val errorMsg = when {
                        task.exception?.message?.contains("already in use", true) == true -> 
                            "😱 Email already registered! Use different email! 📧"
                        task.exception?.message?.contains("weak-password", true) == true -> 
                            "💪 Password too weak! Use 6+ characters! ⚔️"
                        task.exception?.message?.contains("invalid-email", true) == true -> 
                            "📧 Invalid email format! Please check! ✉️"
                        task.exception?.message?.contains("network", true) == true -> 
                            "📡 No internet connection! Check network! 🌐"
                        task.exception?.message?.contains("too-many-requests", true) == true -> 
                            "⏰ Too many attempts! Try again later! 🕐"
                        else -> "❌ Signup failed: ${task.exception?.message ?: "Unknown error"}"
                    }
                    Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
