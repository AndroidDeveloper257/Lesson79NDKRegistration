package com.example.lesson79ndkregistration.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.lesson79ndkregistration.R
import com.example.lesson79ndkregistration.database.AppDatabase
import com.example.lesson79ndkregistration.database.User
import com.example.lesson79ndkregistration.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var database: AppDatabase
    private lateinit var userList: ArrayList<User>
    private lateinit var navOptions: NavOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())
        navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter)
            .setExitAnim(R.anim.exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .setPopExitAnim(R.anim.pop_exit)
            .build()
        setFragmentResultListener("request_key") { requestKey, bundle ->
            username = bundle.getString("username").toString()
            password = bundle.getString("password").toString()
            binding.usernameEt.setText(username)
            binding.passwordEt.setText(password)
        }
        binding.loginBtn.setOnClickListener {
            username = binding.usernameEt.text.toString()
            password = binding.passwordEt.text.toString()
            if (username.isEmpty() or password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Username or password is empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val userId = getUserId()
                if (userId > 0) {
                    login(userId)
                } else {
                    if (userId == -1L) {
                        Toast.makeText(
                            requireContext(),
                            "There is no one has registered yet",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Username or password is wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.createNewTv.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("isLogin", true)
            findNavController().navigate(R.id.registerFragment, bundle, navOptions)
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun login(userId: Long) {
        val bundle = Bundle()
        bundle.putLong("user_id", userId)
        findNavController().navigate(R.id.welcomeFragment, bundle, navOptions)
    }

    private fun getUserId(): Long {
        val encrypted = encrypt(password)
        Log.d(TAG, "getUserId: encryptedPassword -> $encrypted")
        userList = ArrayList(database.userDao().listUsers())
        if (userList.isEmpty()) return -1
        userList.forEach { user ->
            if (user.password == encrypted) return user.id
        }
        return 0
    }

    external fun encrypt(password: String): String

    companion object {
        private const val TAG = "LoginFragment"

        init {
            System.loadLibrary("lesson79ndkregistration")
        }
    }
}