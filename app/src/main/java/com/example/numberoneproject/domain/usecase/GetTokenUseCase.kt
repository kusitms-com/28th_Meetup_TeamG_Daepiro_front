package com.example.numberoneproject.domain.usecase

import android.accounts.NetworkErrorException
import com.example.numberoneproject.data.model.TokenResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.KakaoLoginRepository
import retrofit2.HttpException
import javax.inject.Inject
//네트워크 호출의 예외 캐치하고 처리
class GetTokenUseCase @Inject constructor(private val tokenRepository : KakaoLoginRepository){
    suspend fun postToken(token:String):ApiResult<TokenResponse>{
        return try{
            val result = tokenRepository.getToken(token)
            ApiResult.Success(result)
        }catch (e:Exception){
            when(e){
                //http에러
                is HttpException -> {//에러코드 404,500..
                    val message = e.message() ?: "Http Error"
                    val body = e.response()?.errorBody()?.string() ?: "no error body"
                    //e.code는 http상태코드를 가져온다
                    ApiResult.Failure.HttpError(e.code(), message,body)
                }
                //네트워크 문제가 있을경우
                is NetworkErrorException -> ApiResult.Failure.NetworkError(e)
                else-> ApiResult.Failure.UnknownApiError(e)
            }
        }
    }
}