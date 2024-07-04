package com.heinlin.crt.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.heinlin.crt.MainActivity

abstract class BaseControl<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB
    abstract val pageTitle: String

    abstract fun setUpViewBinding(layoutInflater: LayoutInflater): VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TAG", "pageTitle = $pageTitle")
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(this is MainActivity)
    }

    fun onOptionItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}