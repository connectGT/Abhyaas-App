package com.example.abhyaas.contract

sealed class AppRoute(val route: String) {
    object Login : AppRoute("login")
    object UserSetting : AppRoute("user_setting")
    object UserProfile : AppRoute("user_profile")
    object Main : AppRoute("main")
    object Home : AppRoute("home")
    object Tests : AppRoute("tests")
    object Pass : AppRoute("pass")
    object Updates : AppRoute("updates")

    object TestSeriesDetail : AppRoute("test_series_detail/{seriesId}") {
        fun createRoute(seriesId: String): String = "test_series_detail/$seriesId"
        fun parseSeriesId(actualRoute: String): String? {
            val prefix = "test_series_detail/"
            return if (actualRoute.startsWith(prefix)) actualRoute.removePrefix(prefix) else null
        }
    }

    object TestList : AppRoute("test_list/{seriesId}/{subCategory}") {
        fun createRoute(seriesId: String, subCategory: String): String =
            "test_list/$seriesId/$subCategory"

        fun parseParams(actualRoute: String): Pair<String, String>? {
            val parts = actualRoute.split("/")
            return if (parts.size == 3 && parts[0] == "test_list") {
                Pair(parts[1], parts[2])
            } else null
        }
    }

    object TestInstructions : AppRoute("test_instructions/{testId}") {
        fun createRoute(testId: String): String = "test_instructions/$testId"
        fun parseTestId(actualRoute: String): String? {
            val prefix = "test_instructions/"
            return if (actualRoute.startsWith(prefix)) actualRoute.removePrefix(prefix) else null
        }
    }

    object ActiveTest : AppRoute("active_test/{testId}") {
        fun createRoute(testId: String): String = "active_test/$testId"
        fun parseTestId(actualRoute: String): String? {
            val prefix = "active_test/"
            return if (actualRoute.startsWith(prefix)) actualRoute.removePrefix(prefix) else null
        }
    }

    object TestResult : AppRoute("test_result/{testId}") {
        fun createRoute(testId: String): String = "test_result/$testId"
        fun parseTestId(actualRoute: String): String? {
            val prefix = "test_result/"
            return if (actualRoute.startsWith(prefix)) actualRoute.removePrefix(prefix) else null
        }
    }

    object PrivacyPolicy : AppRoute("privacy_policy")
}

/**
 * Deterministic simulator of Jetpack Compose NavHost backstack operations.
 */
class BackstackSimulator(initialRoute: String = AppRoute.Home.route) {
    private val stack = mutableListOf<String>()

    init {
        stack.add(initialRoute)
    }

    val currentDestination: String
        get() = stack.last()

    val currentStack: List<String>
        get() = stack.toList()

    val size: Int
        get() = stack.size

    fun navigate(
        destination: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        launchSingleTop: Boolean = false
    ) {
        if (launchSingleTop && stack.isNotEmpty() && stack.last() == destination) {
            return
        }

        if (popUpToRoute != null) {
            val targetIndex = stack.indexOfLast {
                it == popUpToRoute || matchesTemplate(it, popUpToRoute)
            }
            if (targetIndex != -1) {
                val popCount = if (inclusive) {
                    stack.size - targetIndex
                } else {
                    stack.size - (targetIndex + 1)
                }
                repeat(popCount) {
                    if (stack.isNotEmpty()) {
                        stack.removeAt(stack.size - 1)
                    }
                }
            }
        }

        stack.add(destination)
    }

    fun popBackStack(): Boolean {
        return if (stack.size > 1) {
            stack.removeAt(stack.size - 1)
            true
        } else {
            false
        }
    }

    private fun matchesTemplate(actual: String, template: String): Boolean {
        val actualParts = actual.split("/")
        val templateParts = template.split("/")
        if (actualParts.size != templateParts.size) return false
        for (i in actualParts.indices) {
            val t = templateParts[i]
            if (t.startsWith("{") && t.endsWith("}")) continue
            if (t != actualParts[i]) return false
        }
        return true
    }
}
