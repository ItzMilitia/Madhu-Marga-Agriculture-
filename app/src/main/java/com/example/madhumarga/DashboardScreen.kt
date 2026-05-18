package com.example.madhumarga

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.madhumarga.ui.theme.TextDark
import java.io.File
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    val honeyOrange = Color(0xFFFF8F00)
    val honeyLight = Color(0xFFFFF3E0)
    val honeyAccent = Color(0xFFFFE082)

    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hive-db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    var hiveList by remember { mutableStateOf(listOf<Hive>()) }
    var harvestList by remember { mutableStateOf(listOf<Harvest>()) }

    LaunchedEffect(Unit) {
        hiveList = db.hiveDao().getAllHives()
        harvestList = db.hiveDao().getAllHarvest()
    }

    val totalHives = hiveList.size
    val totalHoney = harvestList.sumOf { it.quantity.toDouble() }

    val bestHive = hiveList.maxByOrNull { hive ->
        harvestList
            .filter { it.hiveId == hive.id }
            .sumOf { it.quantity.toDouble() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Madhu-Marga 🐝", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = honeyOrange
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFFFF8E1))
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Text(
                "📊 Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🍯 TOTAL HONEY CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = honeyAccent),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Total Honey Yield",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "${"%.2f".format(totalHoney)} kg",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // 🐝 TOTAL HIVES
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = honeyLight)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Hives",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextDark
                        )
                        Text(
                            "$totalHives",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    }
                }

                // 🏆 BEST HIVE
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = honeyLight)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Top Hive",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextDark
                        )

                        val topHiveName = bestHive?.name ?: "None"

                        Text(
                            topHiveName,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            color = TextDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ⚠ ALERTS
            Text(
                "⚠ System Alerts",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (totalHoney < 10)
                        Color(0xFFFFEBEE)
                    else
                        Color(0xFFE8F5E9)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        if (totalHoney < 10) "📉" else "✅",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        if (totalHoney < 10)
                            "Low production detected. Check hive health and nutrition."
                        else
                            "All systems normal. Hive productivity is stable.",
                        color = if (totalHoney < 10)
                            Color(0xFFC62828)
                        else
                            Color(0xFF2E7D32)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 📄 EXPORT BUTTON (REAL FILE EXPORT)
            Button(
                onClick = {

                    val report = buildString {
                        append("Madhu-Marga Report\n\n")
                        append("Date: ${Date()}\n\n")
                        append("Total Honey: ${"%.2f".format(totalHoney)} kg\n")
                        append("Total Hives: $totalHives\n\n")

                        append("Hive Breakdown:\n")
                        hiveList.forEach { hive ->
                            val total = harvestList
                                .filter { it.hiveId == hive.id }
                                .sumOf { it.quantity.toDouble() }

                            append("${hive.name}: ${"%.2f".format(total)} kg\n")
                        }
                    }

                    try {
                        val file = File(
                            context.getExternalFilesDir(null),
                            "madhu_report.txt"
                        )

                        file.writeText(report)

                        Toast.makeText(
                            context,
                            "Report saved!",
                            Toast.LENGTH_LONG
                        ).show()

                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Error: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = honeyOrange)
            ) {
                Text("Export Report", style = MaterialTheme.typography.titleMedium, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Back to Menu", color = TextDark)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}