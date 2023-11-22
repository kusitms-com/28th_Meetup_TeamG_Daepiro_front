package com.daepiro.numberoneproject.presentation.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.BuildConfig
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.TokenRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.databinding.FragmentLoginBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import com.daepiro.numberoneproject.presentation.util.TokenManager
import com.daepiro.numberoneproject.presentation.view.MainActivity
import com.daepiro.numberoneproject.presentation.viewmodel.LoginViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    val loginVM by viewModels<LoginViewModel>()
    var naverLoginToken :String? = ""
    @Inject lateinit var tokenManager : TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this@LoginFragment



    }

    override fun setupInit() {
        val naverClientId = BuildConfig.NAVER_CLIENT_ID
        val naverClientSecret = BuildConfig.NAVER_CLIENT_SECRET
        NaverIdLoginSDK.initialize(requireContext(), naverClientId, naverClientSecret, "네이버 로그인")

    }

    override fun subscribeUi() {
        repeatOnStarted {
            loginVM.loginErrorState.collectLatest { response ->
                response?.let {
                    when(it) {
                        is ApiResult.Failure.HttpError -> {
                            when(it.code) {
                                403 -> Log.d("taag LoginActivity","LoginActivity 403 에러 펑")
                                404 -> Log.d("taag LoginActivity","LoginActivity 404 에러 펑")
                                else -> Log.d("taag LoginActivity","LoginActivity ${it.code}번 에러 펑")
                            }
                        }
                        else -> Log.d("LoginActivity Error","네트워크 상태 확인")
                    }
                }
            }
        }

        repeatOnStarted {
            tokenManager.accessToken.collectLatest {
                if (it.isNotEmpty()) {
                    val action = LoginFragmentDirections.actionLoginFragmentToGuideFirstFragment()
                    findNavController().navigate(action)
//                    startActivity(Intent(requireContext(), MainActivity::class.java))
//                    requireActivity().finish()
                    //추후 json파일 받아온적 있는지 여부 검사후 요청
                }
            }
        }
    }

    fun clickNaverLogin(view: View) {
        showToast("검수 중입니다.")
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

        NaverIdLoginSDK.authenticate(requireContext(), oauthLoginCallback)
    }

//    fun setupKakaoLogin(view:View) {
//        // 카카오 계정 로그인
//        val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
//            if (error != null) {
//                Toast.makeText(requireContext(),"카카오계정 로그인 실패 ${error}", Toast.LENGTH_SHORT).show()
//            }
//            else if (token != null) {
//                loginVM.userKakaoLogin(TokenRequestBody(token.accessToken))
//                }
//        }
//
//        // 카카오톡 어플있다면 카카오톡 로그인 시도
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
//            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
//                if (error != null) {
//                    Log.e("KakaoLoginError", "Error logging in with Kakao: ", error)
//                    Toast.makeText(requireContext(),"카카오톡 로그인 실패 ${error}", Toast.LENGTH_SHORT).show()
//                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                        return@loginWithKakaoTalk
//                    }
//
//                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
//                } else if (token != null) {
//                    loginVM.userKakaoLogin(TokenRequestBody(token.accessToken))
//                }
//            }
//        } else {
//            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
//        }
//    }

    fun setupKakaoLogin(view:View){
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = kakakoLoginCallback)
        } else {
            loginWithKakaoAccount()
        }
    }
    private fun loginWithKakaoAccount(){
        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakakoLoginCallback)

    }

    private val kakakoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            if (error.toString().contains("statusCode=302")){
                loginWithKakaoAccount()
            } else {
                Log.e("TAG", "로그인 실패", error)
            }
        }
        else if (token != null) {
            Log.i("TAG", "로그인 성공 ${token.accessToken}")
            loginVM.userKakaoLogin(TokenRequestBody(token.accessToken))
        }
    }

}