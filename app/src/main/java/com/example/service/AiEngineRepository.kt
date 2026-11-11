package com.example.service

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.UserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class AiEngineRepository {
  private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .build()

  suspend fun generateContent(
    prompt: String,
    systemInstruction: String,
    userSettings: UserSettings,
    fallbackGenerator: () -> String
  ): GenerationResult = withContext(Dispatchers.IO) {
    val apiKey = when {
      userSettings.customApiKey.isNotBlank() -> userSettings.customApiKey.trim()
      try {
        BuildConfig.GEMINI_API_KEY.isNotBlank() && BuildConfig.GEMINI_API_KEY != "MY_GEMINI_API_KEY"
      } catch (e: Exception) { false } -> BuildConfig.GEMINI_API_KEY.trim()
      else -> ""
    }

    if (apiKey.isBlank()) {
      Log.i("AiEngineRepository", "API key not set, using built-in pedagogical curriculum engine.")
      return@withContext GenerationResult(
        content = fallbackGenerator(),
        source = GenerationSource.BUILT_IN_ENGINE,
        message = "Dihasilkan secara cerdas oleh Kurikulum Merdeka Engine SD Negeri Margaasih (Tambahkan Gemini API Key di Pengaturan untuk koneksi AI langsung)."
      )
    }

    try {
      val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"
      
      val requestBodyJson = JSONObject().apply {
        put("contents", JSONArray().apply {
          put(JSONObject().apply {
            put("role", "user")
            put("parts", JSONArray().apply {
              put(JSONObject().apply {
                put("text", "$systemInstruction\n\n$prompt")
              })
            })
          })
        })
        put("generationConfig", JSONObject().apply {
          put("temperature", 0.7)
          put("maxOutputTokens", 4096)
        })
      }

      val request = Request.Builder()
        .url(url)
        .post(requestBodyJson.toString().toRequestBody("application/json".toMediaType()))
        .build()

      val response = client.newCall(request).execute()
      val responseBody = response.body?.string().orEmpty()

      if (!response.isSuccessful) {
        Log.w("AiEngineRepository", "Gemini API error code ${response.code}: $responseBody")
        return@withContext GenerationResult(
          content = fallbackGenerator(),
          source = GenerationSource.BUILT_IN_ENGINE_FALLBACK,
          message = "Gemini API merespons kode ${response.code}. Dialihkan secara otomatis ke Kurikulum Merdeka Engine SD Negeri Margaasih."
        )
      }

      val json = JSONObject(responseBody)
      val candidates = json.optJSONArray("candidates")
      val firstCandidate = candidates?.optJSONObject(0)
      val contentObj = firstCandidate?.optJSONObject("content")
      val parts = contentObj?.optJSONArray("parts")
      val text = parts?.optJSONObject(0)?.optString("text")

      if (!text.isNullOrBlank()) {
        GenerationResult(
          content = text.trim(),
          source = GenerationSource.GEMINI_AI,
          message = "Berhasil digenerate secara langsung menggunakan Google Gemini AI."
        )
      } else {
        GenerationResult(
          content = fallbackGenerator(),
          source = GenerationSource.BUILT_IN_ENGINE_FALLBACK,
          message = "Respons AI kosong. Konten dihasilkan oleh Kurikulum Merdeka Engine SD Negeri Margaasih."
        )
      }
    } catch (e: Exception) {
      Log.e("AiEngineRepository", "Gemini request failed: ${e.message}", e)
      GenerationResult(
        content = fallbackGenerator(),
        source = GenerationSource.BUILT_IN_ENGINE_FALLBACK,
        message = "Koneksi ke Gemini API terputus (${e.localizedMessage ?: "Network error"}). Digenerate oleh Kurikulum Merdeka Engine."
      )
    }
  }
}

enum class GenerationSource {
  GEMINI_AI,
  BUILT_IN_ENGINE,
  BUILT_IN_ENGINE_FALLBACK
}

data class GenerationResult(
  val content: String,
  val source: GenerationSource,
  val message: String
)
