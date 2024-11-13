package com.seymur.mobileapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seymur.mobileapp.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private var fromRegisterPage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("information", MODE_PRIVATE)
        fromRegisterPage = preferences.getBoolean("fromRegisterPage", false)


        binding.LoginButton.setOnClickListener {
            val loginMail = binding.loginMail.text.toString()
            val loginPassword = binding.loginPassword.text.toString()


            val file = File(filesDir, "x.txt")
            val userExists = file.exists() && file.readLines().any { line ->
                val (email, password) = line.split(":")
                email == loginMail && password == loginPassword
            }

            if (userExists) {
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "The information that you entered is incorrect", Toast.LENGTH_LONG).show()
            }
        }


        binding.RegisterButton.setOnClickListener {
            preferences.edit().putBoolean("fromRegisterPage", true).apply()
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fromRegisterPage = preferences.getBoolean("fromRegisterPage", false)
    }

    override fun onBackPressed() {
        if (fromRegisterPage) {
            preferences.edit().putBoolean("fromRegisterPage", false).apply()
            super.onBackPressed()
        } else {
            finish()
        }
    }
}
