package com.salihakbas.ecommercecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.salihakbas.ecommercecompose.ui.cart.CartScreen
import com.salihakbas.ecommercecompose.ui.cart.CartViewModel
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordScreen
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordViewModel
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutScreen
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutViewModel
import com.salihakbas.ecommercecompose.ui.detail.DetailScreen
import com.salihakbas.ecommercecompose.ui.detail.DetailViewModel
import com.salihakbas.ecommercecompose.ui.discount.DiscountScreen
import com.salihakbas.ecommercecompose.ui.discount.DiscountViewModel
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesScreen
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesViewModel
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordScreen
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordViewModel
import com.salihakbas.ecommercecompose.ui.home.HomeScreen
import com.salihakbas.ecommercecompose.ui.home.HomeViewModel
import com.salihakbas.ecommercecompose.ui.product.ProductScreen
import com.salihakbas.ecommercecompose.ui.product.ProductViewModel
import com.salihakbas.ecommercecompose.ui.profile.ProfileScreen
import com.salihakbas.ecommercecompose.ui.profile.ProfileViewModel
import com.salihakbas.ecommercecompose.ui.search.SearchScreen
import com.salihakbas.ecommercecompose.ui.search.SearchViewModel
import com.salihakbas.ecommercecompose.ui.signin.SignInScreen
import com.salihakbas.ecommercecompose.ui.signin.SignInViewModel
import com.salihakbas.ecommercecompose.ui.signup.SignUpScreen
import com.salihakbas.ecommercecompose.ui.signup.SignUpViewModel
import com.salihakbas.ecommercecompose.ui.splash.SplashScreen
import com.salihakbas.ecommercecompose.ui.splash.SplashViewModel

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
                onAction = viewModel::onAction,
                navController = navController
            )
        }
        composable<Screen.SignIn> {
            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SignInScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateToSignUp = { navController.navigate(Screen.SignUp) },
                navigateToHome = { navController.navigate(Screen.Home) },
                navigateToForgotPassword = { navController.navigate(Screen.ForgotPassword) }
            )
        }
        composable<Screen.SignUp> {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SignUpScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateToSignIn = { navController.navigate(Screen.SignIn) }
            )
        }
        composable<Screen.ForgotPassword> {
            val viewModel: ForgotPasswordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ForgotPasswordScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateToSignIn = { navController.navigate(Screen.SignIn) }
            )
        }
        composable<Screen.ChangePassword> {
            val viewModel: ChangePasswordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ChangePasswordScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateToProfile = { navController.navigate(Screen.Profile) }
            )
        }
        composable<Screen.Home> {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            HomeScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                userId = userId,
                onCategoryClick = {
                    navController.navigate("${Screen.getRoute(Screen.Product(""))}/$it")
                },
                navigateToSearch = { navController.navigate(Screen.Search) },
                navigateToDiscount = { navController.navigate(Screen.Discount) },
                navigateToProducts = {
                    navController.navigate("${Screen.getRoute(Screen.Product(""))}/Tümü")
                },
                navigateToDetail = { productId ->
                    navController.navigate("${Screen.getRoute(Screen.Detail(0))}/$productId")
                }
            )
            LaunchedEffect(key1 = userId) {
                viewModel.fetchUserFromRealtimeDatabase(userId)
            }
        }
        composable(
            route = "${Screen.getRoute(Screen.Detail(0))}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: -1
            val viewModel: DetailViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect

            DetailScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navController = navController,
                onBackClick = { navController.popBackStack() },
                navigateToSearch = { navController.navigate(Screen.Search) },
                userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            )
        }
        composable<Screen.Cart> {
            val viewModel: CartViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            CartScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onBackClick = { navController.popBackStack() },
                navigateCheckout = { navController.navigate(Screen.Checkout) },
                navigateToDetail = { productId ->
                    navController.navigate("${Screen.getRoute(Screen.Detail(0))}/$productId")
                }
            )
            LaunchedEffect(Unit) {
                viewModel.getCartProducts(userId)
            }
        }
        composable<Screen.Checkout> {
            val viewModel: CheckoutViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            CheckoutScreen(
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
                onAction = viewModel::onAction,
                navigateToDetail = { productId ->
                    navController.navigate("${Screen.getRoute(Screen.Detail(0))}/$productId")
                }
            )
        }
        composable<Screen.Profile> {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ProfileScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateToSignIn = { navController.navigate(Screen.SignIn) },
                navigateToChangePassword = { navController.navigate(Screen.ChangePassword) }
            )
        }

        composable<Screen.Search> {
            val viewModel: SearchViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SearchScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateBack = { navController.popBackStack() },
                navigateDetail = { productId ->
                    navController.navigate("${Screen.getRoute(Screen.Detail(0))}/$productId")
                }
            )
        }

        composable<Screen.Discount> {
            val viewModel: DiscountViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            DiscountScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateBack = { navController.popBackStack() },
                navigateToDetail = { productId ->
                    navController.navigate("${Screen.getRoute(Screen.Detail(0))}/$productId")
                }
            )
        }

        composable(
            route = "${Screen.getRoute(Screen.Product(""))}/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) {
            val viewModel: ProductViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ProductScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navigateBack = { navController.popBackStack() },
                navigateToDetail = { productId ->
                    navController.navigate("${Screen.getRoute(Screen.Detail(0))}/$productId")
                }
            )
        }
    }
}