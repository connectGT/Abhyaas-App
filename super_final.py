# -*- coding: utf-8 -*-
import os
base = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"

path_main = os.path.join(base, r"ui\screens\main\MainScreen.kt")
with open(path_main, 'r', encoding='utf-8') as f:
    c = f.read()
c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen\n', '')
c = c.replace('BottomNavItem("Pass", Screen.Pass.route, Icons.Default.CardMembership),', '')
c = c.replace('''onPassClick = {
                        navController.navigate("pass")
                    },''', '')
c = c.replace('''onPassClick = {
                        navController.navigate(Screen.Pass.route)
                    },''', '')
c = c.replace('''composable(Screen.Pass.route) {
            PassScreen()
        }''', '')
# there might be another onPassClick format in MainScreen
c = c.replace('''                    onPassClick = {
                        navController.navigate("pass")
                    }''', '')
c = c.replace('''                    onPassClick = {
                        navController.navigate(Screen.Pass.route)
                    }''', '')
import re
c = re.sub(r'onPassClick\s*=\s*\{\s*navController\.navigate\([^\)]+\)\s*\},?', '', c)
with open(path_main, 'w', encoding='utf-8') as f:
    f.write(c)

path_smls = os.path.join(base, r"ui\screens\study\StudyMaterialListScreen.kt")
with open(path_smls, 'r', encoding='utf-8') as f:
    c = f.read()
c = c.replace('onUnlockClick: () -> Unit = {},', '')
c = c.replace('onUnlockClick: () -> Unit = {}', '')
c = c.replace('onMaterialClick: (materialId: String, seriesId: String) -> Unit = { _, _ -> },', 'onMaterialClick: (materialId: String, seriesId: String) -> Unit = { _, _ -> }')

c = c.replace('''onClick = {
                                if (material.isFree) {
                                    onMaterialClick(material.id, seriesId)
                                } else {
                                    onUnlockClick()
                                }
                            }''', 'onClick = { onMaterialClick(material.id, seriesId) }')
c = c.replace('''if (!material.isFree) {
                                    Container(
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        Text(text = "LOCKED", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }''', '')
c = c.replace('''// Lock Icon Overlay
                            if (!material.isFree) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }''', '')
c = c.replace('''LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {''', '''LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Tap any note to open it. To add your PDFs, upload them to Google Drive and share the link.",
                    color = TextSecondaryDark,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
                )
            }''')

with open(path_smls, 'w', encoding='utf-8') as f:
    f.write(c)