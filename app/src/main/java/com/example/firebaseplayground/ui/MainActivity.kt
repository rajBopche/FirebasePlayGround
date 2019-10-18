package com.example.firebaseplayground.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseplayground.R
import com.example.firebaseplayground.ui.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fbAuth = FirebaseAuth.getInstance()
    val dbRef = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onRegisterButtonClicked()
        onLoginButtonClicked()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun onRegisterButtonClicked() {
        btn_register.setOnClickListener {

            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Registration Successful from firebase")
                } else {
                    showToast(task.exception?.localizedMessage ?: "Something went wrong")
                }
            }
        }
    }

    private fun onLoginButtonClicked() {
        btn_login.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Login Successful from firebase")
                    saveUserRecord("Roman", password)
                } else {
                    showToast(task.exception?.localizedMessage ?: "Something went wrong")
                }
            }
        }
    }

    private fun saveUserRecord(userName: String, userPassword: String) {
        val user = User(userName, userPassword)
        dbRef.child("users").child("1").setValue(user)
    }
}
