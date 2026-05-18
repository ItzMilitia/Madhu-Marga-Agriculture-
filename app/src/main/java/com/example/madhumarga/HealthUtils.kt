package com.example.madhumarga

fun calculateHiveHealth(
    queenPresent: Boolean,
    pests: Boolean,
    activity: String
): Int {

    var score = 50

    if (queenPresent) score += 30 else score -= 30
    if (!pests) score += 20 else score -= 20

    when (activity) {
        "High" -> score += 20
        "Medium" -> score += 10
        "Low" -> score -= 20
    }

    return score.coerceIn(0, 100)
}
