package com.example.numberoneproject.presentation.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.numberoneproject.R
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.util.TokenManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val DURATION_TIME = 2000L    // 스플래시 화면 지연시간
    private lateinit var cm2 : ConnectivityManager
    private val tokenManager: TokenManager = TokenManager(this)

    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // 네트워크가 정상적인 경우
            Toast.makeText(this@SplashActivity, "연결성공 $network", Toast.LENGTH_SHORT).show()
            checkLogin()
            Handler(Looper.getMainLooper()).postDelayed({
                //startActivity(Intent( this@SplashActivity,LoginActivity::class.java))
                finish()
            }, DURATION_TIME)
        }

        override fun onLost(network: Network) {
            // 네트워크가 연결되지 않은 경우
            Toast.makeText(this@SplashActivity,"연결실패", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val builder = NetworkRequest.Builder()

        cm2 = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm2.registerNetworkCallback(builder.build(),networkCallBack)
    }

    //자동로그인 가능한지 확인
    fun checkLogin(){
        lifecycleScope.launch{
            tokenManager.accessToken.collectLatest {
                if(it.isNotEmpty()){
                    val intent = Intent(this@SplashActivity,MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this@SplashActivity,LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cm2 = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm2.unregisterNetworkCallback(networkCallBack)
    }
}