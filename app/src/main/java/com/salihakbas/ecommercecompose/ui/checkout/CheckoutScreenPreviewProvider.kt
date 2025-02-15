package com.salihakbas.ecommercecompose.ui.checkout

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.salihakbas.ecommercecompose.data.model.Address

class CheckoutScreenPreviewProvider : PreviewParameterProvider<CheckoutContract.UiState> {
    override val values: Sequence<CheckoutContract.UiState>
        get() = sequenceOf(
            CheckoutContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            CheckoutContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            CheckoutContract.UiState(
                isLoading = false,
                address = listOf(
                    Address(
                        title = "Ev",
                        description = "Karadeniz Caddesi Sahil Mahallesi",
                        city = "Trabzon",
                        district = "Araklı",
                        code = "61000"
                    )
                )
            ),
        )
}