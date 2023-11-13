package com.daepiro.numberoneproject.presentation.view.funding.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentSendHeartBottomSheetBinding
import com.daepiro.numberoneproject.presentation.viewmodel.FundingViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import games.moisoni.google_iab.BillingConnector
import games.moisoni.google_iab.BillingEventListener
import games.moisoni.google_iab.enums.ProductType
import games.moisoni.google_iab.models.BillingResponse
import games.moisoni.google_iab.models.ProductInfo
import games.moisoni.google_iab.models.PurchaseInfo

@AndroidEntryPoint
class SendHeartBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSendHeartBottomSheetBinding
    private lateinit var billingConnector: BillingConnector
    val fundingVM by viewModels<FundingViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_heart_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sheet = this
        binding.viewmodel = fundingVM
        binding.lifecycleOwner = this
        this.isCancelable = false

        billingConnector = BillingConnector(requireContext(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkf/GZH9R2T1j9EQ1fiHXwGEJ5LmoISGAk4KUsEn/+qTIBlUlC0qi7z9Z7X+VT8lIgW15cJir/hEsZvne8Fy+NZJXvNbbjOYee2mbw63aMLEMOfkrHtOF071y9N9tdPFuche9TSV1z5ZBH0f2GVLEw90aHVzfQE6h99rhtBgPd9L7QBCn8sVvp6H1xVX+prMB5tbb18ji4kkZBmHVfV6MqIQ3lM6yXLn1sq8LgBjpbDnGhc20PDOutWbbCQdGW6QnIgGMJe0BymSGAdoKGDacAuTQt3Ia4QczXjxBRgP9/PMU32Om/2E62X2buf66v8W/SRiOWItFt5F7MQykQCItoQIDAQAB")
            .setConsumableIds(listOf("heart_1000"))
            .setNonConsumableIds(listOf("heart_1000"))
            .autoAcknowledge()
            .autoConsume()
            .enableLogging()
            .connect()

        billingConnector.setBillingEventListener(object : BillingEventListener {
            override fun onProductsFetched(productDetails: MutableList<ProductInfo>) {
            }

            override fun onPurchasedProductsFetched(
                productType: ProductType,
                purchases: MutableList<PurchaseInfo>
            ) {
            }

            override fun onProductsPurchased(purchases: MutableList<PurchaseInfo>) {
            }

            override fun onPurchaseAcknowledged(purchase: PurchaseInfo) {
            }

            override fun onPurchaseConsumed(purchase: PurchaseInfo) {
            }

            override fun onBillingError(
                billingConnector: BillingConnector,
                response: BillingResponse
            ) {
            }
        })


        binding.ivClose.setOnClickListener {
            this.dismiss()
        }

        binding.btnHeartSend.setOnClickListener {
            CheerDialogFragment().show(parentFragmentManager, "")
        }
    }

    fun onClickView(view: View) {
        when(view.id) {
            R.id.btn_minus -> if (fundingVM.selectedHeartCount.value > 0) fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value - 1
            R.id.btn_plus -> fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value + 1

            R.id.tv_reset -> fundingVM.selectedHeartCount.value = 0
            R.id.tv_heart_5 -> fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value + 5
            R.id.tv_heart_10 -> fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value + 10
            R.id.tv_heart_20 -> fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value + 20
            R.id.tv_heart_all -> fundingVM.selectedHeartCount.value = 0

            R.id.btn_heart_send -> {
                billingConnector.purchase(requireActivity(), "heart_1000")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (billingConnector != null) {
            billingConnector.release()
        }
    }
}