package com.example.madhumarga

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.room.Room
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.example.madhumarga.ui.theme.HoneyPrimary
import com.example.madhumarga.ui.theme.TextDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PieChartScreen(onBack: () -> Unit) {

    val context = LocalContext.current

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Madhu-Marga 🐝", color = androidx.compose.ui.graphics.Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HoneyPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(androidx.compose.ui.graphics.Color(0xFFFFF8E1))
                .padding(20.dp)
        ) {

            Text(
                "🥧 Yield Distribution",
                style = MaterialTheme.typography.headlineSmall,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (hiveList.isNotEmpty()) {

                val entries = hiveList.map { hive ->
                    val total = harvestList
                        .filter { it.hiveId == hive.id }
                        .sumOf { it.quantity.toDouble() }

                    PieEntry(total.toFloat(), hive.name)
                }

                // 🔥 CARD WRAPPER (UI UPGRADE)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = androidx.compose.ui.graphics.Color(0xFFFFF3E0)
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),

                            factory = { ctx ->
                                PieChart(ctx).apply {
                                    // 0xFF5D4037 -> (93, 64, 55)
                                    val brownColor = Color.rgb(93, 64, 55)

                                    val dataSet = PieDataSet(entries, "").apply {
                                        colors = listOf(
                                            Color.rgb(255, 193, 7),
                                            Color.rgb(255, 152, 0),
                                            Color.rgb(255, 87, 34),
                                            Color.rgb(255, 235, 59),
                                            Color.rgb(255, 204, 128)
                                        )
                                        valueTextSize = 14f
                                        valueTextColor = brownColor
                                        sliceSpace = 3f
                                    }

                                    data = PieData(dataSet)

                                    description.isEnabled = false

                                    isDrawHoleEnabled = true
                                    setHoleColor(Color.TRANSPARENT)

                                    setCenterText("Honey\nDistribution")
                                    setCenterTextSize(16f)
                                    setCenterTextColor(brownColor)

                                    setUsePercentValues(true)

                                    legend.isEnabled = true
                                    legend.textColor = brownColor

                                    animateY(1200)

                                    invalidate()
                                }
                            }
                        )
                    }
                }

            } else {

                // 🔥 EMPTY STATE
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = androidx.compose.ui.graphics.Color(0xFFFFF3E0)
                    )
                ) {
                    Text(
                        "No data available.\nAdd hives and harvest records to view distribution.",
                        modifier = Modifier.padding(16.dp),
                        color = TextDark
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFFFF8F00)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back", color = androidx.compose.ui.graphics.Color.White)
            }
        }
    }
}
