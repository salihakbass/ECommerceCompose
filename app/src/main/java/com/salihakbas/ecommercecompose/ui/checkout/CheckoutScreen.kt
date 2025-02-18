package com.salihakbas.ecommercecompose.ui.checkout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.data.model.Address
import com.salihakbas.ecommercecompose.data.model.CreditCard
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiAction
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiEffect
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiState
import kotlinx.coroutines.flow.Flow

@Composable
fun CheckoutScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    var selectedAddressId by remember { mutableStateOf<Long?>(null) }
    var selectedCreditCardId by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        bottomBar = {
            BottomBarCheckout()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CheckoutContent(
                addresses = uiState.address,
                selectedAddressId = selectedAddressId,
                onSelectAddress = { selectedAddressId = it },
                onAddAddress = { address -> onAction(UiAction.AddAddress(address)) },
                onAction = onAction,
                uiState = uiState,
                selectedCreditCardId = selectedCreditCardId,
                onSelectCard = { selectedCreditCardId = it },
                onAddCreditCard = { creditCard -> onAction(UiAction.AddCreditCard(creditCard)) },
                creditCard = uiState.creditCards
            )
        }
    }
}

@Composable
fun CheckoutContent(
    addresses: List<Address>,
    creditCard: List<CreditCard>,
    selectedAddressId: Long?,
    selectedCreditCardId: Long?,
    onSelectAddress: (Long) -> Unit,
    onSelectCard: (Long) -> Unit,
    onAddAddress: (Address) -> Unit,
    onAction: (UiAction) -> Unit,
    uiState: UiState,
    onAddCreditCard: (CreditCard) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AddressCard(
            addresses = addresses,
            selectedAddressId = selectedAddressId,
            onSelectAddress = onSelectAddress,
            onAddAddress = onAddAddress,
            onAction = onAction,
            uiState = uiState
        )
        CreditCardCard(
            onAction = onAction,
            selectedCreditCardId = selectedCreditCardId,
            onSelectCard = onSelectCard,
            uiState = uiState,
            onAddCreditCard = onAddCreditCard,
            creditCard = creditCard
        )
    }
}

@Composable
fun AddressCard(
    addresses: List<Address>,
    selectedAddressId: Long?,
    onSelectAddress: (Long) -> Unit,
    onAddAddress: (Address) -> Unit,
    onAction: (UiAction) -> Unit,
    uiState: UiState
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                            AddressCardItem(
                                address = address,
                                isSelected = address.id.toLong() == selectedAddressId,
                                onSelect = { onSelectAddress(address.id.toLong()) }
                            )
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
fun AddressCardItem(address: Address, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 180.dp, height = 180.dp)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Color.Blue else Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

@Composable
fun CreditCardItem(creditCard: CreditCard, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 320.dp, height = 180.dp)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Color.Yellow else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = creditCard.cardName,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = creditCard.cardOwnerName,
                fontSize = 16.sp,
                color = Color.White
            )

            Text(
                text = "${creditCard.cardNumber.take(4)} **** **** ${
                    creditCard.cardNumber.takeLast(
                        4
                    )
                }",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = creditCard.expiryDate.padEnd(5, '/'),
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = "*${creditCard.cvv.takeLast(2)}",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
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
fun CreditCardCard(
    onAction: (UiAction) -> Unit,
    uiState: UiState,
    selectedCreditCardId: Long?,
    onSelectCard: (Long) -> Unit,
    onAddCreditCard: (CreditCard) -> Unit,
    creditCard: List<CreditCard>
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
                text = "Kartlarım",
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
            if (creditCard.isEmpty()) {
                Text(
                    text = "Kayıtlı kart bulunmamaktadır.",
                    fontSize = 16.sp
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(creditCard) { card ->
                        CreditCardItem(
                            creditCard = card,
                            isSelected = card.id.toLong() == selectedCreditCardId,
                            onSelect = { onSelectCard(card.id.toLong()) }
                        )
                    }
                }
            }
        }
    }
    if (showBottomSheet) {
        CreditCardBottomSheet(
            onDismiss = { showBottomSheet = false },
            uiState = uiState,
            onAction = onAction,
            onAdd = { creditCard ->
                onAddCreditCard(creditCard)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCardBottomSheet(
    onDismiss: () -> Unit,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    onAdd: (CreditCard) -> Unit
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
            Text("Yeni Kart Ekle", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = uiState.cardName,
                onValueChange = { onAction(UiAction.OnCardNameChange(it.replaceFirstChar { char -> char.uppercase() })) },
                label = { Text("Kart İsmi") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.cardOwnerName,
                onValueChange = { onAction(UiAction.OnCardOwnerNameChange(it)) },
                label = { Text("Ad Soyad") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                value = uiState.cardNumber,
                onValueChange = { input ->
                    val digitsOnly = input.filter { it.isDigit() }
                    if (digitsOnly.length <= 16) {
                        val formatted = digitsOnly.chunked(4).joinToString(" ")
                        onAction(UiAction.OnCardNumberChange(formatted))
                    }

                },
                label = { Text("Kart Numarası") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                OutlinedTextField(
                    value = uiState.cardDate,
                    onValueChange = {
                        if (it.length <= 5) {
                            val formatted = it.replace(Regex("[^0-9]"), "")
                                .chunked(2)
                                .joinToString("/")

                            onAction(UiAction.OnCardDateChange(formatted))
                        }
                    },
                    label = { Text("Son Kullanma Tarihi") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = uiState.cardCvv,
                    onValueChange = {
                        if (it.length <= 3) {
                            onAction(UiAction.OnCardCvvChange(it))
                        }
                    },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
            Button(
                onClick = {
                    if (uiState.cardOwnerName.isNotBlank() && uiState.cardNumber.isNotBlank() && uiState.cardCvv.isNotBlank()) {
                        onAdd(
                            CreditCard(
                                id = 0,
                                cardName = uiState.cardName,
                                cardNumber = uiState.cardNumber,
                                expiryDate = uiState.cardDate,
                                cvv = uiState.cardCvv,
                                cardOwnerName = uiState.cardOwnerName,
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
fun BottomBarCheckout() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            )
        ) {
            Text(
                text = "Ödemeyi Gerçekleştir",
                fontSize = 20.sp
            )
        }
    }
}



