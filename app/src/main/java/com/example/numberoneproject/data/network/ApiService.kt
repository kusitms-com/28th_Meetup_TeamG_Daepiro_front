package com.example.numberoneproject.data.network

import com.example.numberoneproject.data.model.LoginTestResponse
import com.example.numberoneproject.data.model.LoginTokenResponse
import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.model.ShelterUrlResponse
import com.example.numberoneproject.data.model.TokenRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    // 만료된 AccessToken 갱신
    @POST("/token/refresh")
    suspend fun refreshAccessToken(
        @Body body: TokenRequestBody
    ): ApiResult<LoginTokenResponse>

    // 네이버 로그인
    @POST("/token/naver")
    suspend fun userNaverLogin(
        @Body body: TokenRequestBody
    ): ApiResult<LoginTokenResponse>

    //카카오 로그인
    @POST("/token/kakao")
    suspend fun userKakaoLogin(
        @Body body: TokenRequestBody
    ): ApiResult<LoginTokenResponse>

    // 로그인 테스트
    @GET("/api/logintest")
    suspend fun apiLoginTest(
        @Header("Authorization") token: String
    ): ApiResult<LoginTestResponse>

    // 가까운 대피소 목록 조회
    @POST("/api/shelters/list")
    suspend fun getAroundSheltersList(
        @Header("Authorization") token: String,
        @Body body: ShelterRequestBody
    ): ApiResult<ShelterListResponse>


    //대피소 전체 데이터 가진 링크 가져오기
    @GET("/api/shelters/init")
    suspend fun getShelters(
        @Header("Authorization") token:String
    ): ApiResult<ShelterUrlResponse>

    //대피소 url로부터 파싱
    @GET
    suspend fun getDataFromUrl(
        @Url url:String
    ):ApiResult<ShelterData>
}