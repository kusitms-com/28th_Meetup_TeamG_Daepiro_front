package com.example.numberoneproject.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.numberoneproject.BuildConfig
import com.example.numberoneproject.R
import com.example.numberoneproject.data.model.TokenRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.databinding.ActivityLoginBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.util.TokenManager
import com.example.numberoneproject.presentation.viewmodel.LoginViewModel
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    val loginVM by viewModels<LoginViewModel>()
    var naverLoginToken :String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this@LoginActivity

    }

    override fun setupInit() {
        val naverClientId = BuildConfig.NAVER_CLIENT_ID
        val naverClientSecret = BuildConfig.NAVER_CLIENT_SECRET
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret, "네이버 로그인")

    }

    override fun subscribeUi() {
        repeatOnStarted {
            loginVM.loginErrorState.collectLatest { response ->
                response?.let {
                   when(it) {
                       is ApiResult.Failure.HttpError -> {
                           when(it.code) {
                               403 -> {
                                   showToast("403 에러 펑")
                               }
                               404 -> {
                                   showToast("404 에러 펑")
                               }
                               else -> { showToast("${it.code}번 에러 펑") }
                           }
                       }
                       else -> showToast("네트워크 상태 확인")
                   }
                }
            }
        }

        repeatOnStarted {
            TokenManager(this@LoginActivity).accessToken.collectLatest {
                if (it.isNotEmpty()) {
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    fun setupNaverLogin(view: View) {
        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                loginVM.userNaverLogin(TokenRequestBody(naverLoginToken!!))
            }
            override fun onFailure(httpStatus: Int, message: String) {

            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                naverLoginToken = NaverIdLoginSDK.getAccessToken()
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }
}