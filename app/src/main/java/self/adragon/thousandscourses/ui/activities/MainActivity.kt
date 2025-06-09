package self.adragon.thousandscourses.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import self.adragon.thousandscourses.R
import self.adragon.thousandscourses.data.database.CourseDatabase
import self.adragon.thousandscourses.ui.adapters.ViewPagerAdapter
import self.adragon.thousandscourses.ui.fragments.Account
import self.adragon.thousandscourses.ui.fragments.Favorite
import self.adragon.thousandscourses.ui.fragments.Home

private const val APP_PREFERENCES = "app_setting"
private const val APP_PREFERENCES_DATABASE_EXIST = "isDatabaseExist"
private const val APP_PREFERENCES_USER_LOGGED_IN = "isUserLoggedIn"

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        if (!isUserLoggedIn()) initializeLogin()

        mainViewPager2 = findViewById(R.id.mainViewPager2)
        tabLayout = findViewById(R.id.tabLayout)

        val fragments = listOf(Home(), Favorite(), Account())
        val icons = listOf(
            R.drawable.ic_home,
            R.drawable.ic_favorite,
            R.drawable.ic_account
        )
        // Hack, but whatever, not enough items to memory management hell
        val selectedIcons = listOf(
            R.drawable.ic_home_selected,
            R.drawable.ic_favorite_selected,
            R.drawable.ic_account_selected
        )
        mainViewPager2.apply {
            adapter = ViewPagerAdapter(fragments, this@MainActivity)
            isUserInputEnabled = false
        }
        initializeTabLayout(icons, selectedIcons)
    }

    private fun isUserLoggedIn() = settings.getBoolean(APP_PREFERENCES_USER_LOGGED_IN, false)
    private fun initializeLogin() {
        val loginLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    settings.edit(commit = true) {
                        putBoolean(APP_PREFERENCES_USER_LOGGED_IN, true)
                    }
                    recreate()
                } else finish()
            }

        val intent = Intent(this, Login::class.java)
        loginLauncher.launch(intent)
    }

    private fun initializeTabLayout(icons: List<Int>, selectedIcons: List<Int>) {
        // Set all tabs
        TabLayoutMediator(tabLayout, mainViewPager2) { tab, i ->
            val tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
            val tabIcon = tabView.findViewById<ImageView>(R.id.tabIcon)
            tabIcon.setImageResource(if (i in icons.indices) icons[i] else selectedIcons.first())

            tab.customView = tabView
        }.attach()

        // flip-flop selected/unselected
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val i = tab?.position ?: -1
                val tabView =
                    LayoutInflater.from(this@MainActivity).inflate(R.layout.custom_tab, null)
                val tabIcon = tabView.findViewById<ImageView>(R.id.tabIcon)
                tabIcon.setImageResource(if (i in icons.indices) selectedIcons[i] else selectedIcons.first())

                tab?.customView = tabView
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val i = tab?.position ?: -1
                val tabView =
                    LayoutInflater.from(this@MainActivity).inflate(R.layout.custom_tab, null)
                val tabIcon = tabView.findViewById<ImageView>(R.id.tabIcon)
                tabIcon.setImageResource(if (i in icons.indices) icons[i] else selectedIcons.first())

                tab?.customView = tabView
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Set the first one
        val tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
        val tabIcon = tabView.findViewById<ImageView>(R.id.tabIcon)
        tabIcon.setImageResource(selectedIcons.first())
        tabLayout.getTabAt(0)?.customView = tabView
    }
}