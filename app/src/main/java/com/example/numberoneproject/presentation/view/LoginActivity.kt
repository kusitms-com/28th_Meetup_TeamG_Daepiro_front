package com.example.numberoneproject.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private lateinit var tokenManager : TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this@LoginActivity

    }

    override fun setupInit() {
        val naverClientId = BuildConfig.NAVER_CLIENT_ID
        val naverClientSecret = BuildConfig.NAVER_CLIENT_SECRET
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret, "네이버 로그인")

        tokenManager = TokenManager(this)
    }

    override fun subscribeUi() {
        repeatOnStarted {
            loginVM.loginErrorState.collectLatest { response ->
                response?.let {
                   when(it) {
                       is ApiResult.Failure.HttpError -> {
                           when(it.code) {
                               403 -> {
                                   showToast("LoginActivity 403 에러 펑")
                               }
                               404 -> {
                                   showToast("LoginActivity 404 에러 펑")
                               }
                               else -> { showToast("LoginActivity ${it.code}번 에러 펑") }
                           }
                       }
                       else -> showToast("네트워크 상태 확인")
                   }
                }
            }
        }

        repeatOnStarted {
            tokenManager.accessToken.collectLatest {
                Log.d("taag accessToken", it)
                if (it.isNotEmpty()) {
                    /** 이렇게 하니까 일단 두 번 MainActivity 가던 것 고쳐짐 **/
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                    Log.d("taag", "아직 LoginActivity collect 살아있음")
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