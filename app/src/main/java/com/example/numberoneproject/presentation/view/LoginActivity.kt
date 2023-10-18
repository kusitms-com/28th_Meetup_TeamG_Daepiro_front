package com.example.numberoneproject.presentation.view

import android.content.Intent
import android.media.MediaSession2.SessionCallback
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.numberoneproject.BuildConfig
import com.example.numberoneproject.R
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.databinding.ActivityLoginBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.viewmodel.KakaoLoginViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel:KakaoLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAutoLogin()
        binding.kakao.setOnClickListener{
            startKaKaoLogin()
        }
        observeTokenResponse()
    }

    private fun observeTokenResponse(){
        //collect를 통한 데이터 수집
        repeatOnStarted{
            viewModel.tokenResponse.collect{result->
                when(result){
                    is ApiResult.Success->{
                        //토큰의 성공적 전송
                        viewModel.saveToken(listOf("${result.data.accessToken}","${result.data.refreshToken}"))
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    is ApiResult.Failure->{
                        handleApiFailure(result)
                    }
                    else ->{
                    }
                }
            }
        }
    }

    private fun handleApiFailure(failure: ApiResult.Failure){
        when(failure){
            is ApiResult.Failure.HttpError->{
                Toast.makeText(this, "http error", Toast.LENGTH_SHORT).show()
            }
            is ApiResult.Failure.NetworkError->{
                Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show()
            }
            is ApiResult.Failure.UnknownApiError->{
                Toast.makeText(this, "unknown", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun startKaKaoLogin(){
        //카카오계정 로그인시 사용
        val callback: (OAuthToken?, Throwable?) -> Unit = {token, error->
            if(error != null){
                Log.e("kakao", "계정 로그인 실패${error}")
            }
            else if(token != null){
                viewModel.postKakaoToken("${token.accessToken}")
            }
        }
        //카카오톡 로그인시 사용
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk(this){token, error->
                if(error != null){
                    Log.e("kakao", "카카오톡 로그인 실패", error)
                    //의도적인 로그인 취소로 카카오계정 로그인 없이 로그인 취소 처리
                    if(error is ClientError && error.reason==ClientErrorCause.Cancelled){
                        return@loginWithKakaoTalk
                    }
                    //카카오톡 연결 카카오계정 없을때 카카오계정 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }else if(token != null){
                    val redirectUri = "${BuildConfig.BASE_URL}"
                    val clientId="${BuildConfig.KAKAO}"
                    val url = "https://kauth.kakao.com/oauth/authorize?client_id=$clientId&redirect_uri=$redirectUri&response_type=code"
                    viewModel.postKakaoToken("${token.accessToken}")
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
        }else{
            UserApiClient.instance.loginWithKakaoAccount(this,callback = callback)
        }
    }

    //자동로그인을 위한 체크
    private fun checkAutoLogin(){
        viewModel.checkIsLogin()
        lifecycleScope.launch{
            viewModel.isLogin.collectLatest { isLogin->
                if(isLogin==true){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}