package com.example.numberoneproject.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import com.example.numberoneproject.BuildConfig
import com.example.numberoneproject.R
import com.example.numberoneproject.data.model.LoginBody
import com.example.numberoneproject.databinding.ActivityLoginBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.util.Extensions.myLog
import com.example.numberoneproject.presentation.util.TokenManager
import com.example.numberoneproject.presentation.viewmodel.LoginViewModel
import com.example.numberoneproject.presentation.viewmodel.SampleViewModel
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var tokenManager: TokenManager
    val loginVM by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val naverClientId = BuildConfig.NAVER_CLIENT_ID
        val naverClientSecret = BuildConfig.NAVER_CLIENT_SECRET
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret, "네이버 로그인")

        binding.btnNaverLogin.setOnClickListener {
            setupNaverLogin()
        }

        loginVM.setLoginTokens(tokenManager)

        lifecycleScope.launch {
            myLog(tokenManager.accessToken.first())

        }
    }

    override fun initView() {
        tokenManager = TokenManager(this)

    }

    private fun setupNaverLogin() {
        var naverToken :String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
                binding.tvResult.text = "id: ${userId} \ntoken: ${naverToken}"
                Toast.makeText(this@LoginActivity, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()

                loginVM.userLogin(LoginBody(naverToken!!))
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@LoginActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        /** OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@LoginActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }
}