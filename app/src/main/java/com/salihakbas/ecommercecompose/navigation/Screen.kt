package com.salihakbas.ecommercecompose.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Splash : Screen

    @Serializable
    data object SignIn : Screen

    @Serializable
    data object SignUp : Screen

    @Serializable
    data object ForgotPassword : Screen

    @Serializable
    data object ChangePassword : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object Detail : Screen

    @Serializable
    data object Cart : Screen

    @Serializable
    data object Favorites : Screen

    @Serializable
    data object Profile : Screen
}