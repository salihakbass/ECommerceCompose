package com.salihakbas.ecommercecompose.ui.checkout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.data.model.Address
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiAction
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiEffect
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiState
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import kotlinx.coroutines.flow.Flow

@Composable
fun CheckoutScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> CheckoutContent(
            addresses = uiState.address,
            onAddAddress = { address -> onAction(UiAction.AddAddress(address)) },
            onAction = onAction,
            uiState = uiState
        )
    }
}

@Composable
fun CheckoutContent(
    addresses: List<Address>,
    onAddAddress: (Address) -> Unit,
    onAction: (UiAction) -> Unit,
    uiState: UiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AddressCard(
            addresses = addresses,
            onAddAddress = onAddAddress,
            onAction = onAction,
            uiState = uiState
        )
        CreditCardCard()
    }
}

@Composable
fun AddressCard(
    addresses: List<Address>,
    onAddAddress: (Address) -> Unit,
    onAction: (UiAction) -> Unit,
    uiState: UiState
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Adreslerim",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { showBottomSheet = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Aç/Kapat"
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInVertically(initialOffsetY = { -10 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -10 }) + fadeOut()
        ) {
            Column {
                if (addresses.isEmpty()) {
                    Text("Kayıtlı adres bulunmamaktadır.", fontSize = 16.sp)
                } else {
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(addresses) { address ->
                            AddressCardItem(address = address)
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        AddressBottomSheet(
            onDismiss = { showBottomSheet = false },
            onAdd = { address ->
                onAddAddress(address)
            },
            onAction = onAction,
            uiState = uiState
        )
    }
}

@Composable
fun AddressCardItem(address: Address) {
    Card(
        modifier = Modifier
            .size(width = 180.dp, height = 180.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(width = 1.dp, color = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Text(
                text = address.title,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${address.city} / " + address.district,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = address.description,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressBottomSheet(
    onDismiss: () -> Unit,
    onAdd: (Address) -> Unit,
    onAction: (UiAction) -> Unit,
    uiState: UiState
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Yeni Adres Ekle", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { onAction(UiAction.OnTitleChange(it)) },
                    label = { Text("Adres Başlığı") },
                    placeholder = { Text(text = "Ev, Ofis vb.") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.city,
                    onValueChange = { onAction(UiAction.OnCityChange(it)) },
                    label = { Text("Şehir") },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                OutlinedTextField(
                    value = uiState.district,
                    onValueChange = { onAction(UiAction.OnDistrictChange(it)) },
                    label = { Text("İlçe") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.code,
                    onValueChange = { onAction(UiAction.OnCodeChange(it)) },
                    label = { Text("Posta Kodu") },
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = uiState.description,
                onValueChange = { onAction(UiAction.OnDescriptionChange(it)) },
                label = { Text("Açıklama") },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)

            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (uiState.title.isNotBlank() && uiState.city.isNotBlank() && uiState.district.isNotBlank()) {
                        onAdd(
                            Address(
                                id = 0,
                                title = uiState.title,
                                description = uiState.description,
                                city = uiState.city,
                                district = uiState.district,
                                code = uiState.code
                            )
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ekle")
            }
        }
    }
}

@Composable
fun CreditCardCard() {
    var isExpanded by remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = isExpanded, label = "expandTransition")

    val cardOffset by transition.animateDp(
        transitionSpec = { tween(durationMillis = 500) },
        label = "Card Offset"
    ) { expanded ->
        if (expanded) 0.dp else (-10).dp
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kartlarım",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Aç/Kapat"
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInVertically(initialOffsetY = { -10 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -10 }) + fadeOut()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Adres 1: İstanbul, Türkiye", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Adres 2: Ankara, Türkiye", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Adres Ekle")
                    }
                }
            }
        }
    }
}



