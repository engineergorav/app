package com.example.talkitiveai.network

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object ApiService {

    private const val BASE_URL = "https://api.openai.com/v1/chat/completions"
    private const val API_KEY = "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\n" // Replace with your actual key

    private val client = OkHttpClient()

    fun sendMessage(message: String, callback: (String?) -> Unit) {
        val requestBody = JSONObject().apply {
            put("model", "gpt-3.5-turbo")
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", message)
                })
            })
        }

        val body = RequestBody.create(
            MediaType.parse("application/json"),
            requestBody.toString()
        )

        val request = Request.Builder()
            .url(BASE_URL)
            .header("Authorization", "Bearer $API_KEY")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        callback(null)
                    } else {
                        val responseText = response.body()?.string()
                        val json = JSONObject(responseText)
                        val reply = json
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content")
                        callback(reply.trim())
                    }
                }
            }
        })
    }
}
