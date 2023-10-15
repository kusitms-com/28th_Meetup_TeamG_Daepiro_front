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
import android.widget.Toast
import com.example.numberoneproject.R

class SplashActivity : AppCompatActivity() {
    private val DURATION_TIME = 2000L
    private lateinit var cm2 : ConnectivityManager

    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // 네트워크가 연결될 때 호출됩니다.
            Toast.makeText(this@SplashActivity, "연결성공 $network", Toast.LENGTH_SHORT).show()
        }

        override fun onLost(network: Network) {
            // 네트워크가 끊길 때 호출됩니다.
            Toast.makeText(this@SplashActivity,"연결실패", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val builder = NetworkRequest.Builder()

        cm2 = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm2.registerNetworkCallback(builder.build(),networkCallBack)

        if(isConnectInternet() != "null") {
            // 연결 성공
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent( this,LoginActivity::class.java))
                finish()
            }, DURATION_TIME)
        } else {
            // 연결 실패
//            Handler(Looper.getMainLooper()).postDelayed({
//                startActivity(Intent( this,LoginActivity::class.java))
//                finish()
//            }, DURATION_TIME)
        }
    }

    private fun isConnectInternet(): String { // 인터넷 연결 체크 함수
        val cm: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        return networkInfo.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        cm2 = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm2.unregisterNetworkCallback(networkCallBack)
    }
}