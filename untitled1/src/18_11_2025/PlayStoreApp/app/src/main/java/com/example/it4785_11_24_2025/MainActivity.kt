package com.example.it4785_11_24_2025

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainRecyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        mainRecyclerView.layoutManager = LinearLayoutManager(this)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.selectedItemId = R.id.nav_apps

        val categories = listOf(
            Category("Suggested for you", listOf(
                App("Mech Assemble: Zombie Swarm", R.drawable.android_icon, "Action • Role Playing", 4.8f, "624 MB", true),
                App("MU: Hồng Hoà Đao", R.drawable.android_icon, "Role Playing", 4.8f, "339 MB", true),
                App("War Inc: Rising", R.drawable.android_icon, "Strategy • Tower defense", 4.9f, "231 MB", true)
            )),
            Category("Recommended for you", listOf(
                App("Suno - AI Music &", R.drawable.android_icon),
                App("Claude by", R.drawable.android_icon),
                App("DramaBox -", R.drawable.android_icon),
                App("Picsart", R.drawable.android_icon)
            )),
            Category("New & updated games", listOf(
                App("Game 1", R.drawable.android_icon),
                App("Game 2", R.drawable.android_icon),
                App("Game 3", R.drawable.android_icon)
            ))
        )

        mainRecyclerView.adapter = CategoryAdapter(categories)
    }
}