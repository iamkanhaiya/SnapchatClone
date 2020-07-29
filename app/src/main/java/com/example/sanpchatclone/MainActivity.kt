package com.example.sanpchatclone

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {


    var emailEditText : EditText? = null;
    var passwordEditText : EditText? = null;
    val mAuth = FirebaseAuth.getInstance()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

emailEditText = findViewById(R.id.emailfield);
    passwordEditText = findViewById(R.id.passwordfield);

        if (mAuth.currentUser!=null){

           logIn()




        }




    }


    fun goClicked(view: View){

// check if we can log in the user
// sign up the user


        mAuth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString()).addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                               logIn()

                            } else {
                               Toast.makeText(this,"Login failed ! Please try again",Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }

                }

                // ...
            }


    }

fun logIn(){

// move to next activity
val intent = Intent(this, SanpsActivity::class.java)
startActivity(intent)
}










 }