package com.example.minimoneybox.activities

import android.animation.Animator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.minimoneybox.R
import com.example.minimoneybox.entities.LoginJSON
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.regex.Pattern


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var btn_sign_in : Button
    lateinit var til_email : TextInputLayout
    lateinit var et_email : EditText
    lateinit var til_password : TextInputLayout
    lateinit var et_password : EditText
    lateinit var til_name : TextInputLayout
    lateinit var et_name : EditText
    lateinit var pigAnimation : LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        setupAnimation()
    }

    private fun setupViews() {
        btn_sign_in = findViewById(R.id.btn_sign_in)
        til_email = findViewById(R.id.til_email)
        et_email = findViewById(R.id.et_email)
        til_password = findViewById(R.id.til_password)
        et_password = findViewById(R.id.et_password)
        til_name = findViewById(R.id.til_name)
        et_name = findViewById(R.id.et_name)
        pigAnimation = findViewById(R.id.animation)

        btn_sign_in.setOnClickListener {
            if (allFieldsValid()) {
                Toast.makeText(this, R.string.input_valid, Toast.LENGTH_LONG).show()
                loginRequest()
            }
        }
    }

    private fun loginRequest() {
        val client = OkHttpClient()
        val url = "https://api-test01.moneyboxapp.com/users/login"

        val email = et_email.text.toString()
        val password = et_password.text.toString()
        val name = et_name.text.toString()

        val formBuilder = FormBody.Builder()
            .add("Email", email)
            .add("Password", password)
            .add("Idfa", name)

        val formBody = formBuilder.build()

        val request = Request.Builder()
            .url(url)
            .addHeader("AppId","3a97b932a9d449c981b595")
            .addHeader("Content-Type","application/json")
            .addHeader("appVersion",	"5.10.0")
            .addHeader("apiVersion",	"3.0.0")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {

            @Throws(IOException::class)

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val myResponse = response.body()!!.string()
                    val gson = Gson()
                    val jsonResponse = gson.fromJson(myResponse, LoginJSON::class.java)
                    var bearerToken = jsonResponse.session.bearerToken
                    bearerToken = "Bearer " + bearerToken
                    userProfileRequest(bearerToken, name)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

    // Matteo
    private fun userProfileRequest(token : String, name : String) {
        val client = OkHttpClient()
        val url = "https://api-test01.moneyboxapp.com/investorproducts"

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", token)
            .addHeader("AppId","3a97b932a9d449c981b595")
            .addHeader("Content-Type","application/json")
            .addHeader("appVersion",	"5.10.0")
            .addHeader("apiVersion",	"3.0.0")
            .build()

        client.newCall(request).enqueue(object : Callback {

            @Throws(IOException::class)

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val myResponse = response.body()!!.string()

                    val intent = Intent(this@LoginActivity, UserAccountActivity::class.java)
                    if (name != "") {
                        intent.putExtra("name", name)
                    }
                    intent.putExtra("json", myResponse)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@LoginActivity, R.string.login_error, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun allFieldsValid() : Boolean {
        var allValid = false

        if (Pattern.matches(EMAIL_REGEX, et_email.text.toString())) {
            til_email.error = null
        } else {
            til_email.error = getString(R.string.email_address_error)
        }

        if (Pattern.matches(PASSWORD_REGEX, et_password.text.toString())) {
            allValid = true
            til_password.error = null
        } else {
            til_password.error = getString(R.string.password_error)
            allValid = false
        }

        if (et_name.text.toString().isNotEmpty()) {
            if (Pattern.matches(NAME_REGEX, et_name.text.toString())) {
                allValid = true
                til_name.error = null
            } else {
                til_name.error = getString(R.string.full_name_error)
                allValid = false
            }
        } else {
            til_name.error = null
        }

        return allValid
    }

    private fun setupAnimation() {
        pigAnimation.playAnimation()
        pigAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {
                pigAnimation.setMinAndMaxFrame(131, 158)
            }
        })
    }

    companion object {
        val EMAIL_REGEX = "[^@]+@[^.]+\\..+"
        val NAME_REGEX = "[a-zA-Z]{6,30}"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z]).{10,50}$"
        val firstAnim = 0 to 109
        val secondAnim = 131 to 158
    }
}
