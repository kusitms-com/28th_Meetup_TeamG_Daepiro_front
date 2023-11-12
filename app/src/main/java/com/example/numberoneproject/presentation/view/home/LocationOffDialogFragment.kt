package com.example.numberoneproject.presentation.view.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentLocationOffDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment
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