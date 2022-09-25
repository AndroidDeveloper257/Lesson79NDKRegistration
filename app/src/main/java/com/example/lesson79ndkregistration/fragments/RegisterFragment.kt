package com.example.lesson79ndkregistration.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.lesson79ndkregistration.R
import com.example.lesson79ndkregistration.database.AppDatabase
import com.example.lesson79ndkregistration.database.User
import com.example.lesson79ndkregistration.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())
        val isLogin = arguments?.getBoolean("isLogin")
        binding.registerBtn.setOnClickListener {
            val fullName = binding.fullNameEt.text.toString()
            val username = binding.usernameEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (fullName.isEmpty() or username.isEmpty() or password.isEmpty()) Toast.makeText(
                requireContext(),
                "fullname or username or password is empty ",
                Toast.LENGTH_SHORT
            ).show() else {
                if (checkData(username, password)) {
                    val user = User(
                        0,
                        fullName,
                        username,
                        encrypt(password)
                    )
                    database.userDao().addUser(user)
                    if (isLogin != null) {
                        loginUser(user, isLogin)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "This username and password pair is busy",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun loginUser(user: User, login: Boolean) {
        if (login) {
            val bundle = Bundle()
            bundle.putString("username", user.username)
            bundle.putString("password", user.password)
            setFragmentResult("request_key", bundle)
            findNavController().popBackStack()
        } else {
            val list = database.userDao().listUsers()
            list.forEach {
                if (it.password == user.password && it.username == user.username) {
                    val bundle = Bundle()
                    bundle.putLong("user_id", it.id)
                    val navOptions: NavOptions = NavOptions.Builder()
                        .setEnterAnim(R.anim.enter)
                        .setExitAnim(R.anim.exit)
                        .setPopEnterAnim(R.anim.pop_enter)
                        .setPopExitAnim(R.anim.pop_exit)
                        .build()
                    findNavController().navigate(R.id.welcomeFragment, bundle, navOptions)
                }
            }
        }
    }

    private fun checkData(username: String, password: String): Boolean {
        val list = database.userDao().listUsers()
        list.forEach {
            if (it.username == username && it.password == password) return false
        }
        return true
    }

    external fun encrypt(password: String): String

    companion object {
        init {
            System.loadLibrary("lesson79ndkregistration")
        }
    }
}