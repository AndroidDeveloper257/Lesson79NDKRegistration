package com.example.lesson79ndkregistration.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lesson79ndkregistration.R
import com.example.lesson79ndkregistration.database.AppDatabase
import com.example.lesson79ndkregistration.database.User
import com.example.lesson79ndkregistration.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
        val l = arguments?.getLong("user_id")
        val list = AppDatabase.getInstance(requireContext()).userDao().listUsers()
        list.forEach {
            if (it.id == l) {
                user = it
            }
        }
        binding.text.text = "Welcome\n${user.fullName}"
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}