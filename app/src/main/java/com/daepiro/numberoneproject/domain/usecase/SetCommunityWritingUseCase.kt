package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommentWritingRequestBody
import com.daepiro.numberoneproject.data.model.CommentWritingResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class SetCommunityWritingUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(
        token:String,
        title:String,
        content:String,
        articleTag:String,
        longtitude:Double,
        latitude:Double,
        regionAgreementCheck:Boolean,
        imageList:List<MultipartBody.Part>
    ):ApiResult<CommentWritingResponse>{
        return communityRepository.setTownDetail(token,title,content,articleTag,longtitude,latitude,regionAgreementCheck,imageList)
    }
}