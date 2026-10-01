$content = Get-Content -Path "app\src\main\java\com\example\abhyaas\ui\screens\profile\UserProfileScreen.kt" -Raw
$target = Get-Content -Path "temp.txt" -Raw

$replacement = @"
            if (userProfile.totalTestsAttempted > 0) {
$($target.TrimEnd())
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = "No Data",
                            tint = TextSecondaryDark,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Analytics Yet",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Take your first mock test to see your progress graph and average scores here.",
                            color = TextSecondaryDark,
                            fontSize = 14.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            // Profile Quick Actions
"@

$content = $content.Replace($target, $replacement)
Set-Content -Path "app\src\main\java\com\example\abhyaas\ui\screens\profile\UserProfileScreen.kt" -Value $content
