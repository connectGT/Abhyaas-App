import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\navigation\AppNavHost.kt", "r", encoding="utf-8") as f:
    content = f.read()

# Add EmptyStateScreen import if not there
if "import com.example.abhyaas.ui.screens.main.EmptyStateScreen" not in content:
    content = content.replace(
        "import com.example.abhyaas.ui.screens.tests.TestSeriesDetailScreen",
        "import com.example.abhyaas.ui.screens.tests.TestSeriesDetailScreen\nimport com.example.abhyaas.ui.screens.main.EmptyStateScreen\nimport com.example.abhyaas.ui.screens.pass.PassScreen"
    )

# Fix onFolderClick routing
old_click = """                onFolderClick = { sId, subCategory ->
                    navController.navigate(Screen.TestList.createRoute(sId, subCategory))
                },"""
new_click = """                onFolderClick = { sId, subCategory ->
                    if (subCategory == "empty_state") {
                        navController.navigate("empty_state")
                    } else {
                        navController.navigate(Screen.TestList.createRoute(sId, subCategory))
                    }
                },"""
content = content.replace(old_click, new_click)

# Add Pass and Empty State composables
new_routes = """
        // Extra routes for Pass and Empty State from outside BottomNav
        composable(Screen.Pass.route) {
            PassScreen()
        }
        composable("empty_state") {
            EmptyStateScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // 7. Test List"""

if "composable(Screen.Pass.route)" not in content:
    content = content.replace("        // 7. Test List", new_routes)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\navigation\AppNavHost.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("AppNavHost patched.")
