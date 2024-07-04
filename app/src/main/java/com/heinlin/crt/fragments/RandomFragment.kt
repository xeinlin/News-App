package com.heinlin.crt.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heinlin.crt.R
import com.heinlin.crt.base.BaseFragment
import com.heinlin.crt.databinding.FragmentCalculatorBinding
import com.heinlin.crt.databinding.FragmentRandomBinding

class RandomFragment : BaseFragment<FragmentRandomBinding>() {

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRandomBinding {
        return FragmentRandomBinding.inflate(inflater, container, false)
    }
}