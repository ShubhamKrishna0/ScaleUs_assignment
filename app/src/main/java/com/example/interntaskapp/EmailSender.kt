package com.example.interntaskapp

import android.util.Log
import kotlinx.coroutines.*
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender {
    
    companion object {
        fun sendOtpEmail(toEmail: String, otp: String, callback: (Boolean) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = try {
                    val props = Properties().apply {
                        put("mail.smtp.host", "smtp.gmail.com")
                        put("mail.smtp.port", "587")
                        put("mail.smtp.auth", "true")
                        put("mail.smtp.starttls.enable", "true")
                    }
                    
                    val session = Session.getInstance(props, object : Authenticator() {
                        override fun getPasswordAuthentication(): PasswordAuthentication {
                            return PasswordAuthentication("krishnashubham09@gmail.com", "czuq osax padf xkqp")
                        }
                    })
                    
                    val message = MimeMessage(session).apply {
                        setFrom(InternetAddress("krishnashubham09@gmail.com"))
                        setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail))
                        subject = "Your OTP Code"
                        setText("Your OTP code is: $otp\n\nThis code will expire in 2 minutes.")
                    }
                    
                    Transport.send(message)
                    Log.d("EmailSender", "Email sent successfully")
                    true
                } catch (e: Exception) {
                    Log.e("EmailSender", "Failed to send email: ${e.message}")
                    false
                }
                
                withContext(Dispatchers.Main) {
                    callback(result)
                }
            }
        }
    }
}