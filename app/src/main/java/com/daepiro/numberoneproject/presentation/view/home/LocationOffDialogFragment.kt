package com.daepiro.numberoneproject.presentation.view.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentLocationOffDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment
import kotlin.system.exitProcess

class LocationOffDialogFragment : BaseDialogFragment<FragmentLocationOffDialogBinding>(R.layout.fragment_location_off_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.isCancelable = false

        binding.tvGo.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            ActivityCompat.finishAffinity(requireActivity())
            exitProcess(0)
        }
    }


}