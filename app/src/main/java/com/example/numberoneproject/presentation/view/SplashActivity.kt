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
import com.example.numberoneproject.databinding.ActivitySplashBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val DURATION_TIME = 2000L    // 스플래시 화면 지연시간
    private lateinit var cm : ConnectivityManager
    @Inject lateinit var tokenManager: TokenManager

    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        // 네트워크가 연결된 경우
        override fun onAvailable(network: Network) {
            Handler(Looper.getMainLooper()).postDelayed({
                checkAutoLogin()
            }, DURATION_TIME)
        }

        // 네트워크가 연결되지 않은 경우
        override fun onLost(network: Network) {
            Toast.makeText(this@SplashActivity,"네트워크 연결 실패", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val builder = NetworkRequest.Builder()
        cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(builder.build(),networkCallBack)
    }

    /** 자동로그인 가능한지 확인 **/
    fun checkAutoLogin(){
        repeatOnStarted {
            tokenManager.accessToken.collectLatest {
                if(it.isNotEmpty()){
                    val intent = Intent(this@SplashActivity,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this@SplashActivity,LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cm.unregisterNetworkCallback(networkCallBack)
    }
}