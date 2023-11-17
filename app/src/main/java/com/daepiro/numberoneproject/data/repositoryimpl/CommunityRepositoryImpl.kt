package com.daepiro.numberoneproject.data.repositoryimpl

import android.net.Uri
import android.util.Log
import com.daepiro.numberoneproject.data.model.CommentWritingRequestBody
import com.daepiro.numberoneproject.data.model.CommentWritingResponse
import com.daepiro.numberoneproject.data.model.CommunityTownDetailData
import com.daepiro.numberoneproject.data.model.CommunityTownListModel
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
        tag:String?,
        lastArticleId:Int?
    ):ApiResult<CommunityTownListModel>{
        return service.getTownCommentList(token,size,tag,lastArticleId)
    }

    override suspend fun getTownCommentDetail(token:String,articleId:Int):ApiResult<CommunityTownDetailData>{
        return service.getTownCommentDetail(token,articleId)
    }
    override suspend fun setTownDetail(token:String, title:String, content:String, articleTag:String,longtitude:Double, latitude:Double, imageList:List<MultipartBody.Part>)
    :ApiResult<CommentWritingResponse>{
        Log.d("CommunityRepositoryImpl", "before: ${imageList.size}")
        val titleRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),title)
        val contentRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),content)
        val articleTagRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),articleTag)
        val longtitudeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),longtitude.toString())
        val latitudeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),latitude.toString())
        Log.d("CommunityRepositoryImpl", "after: ${imageList.size}")
        return service.setTownDetail(token,titleRequestBody,contentRequestBody,articleTagRequestBody,longtitudeRequestBody,latitudeRequestBody,imageList)
    }
}