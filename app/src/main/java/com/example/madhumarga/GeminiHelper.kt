package com.example.madhumarga

import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType

object GeminiHelper {

    // 🔥 PASTE YOUR API KEY HERE
    private const val API_KEY = "your_api_key"

    fun getHiveAdvice(
        prompt: String,
        callback: (String) -> Unit
    ) {

        val client = OkHttpClient()

        val json = """
        {
          "contents": [{
            "parts": [{
              "text": "$prompt"
            }]
          }]
        }
        """.trimIndent()

        val body = RequestBody.create(
            "application/json".toMediaType(),
            json
        )

        val request = Request.Builder()
            .url(
                "https://generativelanguage.googleapis.com/v1/models/gemini-2.0-flash:generateContent?key=$API_KEY"
            )
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback("AI Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {

                val responseBody = response.body?.string()

                println(responseBody)

                try {

                    val text = JSONObject(responseBody)
                        .getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")

                    callback(text)

                } catch (e: Exception) {
                    callback("Honey Hive is in good health")
                }
            }
        })
    }
}
