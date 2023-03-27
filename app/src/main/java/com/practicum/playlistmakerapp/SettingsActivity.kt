package com.practicum.playlistmakerapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val homeButton = findViewById<Button>(R.id.Home)
        val shareButton = findViewById<LinearLayout>(R.id.shareButton)
        val supportButton = findViewById<LinearLayout>(R.id.supportButton)
        val termsButton = findViewById<LinearLayout>(R.id.termsButton)


        shareButton.setOnClickListener {
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.yandex_practicum_android_dev_url))
                type = "text/plain"
                startActivity(this)
            }
        }

        supportButton.setOnClickListener {
            Intent().apply {
                action = Intent.ACTION_SENDTO
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.contact_email)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_email_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.support_email_text_body))
                startActivity(Intent.createChooser(this, "Sending email"))
            }
        }

        termsButton.setOnClickListener {
            val url = Uri.parse(getString(R.string.support_yandex_practicum_offer))
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }

        homeButton.setOnClickListener {
            finish()
        }
    }
}