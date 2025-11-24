package com.example.it4785_11_24_2025

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

data class Email(val sender: String, val subject: String, val body: String, val time: String)

val emails = listOf(
    Email("Edurila.com", "\$19 Only (First 10 spots) - Bestselling...", "Are you looking to Learn Web Designin...", "12:34 PM"),
    Email("Chris Abad", "Help make Campaign Monitor better", "Let us know your thoughts! No Images...", "11:22 AM"),
    Email("Tuto.com", "8h de formation gratuite et les nouvea...", "Photoshop, SEO, Blender, CSS, WordPre...", "11:04 AM"),
    Email("support", "Société Ovh : suivi de vos services - hp...", "SAS OVH - http://www.ovh.com 2 rue K...", "10:26 AM"),
    Email("Matt from lonic", "The New lonic Creator Is Here!", "Announcing the all-new Creator, builo...", "10:25 AM"),
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Inbox"

        val listView = findViewById<ListView>(R.id.listView)
        val adapter = EmailAdapter(this, emails)
        listView.adapter = adapter
    }
}