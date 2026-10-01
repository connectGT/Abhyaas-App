import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\tests\TestListScreen.kt", "r", encoding="utf-8") as f:
    content = f.read()

# I will find the TabRow and the activeTabIndex variable and delete them.
# The code is:
'''
        ) {
            val activeTabIndex = uiState.selectedSubTabIndex.coerceIn(0, (uiState.subTabs.size - 1).coerceAtLeast(0))

            // Sub-tabs (dynamically driven from ViewModel state)
            TabRow(
                ...
                }
            }

            Column(
'''

import re
pattern = r"val activeTabIndex = uiState\.selectedSubTabIndex.*?// Sub-tabs.*?TabRow\([^)]*\)\s*\{.*?\n            \}\n\n            Column\("

new_content = re.sub(
    r"val activeTabIndex = uiState\.selectedSubTabIndex.*?TabRow\(.*?\)\s*\{.*?\n            \}\n\n            Column\(",
    r"Column(",
    content,
    flags=re.DOTALL
)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\tests\TestListScreen.kt", "w", encoding="utf-8") as f:
    f.write(new_content)

print("TestListScreen tabs removed.")
