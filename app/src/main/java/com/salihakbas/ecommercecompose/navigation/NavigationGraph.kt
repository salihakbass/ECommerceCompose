package com.salihakbas.ecommercecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.salihakbas.ecommercecompose.ui.splash.SplashScreen
import com.salihakbas.ecommercecompose.ui.splash.SplashViewModel
import com.salihakbas.ecommercecompose.ui.signin.SignInScreen
import com.salihakbas.ecommercecompose.ui.signin.SignInViewModel
import com.salihakbas.ecommercecompose.ui.signup.SignUpScreen
import com.salihakbas.ecommercecompose.ui.signup.SignUpViewModel
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordScreen
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordViewModel
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordScreen
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordViewModel
import com.salihakbas.ecommercecompose.ui.home.HomeScreen
import com.salihakbas.ecommercecompose.ui.home.HomeViewModel
import com.salihakbas.ecommercecompose.ui.detail.DetailScreen
import com.salihakbas.ecommercecompose.ui.detail.DetailViewModel
import com.salihakbas.ecommercecompose.ui.cart.CartScreen
import com.salihakbas.ecommercecompose.ui.cart.CartViewModel
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesScreen
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesViewModel
import com.salihakbas.ecommercecompose.ui.profile.ProfileScreen
import com.salihakbas.ecommercecompose.ui.profile.ProfileViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: Screen,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Screen.Splash> {
            val viewModel: SplashViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SplashScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.SignIn> {
            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SignInScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.SignUp> {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SignUpScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.ForgotPassword> {
            val viewModel: ForgotPasswordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ForgotPasswordScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.ChangePassword> {
            val viewModel: ChangePasswordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ChangePasswordScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.Home> {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            HomeScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.Detail> {
            val viewModel: DetailViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            DetailScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.Cart> {
            val viewModel: CartViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            CartScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.Favorites> {
            val viewModel: FavoritesViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            FavoritesScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable<Screen.Profile> {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ProfileScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }

    }
}