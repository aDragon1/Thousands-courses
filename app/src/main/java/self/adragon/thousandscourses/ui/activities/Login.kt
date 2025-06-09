package self.adragon.thousandscourses.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import self.adragon.thousandscourses.R
import self.adragon.thousandscourses.utils.Utils


class Login : AppCompatActivity(), OnClickListener {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var loginButton: Button
    private lateinit var authTextView: TextView

    private lateinit var okImageButton: ImageButton
    private lateinit var vkImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setViews()

        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setResult(Activity.RESULT_CANCELED, Intent())
                finish()
                isEnabled = false
                OnBackPressedDispatcher().onBackPressed()
            }
        }

    }

    private fun setViews() {
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        loginButton = findViewById(R.id.loginButton)
        vkImageButton = findViewById(R.id.vkImageButton)
        okImageButton = findViewById(R.id.okImageButton)

        loginButton.setOnClickListener(this)
        vkImageButton.setOnClickListener(this)
        okImageButton.setOnClickListener(this)

        authTextView = findViewById(R.id.authTextView)
        val registerSpan = createSpan("#12B956")
        val forgotPassSpan = createSpan("#12B956")
        val spannedString = SpannableStringBuilder("Нету аккаунта? Регистрация\nЗабыл пароль")
        spannedString.setSpan(registerSpan, 15, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannedString.setSpan(forgotPassSpan, 26, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        authTextView.text = spannedString
        authTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun createSpan(textColor: String) = object : ClickableSpan() {
        override fun onClick(widget: View) =
            Toast.makeText(this@Login, "Is not implemented yet", Toast.LENGTH_SHORT).show()


        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.apply {
                color = textColor.toColorInt() // Color.parseColor(textColor)
                isUnderlineText = false
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginButton -> {
                val email = emailEditText.text.toString()
                val pass = passwordEditText.text.toString()
                val isEmailValid = Utils.isEmailValid(email)

                if (email.isBlank() || !isEmailValid) {
                    Toast.makeText(this, "Введите корректный email", Toast.LENGTH_SHORT).show()
                    return
                }
                if (pass.isBlank()) {
                    Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
                    return
                }
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }

            R.id.vkImageButton -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, "https://vk.com/".toUri())
                startActivity(browserIntent)
            }

            R.id.okImageButton -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, "https://ok.ru/".toUri())
                startActivity(browserIntent)
            }
        }
    }
}