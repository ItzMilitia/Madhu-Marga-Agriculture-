package com.example.madhumarga

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.madhumarga.ui.theme.TextDark

@Composable
fun SplashScreen(onFinish: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds splash
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color(0xFFFFC107)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                "🐝",
                fontSize = 80.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Madhu-Marga",
                color = TextDark,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                "Smart Beekeeping",
                color = TextDark
            )
        }
    }
}
