package com.daepiro.numberoneproject.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.databinding.ActivityMainBinding
import com.daepiro.numberoneproject.presentation.base.BaseActivity
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.util.TokenManager
import com.daepiro.numberoneproject.presentation.view.login.LoginActivity
import com.daepiro.numberoneproject.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    val loginVM by viewModels<LoginViewModel>()
    @Inject lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupInit() {
        initNavigation()

        loginVM.loginTest()

        if (intent.getStringExtra("tokenFromFamily")?.isNotEmpty() == true) {
            showToast("초대받고옴" + intent.getStringExtra("tokenFromFamily").toString())
        } else {
            showToast("그냥 옴")
        }
    }

    override fun subscribeUi() {
        /** 유저 토큰정보 확인 용도 **/
        repeatOnStarted {
            val accessToken = tokenManager.accessToken.first()
            val refreshToken = tokenManager.refreshToken.first()

            Log.d("taag AccessToken", accessToken)
            Log.d("taag RefreshToken", refreshToken)
        }

        repeatOnStarted {
            loginVM.loginErrorState.collectLatest { response ->
                response?.let {
                    when(it) {
                        is ApiResult.Failure.HttpError -> {
                            when(it.code) {
                                403 -> {    // AccessToken이 만료된 경우
                                    loginVM.refreshAccessToken()
                                    showToast("403에러 : MainActivity에서 Token 갱신")
                                }
                                404 -> {    // RefreshToken이 만료된 경우
                                    showToast("MainActivity 404 에러 펑")
                                    tokenManager.writeLoginTokens("", "")

                                    finish()
                                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                }
                                else -> { showToast("MainActivity ${it.code}번 에러 펑") }
                            }
                        }
                        else -> showToast("네트워크 상태를 확인하세요.")
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
            if (destination.id == R.id.homeFragment || destination.id==R.id.communityFragment || destination.id == R.id.familyFragment
                || destination.id == R.id.fundingFragment || destination.id == R.id.mypageFragment) {
                binding.bottomNavigationBox.visibility = View.VISIBLE

            } else {
                binding.bottomNavigationBox.visibility = View.GONE
            }
        }
    }
}