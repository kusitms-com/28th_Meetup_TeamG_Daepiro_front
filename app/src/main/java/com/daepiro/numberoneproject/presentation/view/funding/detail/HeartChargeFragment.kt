package com.daepiro.numberoneproject.presentation.view.funding.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentHeartChargeBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import games.moisoni.google_iab.BillingConnector
import games.moisoni.google_iab.BillingEventListener
import games.moisoni.google_iab.enums.ProductType
import games.moisoni.google_iab.models.BillingResponse
import games.moisoni.google_iab.models.ProductInfo
import games.moisoni.google_iab.models.PurchaseInfo

@AndroidEntryPoint
class HeartChargeFragment: BaseFragment<FragmentHeartChargeBinding>(R.layout.fragment_heart_charge) {
    private lateinit var billingConnector: BillingConnector

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this

        billingConnector = BillingConnector(requireContext(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkf/GZH9R2T1j9EQ1fiHXwGEJ5LmoISGAk4KUsEn/+qTIBlUlC0qi7z9Z7X+VT8lIgW15cJir/hEsZvne8Fy+NZJXvNbbjOYee2mbw63aMLEMOfkrHtOF071y9N9tdPFuche9TSV1z5ZBH0f2GVLEw90aHVzfQE6h99rhtBgPd9L7QBCn8sVvp6H1xVX+prMB5tbb18ji4kkZBmHVfV6MqIQ3lM6yXLn1sq8LgBjpbDnGhc20PDOutWbbCQdGW6QnIgGMJe0BymSGAdoKGDacAuTQt3Ia4QczXjxBRgP9/PMU32Om/2E62X2buf66v8W/SRiOWItFt5F7MQykQCItoQIDAQAB")
            .setConsumableIds(listOf("heart_1000","heart_3000","heart_5000","heart_10000","heart_50000","heart_100000"))
            .setNonConsumableIds(listOf("heart_1000","heart_3000","heart_5000","heart_10000","heart_50000","heart_100000"))
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


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun onClickView(view: View) {
        when(view.id) {
            R.id.ll_charge_10 -> billingConnector.purchase(requireActivity(), "heart_1000")
            R.id.ll_charge_30 ->  billingConnector.purchase(requireActivity(), "heart_3000")
            R.id.ll_charge_50 ->  billingConnector.purchase(requireActivity(), "heart_5000")
            R.id.ll_charge_100 ->  billingConnector.purchase(requireActivity(), "heart_10000")
            R.id.ll_charge_500 ->  billingConnector.purchase(requireActivity(), "heart_50000")
            R.id.ll_charge_1000 ->  billingConnector.purchase(requireActivity(), "heart_100000")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (billingConnector != null) {
            billingConnector.release()
        }
    }
}