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
import androidx.room.Room
import android.widget.Toast
import com.example.madhumarga.ui.theme.HoneyPrimary
import com.example.madhumarga.ui.theme.TextDark
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HarvestScreen(onBack: () -> Unit) {

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

    var quantity by remember { mutableStateOf("") }
    var harvestList by remember { mutableStateOf(listOf<Harvest>()) }
    var hiveList by remember { mutableStateOf(listOf<Hive>()) }
    var selectedHive by remember { mutableStateOf<Hive?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    LaunchedEffect(Unit) {
        hiveList = db.hiveDao().getAllHives()
        harvestList = db.hiveDao().getAllHarvest()
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
                "🍯 Harvest Tracker",
                style = MaterialTheme.typography.headlineSmall,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 INPUT CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFE082)
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Select Hive", color = TextDark)

                    Spacer(modifier = Modifier.height(8.dp))

                    Box {
                        Button(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF8F00)
                            )
                        ) {
                            Text(
                                selectedHive?.name ?: "Choose Hive",
                                color = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            hiveList.forEach { hive ->
                                DropdownMenuItem(
                                    text = { Text(hive.name, color = TextDark) },
                                    onClick = {
                                        selectedHive = hive
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Honey Quantity (kg)", color = TextDark) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextDark,
                            unfocusedTextColor = TextDark
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (quantity.isNotEmpty() && selectedHive != null) {

                                val harvest = Harvest(
                                    hiveId = selectedHive!!.id,
                                    quantity = quantity.toFloat(),
                                    year = currentYear
                                )

                                db.hiveDao().insertHarvest(harvest)

                                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()

                                harvestList = db.hiveDao().getAllHarvest()
                                quantity = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF8F00)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Harvest", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "📊 Hive Output",
                style = MaterialTheme.typography.titleMedium,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 🔥 OUTPUT CARDS
            hiveList.forEach { hive ->

                val total = harvestList
                    .filter { it.hiveId == hive.id }
                    .sumOf { it.quantity.toDouble() }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF3E0)
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🐝 ${hive.name}", color = TextDark)
                        Text("🍯 $total kg", color = TextDark)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF8F00)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back", color = Color.White)
            }
        }
    }
}
