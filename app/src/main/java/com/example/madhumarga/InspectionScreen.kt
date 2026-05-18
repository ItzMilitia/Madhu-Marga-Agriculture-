package com.example.madhumarga

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.room.Room
import android.widget.Toast
import com.example.madhumarga.ui.theme.HoneyPrimary
import com.example.madhumarga.ui.theme.TextDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InspectionScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hive-db"
        ).fallbackToDestructiveMigration(true)
            .allowMainThreadQueries()
            .build()
    }

    var hiveList by remember { mutableStateOf(listOf<Hive>()) }
    var selectedHive by remember { mutableStateOf<Hive?>(null) }
    var hiveExpanded by remember { mutableStateOf(false) }
    var queenPresent by remember { mutableStateOf(false) }
    var pestsSeen by remember { mutableStateOf(false) }
    var activityLevel by remember { mutableStateOf("Medium") }
    var alertMessage by remember { mutableStateOf("") }
    var healthScore by remember { mutableIntStateOf(0) }
    var aiAdvice by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        hiveList = db.hiveDao().getAllHives()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Madhu-Marga 🐝", color = Color.White) },
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
                .background(Color(0xFFFFF8E1))
                .padding(20.dp)
        ) {

            Text(
                "🔍 Inspection Analysis",
                style = MaterialTheme.typography.headlineSmall,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 HIVE SELECTION CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE082)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        "Select Hive",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box {
                        Button(
                            onClick = { hiveExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8F00))
                        ) {
                            Text(
                                selectedHive?.name ?: "Choose Hive",
                                color = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = hiveExpanded,
                            onDismissRequest = { hiveExpanded = false }
                        ) {
                            hiveList.forEach { hive ->
                                DropdownMenuItem(
                                    text = { Text(hive.name, color = TextDark) },
                                    onClick = {
                                        selectedHive = hive
                                        hiveExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 INPUT CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = queenPresent,
                            onCheckedChange = { queenPresent = it }
                        )
                        Text("Queen Present", color = TextDark)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = pestsSeen,
                            onCheckedChange = { pestsSeen = it }
                        )
                        Text("Pests Seen", color = TextDark)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Activity Level", color = TextDark)

                    var levelExpanded by remember { mutableStateOf(false) }

                    Box {
                        Button(
                            onClick = { levelExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8F00))
                        ) {
                            Text(activityLevel, color = Color.White)
                        }

                        DropdownMenu(
                            expanded = levelExpanded,
                            onDismissRequest = { levelExpanded = false }
                        ) {
                            listOf("High", "Medium", "Low").forEach {
                                DropdownMenuItem(
                                    text = { Text(it, color = TextDark) },
                                    onClick = {
                                        activityLevel = it
                                        levelExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 ANALYZE BUTTON
            Button(
                onClick = {
                    if (selectedHive == null) {
                        Toast.makeText(context, "Select a hive first!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    healthScore = calculateHiveHealth(queenPresent, pestsSeen, activityLevel)
                    alertMessage = "Analyzing..."

                    val prompt = """
                        You are an expert beekeeper AI assistant.

                        Analyze this hive condition:

                        Queen Present: $queenPresent
                        Pests Seen: $pestsSeen
                        Activity Level: $activityLevel

                        Give short practical advice for the farmer.
                    """.trimIndent()

                    GeminiHelper.getHiveAdvice(prompt) { response ->
                        aiAdvice = response
                        alertMessage = response

                        val inspection = Inspection(
                            hiveId = selectedHive!!.id,
                            queenPresent = queenPresent,
                            pestsSeen = pestsSeen,
                            activityLevel = activityLevel,
                            analysisResult = response,
                            healthScore = healthScore
                        )

                        db.hiveDao().insertInspection(inspection)

                        // UI updates like Toast need to be on the Main Thread
                        android.os.Handler(android.os.Looper.getMainLooper()).post {
                            Toast.makeText(
                                context,
                                "Inspection saved (${selectedHive!!.name})",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8F00)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Analyze & Save", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 RESULT CARD
            if (alertMessage.isNotEmpty()) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (healthScore < 40)
                            Color(0xFFFFEBEE)
                        else
                            Color(0xFFE8F5E9)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            "Health Score: $healthScore%",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            alertMessage,
                            color = if (healthScore < 40)
                                Color.Red
                            else
                                Color(0xFF2E7D32)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8F00)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back", color = Color.White)
            }
        }
    }
}
