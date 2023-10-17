package com.example.numberoneproject.presentation.view

import android.content.Intent
import android.media.MediaSession2.SessionCallback
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.numberoneproject.BuildConfig
import com.example.numberoneproject.R
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.databinding.ActivityLoginBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.kakao.setOnClickListener{
            startKaKaoLogin()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("hey", "why")
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data
        val code = uri?.getQueryParameter("code") ?: "code not found"
        Log.e("server","sdfsdf")
        Log.e("server",code)
        if (code != "code not found") {
            //인가코드 서버 전달
            Log.e("hey", "$code")
        }
        else{
            Log.e("hey", "no")
        }
    }
    fun startKaKaoLogin(){
        //카카오계정 로그인시 사용
        val callback: (OAuthToken?, Throwable?) -> Unit = {token, error->
            if(error != null){
                Log.e("kakao", "계정 로그인 실패${error}")
            }
            else if(token != null){
                Log.i("kakao","계정 로그인 성공 ${token.accessToken}")
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
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
                    Log.i("kakao", "카카오톡 로그인 성공${token.accessToken}")
//                    val intent= Intent(this,MainActivity::class.java)
//                    startActivity(intent)
                    //val BASE_URL = BuildConfig.BASE_URL
                    val redirectUri = "${BuildConfig.BASE_URL}"
                    val clientId="${BuildConfig.KAKAO}"
                    val url = "https://kauth.kakao.com/oauth/authorize?client_id=$clientId&redirect_uri=$redirectUri&response_type=code"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
        }else{
            UserApiClient.instance.loginWithKakaoAccount(this,callback = callback)
        }
    }

//    private fun kakaoUnlink() {
//        UserApiClient.instance.unlink { error->
//            if(error != null){
//                Log.e("unlink", "shut down: ${error}")
//            }
//            else{
//                Log.e("unlink", "sdk에서 토큰 삭제")
//                val intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }
}