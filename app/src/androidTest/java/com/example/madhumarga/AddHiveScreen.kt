package com.example.madhumarga

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddHiveScreen(onBack: () -> Unit) {

    var hiveName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(20.dp)) {

        Text("Add Hive", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = hiveName,
            onValueChange = { hiveName = it },
            label = { Text("Hive Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // we will save later
        }) {
            Text("Save Hive")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}