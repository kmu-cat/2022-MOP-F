package com.example.bori

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Main : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeNavFragment(Home())

        val bottomNav = findViewById<BottomNavigationView>(R.id.main_bottomNav)
        bottomNav.setSelectedItemId(R.id.navigation_home);

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    changeNavFragment(Home())
                }
                R.id.navigation_certifyingShot -> {
                    changeNavFragment(CertifyingShot())
                }
                R.id.navigation_setting -> {
                    changeNavFragment(Setting())
                }
                R.id.navigation_inventory -> {
                    changeNavFragment(CatSettingFragment())
                }
                R.id.navigation_bucketList -> {
                    changeNavFragment(MyBucket())
                }
            }
            true
        }
    }

    fun changeNavFragment(fragment: Fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.main_fragmentLayout, fragment)
            .commit()
    }

    fun changeMyBucketFragment(index: Int){
        when(index){
            0 -> {
                changeNavFragment(MyBucket())
            }

            1 -> {
               changeNavFragment(RecommendBucket())
            }
        }
    }
}