package com.example.sanpchatclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {


    var emailEditText : EditText? = null;
    var passwordEditText : EditText? = null;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

emailEditText = findViewById(R.id.emailfield);
    passwordEditText = findViewById(R.id.passwordfield);

    }


    fun goClicked(view: View){




    }





 }