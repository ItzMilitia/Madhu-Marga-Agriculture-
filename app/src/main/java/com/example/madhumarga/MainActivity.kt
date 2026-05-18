package com.example.madhumarga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madhumarga.ui.theme.MadhuMargaTheme
import com.example.madhumarga.ui.theme.HoneyPrimary
import com.example.madhumarga.ui.theme.TextDark
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            MadhuMargaTheme {
                var currentScreen by remember { mutableStateOf("splash") }

                when (currentScreen) {
                    "splash" -> SplashScreen {
                        currentScreen = "main"
                    }
                    "main" -> MainMenu(
                        onNavigate = { screen -> currentScreen = screen }
                    )
                    "addHive" -> AddHiveScreen(
                        onBack = { currentScreen = "main" }
                    )
                    "inspection" -> InspectionScreen(
                        onBack = { currentScreen = "main" }
                    )
                    "harvest" -> HarvestScreen(
                        onBack = { currentScreen = "main" }
                    )
                    "chart" -> ChartScreen(
                        onBack = { currentScreen = "main" }
                    )
                    "dashboard" -> DashboardScreen(
                        onBack = { currentScreen = "main" }
                    )
                    "pieChart" -> PieChartScreen(
                        onBack = { currentScreen = "main" }
                    )
                    "flora" -> FloraCalendarScreen(
                        onBack = { currentScreen = "main" }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(onNavigate: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Madhu-Marga 🐝", color = TextDark) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HoneyPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFFFF8E1))
                .padding(20.dp)
        ) {
            Text(
                text = "Welcome Back!",
                fontSize = 24.sp,
                color = TextDark,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            MenuCard("🐝 Manage Hives") { onNavigate("addHive") }
            MenuCard("📋 New Inspection") { onNavigate("inspection") }
            MenuCard("🍯 Log Harvest") { onNavigate("harvest") }
            MenuCard("🌼 Flora Calendar") { onNavigate("flora") }
            MenuCard("📊 View Analytics") { onNavigate("chart") }
            MenuCard("📈 Dashboard") { onNavigate("dashboard") }
            MenuCard("🥧 Yield Distribution") { onNavigate("pieChart") }
        }
    }
}

@Composable
fun MenuCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFE082)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextDark
            )
        }
    }
}
