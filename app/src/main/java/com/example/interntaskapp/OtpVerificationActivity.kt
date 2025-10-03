package com.example.interntaskapp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpVerificationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var otpEditText: EditText
    private lateinit var verifyButton: Button
    private lateinit var resendText: TextView
    private lateinit var timerText: TextView

    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var phoneNumber: String = "" // get from previous activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)

        auth = FirebaseAuth.getInstance()

        otpEditText = findViewById(R.id.etOtp)
        verifyButton = findViewById(R.id.btnVerify)
        resendText = findViewById(R.id.tvResend)
        timerText = findViewById(R.id.tvTimer)

        phoneNumber = intent.getStringExtra("phone_number") ?: ""

        sendVerificationCode(phoneNumber)
        startTimer()

        verifyButton.setOnClickListener {
            Log.d("OTP", "Verify button clicked")
            val code = otpEditText.text.toString().trim()
            Log.d("OTP", "Entered code: $code")
            if (code.isNotEmpty()) {
                verifyCode(code)
            } else {
                Log.d("OTP", "Empty OTP entered")
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

        resendText.setOnClickListener {
            Log.d("OTP", "Resend clicked")
            resendCode()
        }
    }

    private fun sendVerificationCode(number: String) {
        val formattedNumber = if (!number.startsWith("+")) "+91$number" else number
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(formattedNumber)
            .setTimeout(2, TimeUnit.MINUTES)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendCode() {
        resendToken?.let {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(2, TimeUnit.MINUTES)
                .setActivity(this)
                .setCallbacks(callbacks)
                .setForceResendingToken(it)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
            startTimer()
        }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Auto-retrieval
            val code = credential.smsCode
            code?.let { verifyCode(it) }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(
                this@OtpVerificationActivity,
                "Verification failed: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onCodeSent(
            verId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verId, token)
            verificationId = verId
            resendToken = token
            Toast.makeText(this@OtpVerificationActivity, "OTP sent", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyCode(code: String) {
        Log.d("OTP", "Verifying code: $code")
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("OTP", "Verification successful, navigating to Dashboard")
                Toast.makeText(this, "Verification Successful", Toast.LENGTH_SHORT).show()
                startActivity(android.content.Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Log.e("OTP", "Verification failed: ${task.exception?.message}")
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startTimer() {
        resendText.isEnabled = false
        object : CountDownTimer(2 * 60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutesPart = seconds / 60
                val secondsPart = seconds % 60
                timerText.text = String.format("%02d:%02d", minutesPart, secondsPart)
            }

            override fun onFinish() {
                timerText.text = "00:00"
                resendText.isEnabled = true
            }
        }.start()
    }
}
