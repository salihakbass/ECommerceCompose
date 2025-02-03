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
    data class Detail(val productId: Int) : Screen

    @Serializable
    data object Cart : Screen

    @Serializable
    data object Favorites : Screen

    @Serializable
    data object Profile : Screen

    @Serializable
    data object Search : Screen

    @Serializable
    data object Discount : Screen

    @Serializable
    data object Product : Screen

    companion object {
        fun getRoute(screen: Screen): String = screen::class.qualifiedName.orEmpty()

        fun showBottomBar(currentRoute: String?): Boolean {
            return when (currentRoute) {
                getRoute(Home), getRoute(Profile), getRoute(Favorites), getRoute(Cart) -> true
                else -> false
            }
        }
    }
}