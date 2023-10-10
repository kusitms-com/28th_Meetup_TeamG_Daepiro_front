package com.example.numberoneproject.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.ActivityLoginBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this,"0786b07056f57dd7e119842166e52498")

        binding.kakao.setOnClickListener{
            //startKaKaoLogin()
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
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }else{
            Log.e("log","is fail")
            UserApiClient.instance.loginWithKakaoAccount(this,callback = callback)

        }
    }
}