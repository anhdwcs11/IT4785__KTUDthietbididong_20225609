package com.example.it4785_11_24_2025

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class EmailAdapter(context: Context, emails: List<Email>) : ArrayAdapter<Email>(context, 0, emails) {

    private val colors = listOf(
        R.color.avatar_color_1,
        R.color.avatar_color_2,
        R.color.avatar_color_3,
        R.color.avatar_color_4,
        R.color.avatar_color_5
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_email, parent, false)

        val email = getItem(position)!!

        val senderInitial = view.findViewById<TextView>(R.id.sender_initial)
        val sender = view.findViewById<TextView>(R.id.sender)
        val subject = view.findViewById<TextView>(R.id.subject)
        val body = view.findViewById<TextView>(R.id.body)
        val time = view.findViewById<TextView>(R.id.time)

        senderInitial.text = email.sender.first().toString()
        sender.text = email.sender
        subject.text = email.subject
        body.text = email.body
        time.text = email.time

        val colorRes = colors[position % colors.size]
        val color = ContextCompat.getColor(context, colorRes)
        (senderInitial.background as? GradientDrawable)?.setColor(color)

        return view
    }
}