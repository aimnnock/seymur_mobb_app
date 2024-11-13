package com.seymur.mobileapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seymur.mobileapp.databinding.RegisterfragmentBinding
import java.io.File
import java.io.FileOutputStream

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: RegisterfragmentBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterfragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        preferences = getSharedPreferences("information", MODE_PRIVATE)


        binding.registerRButton.setOnClickListener {
            val enteredMail = binding.editTextTextEmailAddress.text.toString()
            val enteredPassword = binding.editTextTextPassword.text.toString()


            val file = File(filesDir, "x.txt")
            val userExists = file.exists() && file.readLines().any { line ->
                val (email, password) = line.split(":")
                email == enteredMail && password == enteredPassword
            }

            if (userExists) {
                Toast.makeText(this, "User already exists. Please log in.", Toast.LENGTH_LONG).show()
            } else {

                preferences.edit().apply {
                    putString("user", enteredMail)
                    putString("password", enteredPassword)
                    apply()
                }


                FileOutputStream(file, true).use { outputStream ->
                    outputStream.write("$enteredMail:$enteredPassword\n".toByteArray())
                }

                Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show()


                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        binding.goBackButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
