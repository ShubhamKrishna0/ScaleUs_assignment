package com.example.interntaskapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class EmailOtpActivity : AppCompatActivity() {

    private lateinit var otpEditText: EditText
    private lateinit var verifyButton: Button
    private lateinit var resendButton: Button
    private lateinit var timerText: TextView
    private lateinit var emailText: TextView
    
    private var email: String = ""
    private var phoneNumber: String = ""
    private var generatedOtp: String = ""
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_otp)
        
        otpEditText = findViewById(R.id.etEmailOtp)
        verifyButton = findViewById(R.id.btnVerifyEmail)
        resendButton = findViewById(R.id.btnResendEmail)
        timerText = findViewById(R.id.tvEmailTimer)
        emailText = findViewById(R.id.tvEmailInfo)

        email = intent.getStringExtra("email") ?: ""
        phoneNumber = intent.getStringExtra("phone_number") ?: ""
        
        generateAndShowOtp()
        startTimer()

        verifyButton.setOnClickListener {
            verifyOtp()
        }

        resendButton.setOnClickListener {
            generateAndShowOtp()
            startTimer()
        }
    }

    private fun generateAndShowOtp() {
        generatedOtp = Random.nextInt(100000, 999999).toString()
        emailText.text = "Sending OTP to: $email"
        Log.d("EmailOTP", "Generated OTP: $generatedOtp")
        
        sendEmailOtp()
    }
    
    private fun sendEmailOtp() {
        emailText.text = "Sending OTP to: $email..."
        
        EmailSender.sendOtpEmail(email, generatedOtp) { success ->
            runOnUiThread {
                if (success) {
                    Log.d("EmailOTP", "Email sent successfully")
                    emailText.text = "OTP sent to: $email"
                    Toast.makeText(this, "OTP sent to your email", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("EmailOTP", "Failed to send email")
                    emailText.text = "Email failed.\nDemo OTP: $generatedOtp"
                    Toast.makeText(this, "Demo mode: OTP shown above", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun verifyOtp() {
        val enteredOtp = otpEditText.text.toString().trim()
        if (enteredOtp == generatedOtp) {
            Toast.makeText(this, "🎉 Email verified! Welcome aboard! ✨", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "🤔 Wrong OTP! Try again, champion! 💪", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTimer() {
        resendButton.isEnabled = false
        countDownTimer?.cancel()
        
        countDownTimer = object : CountDownTimer(2 * 60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val secs = seconds % 60
                timerText.text = String.format("%02d:%02d", minutes, secs)
            }

            override fun onFinish() {
                timerText.text = "00:00"
                resendButton.isEnabled = true
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}