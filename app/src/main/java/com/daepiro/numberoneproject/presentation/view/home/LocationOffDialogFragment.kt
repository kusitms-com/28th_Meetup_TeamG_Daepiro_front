package com.daepiro.numberoneproject.presentation.view.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentLocationOffDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment
import kotlin.system.exitProcess

class LocationOffDialogFragment : BaseDialogFragment<FragmentLocationOffDialogBinding>(R.layout.fragment_location_off_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.isCancelable = false

        binding.tvLocationOn.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            ActivityCompat.finishAffinity(requireActivity())
            exitProcess(0)
        }

        binding.tvClose.setOnClickListener {
            ActivityCompat.finishAffinity(requireActivity())
            exitProcess(0)
        }

        val originalText = getString(R.string.대피로에서_제공하는_서비스를_이용하기_위해_).format("항상 허용")
        val spannableString = SpannableString(originalText)
        // 문자열에서 강조할 부분의 시작 인덱스 찾기
        val startIndex = originalText.indexOf("항상 허용")

        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.orange_500)),
            startIndex,
            startIndex + "항상 허용".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvDescription.text = spannableString
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val windowManager = requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = windowManager.currentWindowMetrics
        val deviceWidth = size.bounds.width()

        params?.width = (deviceWidth * 0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}