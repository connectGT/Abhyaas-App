with open(r"app\src\main\java\com\example\abhyaas\ui\screens\tests\TestSeriesDetailScreen.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_click = """                    FolderCard(
                        folder = folder,
                        onClick = { onFolderClick(series.id, folder.title) }
                    )"""

new_click = """                    FolderCard(
                        folder = folder,
                        onClick = { 
                            if (selectedTabIndex == 2) {
                                onFolderClick(series.id, "empty_state")
                            } else {
                                onFolderClick(series.id, folder.title)
                            }
                        }
                    )"""

content = content.replace(old_click, new_click)

with open(r"app\src\main\java\com\example\abhyaas\ui\screens\tests\TestSeriesDetailScreen.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("TestSeriesDetailScreen.kt patched successfully.")
