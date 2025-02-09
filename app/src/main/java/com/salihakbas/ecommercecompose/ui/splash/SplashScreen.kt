package com.salihakbas.ecommercecompose.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.salihakbas.ecommercecompose.navigation.Screen
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.splash.SplashContract.UiAction
import com.salihakbas.ecommercecompose.ui.splash.SplashContract.UiEffect
import com.salihakbas.ecommercecompose.ui.splash.SplashContract.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SplashScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navController: NavController
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> SplashContent(navController)
    }
}

@Composable
fun SplashContent(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    LaunchedEffect(Unit) {
        delay(2000) // Splash ekranı için kısa bir bekleme süresi
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navController.navigate(Screen.getRoute(Screen.Home)) {
                popUpTo(Screen.getRoute(Screen.Splash)) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.getRoute(Screen.SignIn)) {
                popUpTo(Screen.getRoute(Screen.Splash)) { inclusive = true }
            }
        }
    }

    // Burada Splash ekranın UI tasarımını oluşturabilirsiniz
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Loading...", fontSize = 24.sp)
    }
}

