package com.daepiro.numberoneproject.data.repositoryimpl

import android.net.Uri
import android.util.Log
import com.daepiro.numberoneproject.data.model.CommentWritingRequestBody
import com.daepiro.numberoneproject.data.model.CommentWritingResponse
import com.daepiro.numberoneproject.data.model.CommunityDisasterDetailResponse
import com.daepiro.numberoneproject.data.model.CommunityHomeDisasterResponse
import com.daepiro.numberoneproject.data.model.CommunityHomeSituationModel
import com.daepiro.numberoneproject.data.model.CommunityRereplyRequestBody
import com.daepiro.numberoneproject.data.model.CommunityTownDeleteCommentResponse
import com.daepiro.numberoneproject.data.model.CommunityTownDetailData
import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.model.CommunityTownReplyDeleteResponse
import com.daepiro.numberoneproject.data.model.CommunityTownReplyRequestBody
import com.daepiro.numberoneproject.data.model.CommunityTownReplyResponse
import com.daepiro.numberoneproject.data.model.CommunityTownReplyResponseModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Url
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val service:ApiService
):CommunityRepository {
    override suspend fun getTownCommentList(
        token:String,
        size:Int,
        tag:String,
        lastArticleId:Int?,
//        longtitude: Double?,
//        latitude: Double?,
        regionLv2:String
    ):ApiResult<CommunityTownListModel>{
        return service.getTownCommentList(token,size,tag,lastArticleId,regionLv2)
    }

    override suspend fun getTownCommentDetail(token:String,articleId:Int):ApiResult<CommunityTownDetailData>{
        return service.getTownCommentDetail(token,articleId)
    }
    override suspend fun setTownDetail(token:String, title:String, content:String, articleTag:String,longtitude:Double?, latitude:Double?,regionAgreementCheck:Boolean, imageList:List<MultipartBody.Part>)
    :ApiResult<CommentWritingResponse>{
        Log.d("CommunityRepositoryImpl", "before: ${imageList?.size}")
        val titleRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),title)
        val contentRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),content)
        val articleTagRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),articleTag)
        val longtitudeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),longtitude.toString())
        val latitudeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),latitude.toString())
        val regionAgreementCheckRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), regionAgreementCheck.toString())
        Log.d("CommunityRepositoryImpl", "after: ${imageList?.size}")
        return service.setTownDetail(token,titleRequestBody,contentRequestBody,articleTagRequestBody,longtitudeRequestBody,latitudeRequestBody,regionAgreementCheckRequestBody,imageList)
    }

    override suspend fun getTownReply(
        token:String,
        articleId:Int
    ):ApiResult<CommunityTownReplyResponse>{
        return service.getTownReply(token,articleId)
    }

    override suspend fun setTownReply(
        token:String,
        articleid: Int,
        body: CommunityTownReplyRequestBody)
    :ApiResult<CommunityTownReplyResponseModel>{
        return service.setTownReply(token,articleid,body)
    }

    override suspend fun setTownRereply(
        token:String,
        articleid: Int,
        commentid:Int,
        body: CommunityRereplyRequestBody
    ):ApiResult<CommunityTownReplyResponseModel> {
        return service.setTownRereply(token, articleid, commentid, body)
    }

    override suspend fun deleteComment(
        token:String,
        articleid: Int
    ):ApiResult<CommunityTownDeleteCommentResponse>{
        return service.deleteComment(token,articleid)
    }
    override suspend fun deleteReply(
        token:String,
        commentid:Int
    ):ApiResult<CommunityTownReplyDeleteResponse>{
        return service.deleteReply(token,commentid)
    }

    //재난상황 커뮤니티 홈
    override suspend fun getDisasterHome(token:String):ApiResult<CommunityHomeDisasterResponse>{
        return service.getDisasterHome(token)
    }

    //재난상황 커뮤니티 더보기
    override suspend fun getDisasterHomeDetail(token:String, sort:String,disasterId:Int):ApiResult<CommunityDisasterDetailResponse>{
        return service.getDisasterHomeDetail(token,disasterId,sort)
    }
}