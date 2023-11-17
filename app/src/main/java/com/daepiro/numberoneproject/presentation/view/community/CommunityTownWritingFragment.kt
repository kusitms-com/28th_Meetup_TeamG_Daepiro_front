package com.daepiro.numberoneproject.presentation.view.community

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.CommentWritingRequestBody
import com.daepiro.numberoneproject.databinding.FragmentCommunityTownWritingBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.RuntimeException

class CommunityTownWritingFragment : BaseFragment<FragmentCommunityTownWritingBinding>(R.layout.fragment_community_town_writing) {
    val viewModel by activityViewModels<CommunityViewModel>()
    private val imageUrls = mutableListOf<String>()
    private var selectedImageUri:Uri? = null
    //private var imagePart: MultipartBody.Part? = null
    private var imagePartList = mutableListOf<MultipartBody.Part>()
    private var title:String=""
    private var content:String=""
    private var latitude = 0
    private var longtitude=0
    private var articleTag:String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.select.setOnClickListener{
            showBottomSheet()
        }

        binding.titleTxt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                title=p0.toString()
            }

        })
        binding.contentTxt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                content=p0.toString()
            }

        })
        viewModel.tagData.observe(viewLifecycleOwner, Observer{tag->
            when(tag){
                "일상" -> articleTag = "LIFE"
                "교통" -> articleTag = "TRAFFIC"
                else -> articleTag = "NONE"
            }
        })


        binding.addPhoto.setOnClickListener{
            if(checkPermission()){
                getImage()
            }
        }



        binding.complete.setOnClickListener{
            if(title.isNotEmpty() && content.isNotEmpty()){
                viewModel.postComment(title,content,articleTag,126.9723,37.5559,imagePartList)
                Log.d("postComment1","$imagePartList")
                findNavController().popBackStack()
            }
            Log.d("postComment","$articleTag")
        }

    }

    private fun checkPermission():Boolean{
        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES),STORAGE_PERMISSION_CODE)
            Log.d("checkPermission","false")
            return false
        }
        Log.d("checkPermission","true")
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            STORAGE_PERMISSION_CODE ->{
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    //갤러리 오픈
                    getImage()
                }
                else{
                    Log.e("writingFragment", "권한 허용이 필요합니다")
                }
            }
        }
    }

    private fun getImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK || requestCode == GALLERY_REQUEST_CODE){
            selectedImageUri = data?.data
            selectedImageUri?.let{uri->
                val imageUrl = uri.toString()
                addImageToImageUrls(imageUrl)
                val imagePart = uriToMultipartBody(uri)
                imagePartList.add(imagePart)
                Log.d("CommunityTownWritingFragment","$imagePartList")
            }
        }
    }

    private fun uriToMultipartBody(uri:Uri):MultipartBody.Part{
        Log.d("uriToMultipartBody","$uri")
        val cursor = requireContext().contentResolver.query(uri,null,null,null,null)
        cursor?.use{
            if(it.moveToFirst()){
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val fileName = it.getString(index)
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val requestBody = inputStream?.let{stream->
                    RequestBody.create("image/*".toMediaTypeOrNull(), stream.readBytes())
                }
                Log.d("uriToMultipartBody","$fileName")
                return MultipartBody.Part.createFormData("image",fileName,requestBody!!)
            }
        }
        throw RuntimeException("Failed to get uri")
    }

    private fun updateImageViews() {
        val imageViews = listOf(binding.image1, binding.image2, binding.image3)
        val cardViews = listOf(binding.card1, binding.card2, binding.card3)

        imageUrls.forEachIndexed { index, imageUrl ->
            if (index < imageViews.size) {
                loadImageIntoImageView(imageUrl, imageViews[index],cardViews[index])
            }
        }
    }

    private fun loadImageIntoImageView(imageUrl: String, imageView: ImageView,cardView:CardView) {
        cardView.visibility = View.VISIBLE
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }

    private fun addImageToImageUrls(imageUrl: String) {
        imageUrls.add(imageUrl)
        updateImageViews()
    }


    private fun showBottomSheet(){
        val bottomSheet = TagSelectBottomFragment()
        bottomSheet.show(parentFragmentManager,"select")
    }
    companion object{
        private const val STORAGE_PERMISSION_CODE = 100
        private const val GALLERY_REQUEST_CODE = 101
    }

}
