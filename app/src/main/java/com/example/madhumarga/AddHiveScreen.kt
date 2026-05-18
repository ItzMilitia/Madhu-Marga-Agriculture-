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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHiveScreen(onBack: () -> Unit) {

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

    var hiveName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var hiveList by remember { mutableStateOf(listOf<Hive>()) }

    LaunchedEffect(Unit) {
        hiveList = db.hiveDao().getAllHives()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Madhu-Marga 🐝", color = TextDark) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HoneyPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF8E1))
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp)
        ) {

            Text(
                "🐝 Manage Hives",
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

                    OutlinedTextField(
                        value = hiveName,
                        onValueChange = { hiveName = it },
                        label = { Text("Hive Name", color = TextDark) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextDark,
                            unfocusedTextColor = TextDark
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location", color = TextDark) },
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
                            if (hiveName.isNotEmpty() && location.isNotEmpty()) {

                                val hive = Hive(name = hiveName, location = location)
                                db.hiveDao().insertHive(hive)

                                Toast.makeText(context, "Hive Saved!", Toast.LENGTH_SHORT).show()

                                hiveList = db.hiveDao().getAllHives()

                                hiveName = ""
                                location = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF8F00)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Hive", color = TextDark)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "📋 Saved Hives",
                style = MaterialTheme.typography.titleMedium,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 🔥 HIVE LIST
            hiveList.forEach { hive ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF3E0)
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            "🐝 ${hive.name}",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            "📍 ${hive.location}",
                            color = TextDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF8F00)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back", color = TextDark)
            }
        }
    }
}
