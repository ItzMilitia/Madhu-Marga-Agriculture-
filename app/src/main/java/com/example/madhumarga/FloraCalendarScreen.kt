package com.example.madhumarga

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class FloraInfo(
    val name: String,
    val season: String,
    val honeyLevel: String,
    val suggestion: String
)

@Composable
fun FloraCalendarScreen(onBack: () -> Unit) {

    val floraData = mapOf(
        "January" to listOf(
            FloraInfo("Mustard", "Winter", "High", "Best time for honey collection"),
            FloraInfo("Sunflower", "Winter", "Medium", "Good steady yield")
        ),
        "February" to listOf(
            FloraInfo("Neem", "Spring", "Medium", "Improves hive health"),
            FloraInfo("Mango Blossom", "Spring", "High", "Boosts honey production")
        ),
        "March" to listOf(
            FloraInfo("Eucalyptus", "Spring", "High", "Excellent nectar source"),
            FloraInfo("Coconut", "Spring", "Low", "Supportive only")
        ),
        "April" to listOf(
            FloraInfo("Acacia", "Summer", "High", "Strong honey flow"),
            FloraInfo("Jamun", "Summer", "Medium", "Moderate yield")
        )
    )

    // 🔥 CURRENT MONTH DETECTION
    val currentMonth = java.text.SimpleDateFormat(
        "MMMM",
        java.util.Locale.getDefault()
    ).format(java.util.Date())

    // 🔥 SMART LOGIC
    val currentFlora = floraData[currentMonth] ?: emptyList()

    val bestFlora = currentFlora.maxByOrNull {
        when (it.honeyLevel) {
            "High" -> 3
            "Medium" -> 2
            else -> 1
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1))
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            "🌼 Flora Calendar",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFFF8F00)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 SMART SUGGESTION CARD
        if (bestFlora != null) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF3E0)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        "🤖 Smart Suggestion",
                        color = Color(0xFF5D4037),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("🌼 Focus on: ${bestFlora.name}", color = Color(0xFF3E2723))
                    Text("🍯 Yield: ${bestFlora.honeyLevel}", color = Color(0xFF3E2723))
                    Text("📌 ${bestFlora.suggestion}", color = Color(0xFF3E2723))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 🔥 MONTH CARDS
        floraData.forEach { (month, flowers) ->

            val isCurrent = month == currentMonth

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrent)
                        Color(0xFFFFD54F)
                    else
                        Color(0xFFFFE082)
                )
            ) {

                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        "$month ${if (isCurrent) "🔥 Current Season" else ""}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF5D4037)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    flowers.forEach { flora ->

                        Text("🌼 ${flora.name}", color = Color(0xFF3E2723))
                        Text("🍯 Honey Flow: ${flora.honeyLevel}", color = Color(0xFF3E2723))
                        Text("📌 ${flora.suggestion}", color = Color(0xFF3E2723))

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF8F00)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back", color = Color(0xFF5D4037))
        }
    }
}