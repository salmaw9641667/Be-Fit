package uk.ac.tees.mad.W9641667

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Locale

class Workout : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val muscles = listOf("abdominals", "abductors", "adductors", "biceps", "calves", "chest", "forearms", "glutes", "hamstrings", "lats", "lower_back", "middle_back", "neck", "quadriceps", "traps", "triceps")
        val tabs: TabLayout = findViewById(R.id.tabs)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = muscles.size
            override fun createFragment(position: Int): Fragment = MuscleFragment(muscles[position])
        }

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = muscles[position].capitalize(Locale.ROOT)
        }.attach()
    }
}
