package com.example.numberoneproject.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.numberoneproject.R
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.databinding.ActivityMainBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.util.TokenManager
import com.example.numberoneproject.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    val loginVM by viewModels<LoginViewModel>()
    private val tokenManager: TokenManager = TokenManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupInit() {
        initNavigation()

        loginVM.loginTest()
    }

    override fun subscribeUi() {
        repeatOnStarted {
            loginVM.errorState.collectLatest { response ->
                response?.let {
                    when(it) {
                        is ApiResult.Failure.HttpError -> {
                            when(it.code) {
                                403 -> {    // AccessToken이 만료된 경우
                                    showToast("403 에러 펑")

                                    loginVM.refreshAccessToken()
                                    showToast("ㄱㅊ 갱신하고 다시 썼음")
                                }
                                404 -> {    // RefreshToken이 만료된 경우
                                    showToast("404 에러 펑")

                                    tokenManager.writeLoginTokens("", "")

                                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                    finish()
                                }
                                else -> { showToast("${it.code}번 에러 펑") }
                            }
                        }
                        else -> showToast("네트워크 상태 확인")
                    }
                }
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.newsFragment
                || destination.id == R.id.fundingFragment || destination.id == R.id.mypageFragment) {
                binding.bottomNavigationBar.visibility = View.VISIBLE

            } else {
                binding.bottomNavigationBar.visibility = View.GONE
            }
        }
    }
}