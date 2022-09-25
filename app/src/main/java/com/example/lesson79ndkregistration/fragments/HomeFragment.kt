package com.example.lesson79ndkregistration.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.lesson79ndkregistration.R
import com.example.lesson79ndkregistration.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val navOptions: NavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter)
            .setExitAnim(R.anim.exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .setPopExitAnim(R.anim.pop_exit)
            .build()
        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment, null, navOptions)
        }
        binding.registerBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("isLogin", false)
            findNavController().navigate(R.id.registerFragment, null, navOptions)
        }
        return binding.root
    }
}