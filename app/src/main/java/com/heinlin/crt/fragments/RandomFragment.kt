package com.heinlin.crt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heinlin.crt.base.BaseFragment
import com.heinlin.crt.databinding.FragmentRandomBinding

class RandomFragment : BaseFragment<FragmentRandomBinding>() {

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRandomBinding {
        return FragmentRandomBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRandom.setOnClickListener {
            val minVal = binding.edtMin.text.toString().toInt()
            val maxVal = binding.edtMax.text.toString().toInt()
            val mResult = (minVal..maxVal).random()
            binding.tvResult.text = mResult.toString()
        }
    }
}