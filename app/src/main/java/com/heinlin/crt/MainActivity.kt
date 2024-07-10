package com.heinlin.crt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.heinlin.crt.base.BaseControl
import com.heinlin.crt.databinding.ActivityMainBinding
import com.heinlin.crt.fragments.CalculatorFragment
import com.heinlin.crt.fragments.RandomFragment
import com.heinlin.crt.fragments.TictactoeFragment

class MainActivity : BaseControl<ActivityMainBinding>() {

    override val pageTitle: String get() = "sss"

    override fun setUpViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(3000)
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //onBackPressedDispatcher.addCallback(this) { handleBackPress() }

        this.supportActionBar?.hide()
        this.setupTabs()
    }

    private val calcuFragment: CalculatorFragment by lazy { CalculatorFragment() }
    private val randomFragment: RandomFragment by lazy { RandomFragment() }
    private val tictactoeFragment: TictactoeFragment by lazy { TictactoeFragment() }

    private fun setupTabs() {
        binding.bottomNavigation.menu.clear()

        Tabs.entries.forEach {
            binding.bottomNavigation.menu.add(
                Menu.NONE,
                it.id,
                Menu.NONE,
                it.title
            ).icon = ResourcesCompat.getDrawable(resources, it.icon, null)
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            selectedTab = Tabs.getTabByItemId(it.itemId)
            true
        }

        binding.bottomNavigation.setOnItemReselectedListener {
            selectedTab?.let {
                // getFragmentByTab(it).clearBackStack()
            }
        }

        selectedTab = Tabs.entries.first()
    }

    private var selectedTab: Tabs? = null
        set(value) {
            if (field == value || value == null) return

            binding.bottomNavigation.menu.findItem(value.id).isChecked = true

            field?.let { hideFragment(getFragmentByTab(it)) }
            showFragment(getFragmentByTab(value))

            Log.d("TAG", "Set selectedTab: $value")
            field = value
        }
        get() {
            Log.d("TAG", "Get selectedTab: $field")
            return field
        }

    private fun getFragmentByTab(tab: Tabs): Fragment {
        return when (tab) {
            Tabs.CALCULATOR -> calcuFragment
            Tabs.RANDOM -> randomFragment
            Tabs.TICTACTOE -> tictactoeFragment
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.findFragmentByTag(fragment.javaClass.name)?.let {
            supportFragmentManager
                .beginTransaction()
                .show(it)
                .commit()
        } ?: run {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, fragment, fragment.javaClass.name)
                .commit()
        }
    }

    private fun hideFragment(fragment: Fragment) {
        supportFragmentManager.findFragmentByTag(fragment.javaClass.name)?.let {
            supportFragmentManager
                .beginTransaction()
                .hide(it)
                .commit()
        }
    }

    private enum class Tabs(val id: Int, val title: Int, val icon: Int) {
        CALCULATOR(1, R.string.navigation_calculator, R.drawable.ic_calculator),
        RANDOM(2, R.string.navigation_random, R.drawable.ic_random2),
        TICTACTOE(3, R.string.navigation_tictactoe, R.drawable.ic_tictactoe);

        companion object {
            fun getTabByItemId(itemId: Int): Tabs? {
                return Tabs.entries.find { it.id == itemId }
            }
        }
    }


}