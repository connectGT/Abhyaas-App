# -*- coding: utf-8 -*-
import os

base = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"

path_main = os.path.join(base, r"ui\screens\main\MainScreen.kt")
with open(path_main, 'r', encoding='utf-8') as f:
    c = f.read()

c = c.replace('''onPassClick = {
                                bottomNavController.navigate(Screen.Pass.route) {
                                    popUpTo(bottomNavController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },''', '')

c = c.replace('''composable(Screen.Pass.route) {
                        PassScreen(
                            onMenuClick = { scope.launch { drawerState.open() } },
                            onAvatarClick = onNavigateToProfile,
                            onGetPassSuccess = {
                                onNavigateToTestSeries("nayab_tehsildar_2026")
                            }
                        )
                    }''', '')

with open(path_main, 'w', encoding='utf-8') as f:
    f.write(c)
