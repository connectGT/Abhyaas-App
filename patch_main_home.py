import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\main\MainScreen.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_click = """                            onCategoryClick = { categoryId ->
                                if (categoryId == "cat_notes" || categoryId == "cat_current_affairs" || categoryId == "cat_pyq" || categoryId == "cat_practice" || categoryId == "cat_quiz") {
                                    bottomNavController.navigate("empty_state")
                                } else {
                                    onNavigateToTestSeries("nayab_tehsildar_2026")
                                }
                            }"""

new_click = """                            onCategoryClick = { categoryId ->
                                if (categoryId == "cat_live") {
                                    onNavigateToTestSeries("nayab_tehsildar_2026")
                                } else {
                                    bottomNavController.navigate("empty_state")
                                }
                            }"""

content = content.replace(old_click, new_click)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\main\MainScreen.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("MainScreen patched.")
