package com.example.service

import android.content.Context
import android.content.Intent
import com.example.data.model.ProjectEntity
import com.example.data.model.UserSettings
import org.json.JSONArray
import org.json.JSONObject

object DocumentExportHelper {
  fun shareAsPlainText(context: Context, title: String, content: String) {
    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TITLE, title)
      putExtra(Intent.EXTRA_SUBJECT, title)
      putExtra(Intent.EXTRA_TEXT, content)
      type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Bagikan Dokumen Pembelajaran")
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(shareIntent)
  }

  fun exportToPrintableHtml(title: String, content: String, settings: UserSettings): String {
    val formattedBody = content
      .replace(Regex("^# (.+)$", RegexOption.MULTILINE), "<h1>$1</h1>")
      .replace(Regex("^## (.+)$", RegexOption.MULTILINE), "<h2>$1</h2>")
      .replace(Regex("^### (.+)$", RegexOption.MULTILINE), "<h3>$1</h3>")
      .replace(Regex("^\\* (.+)$", RegexOption.MULTILINE), "<li>$1</li>")
      .replace(Regex("\\*\\*(.+?)\\*\\*"), "<strong>$1</strong>")
      .replace(Regex("\\*(.+?)\\*"), "<em>$1</em>")
      .replace("\n\n", "<br/><br/>")

    return """
<!DOCTYPE html>
<html lang="id">
<head>
  <meta charset="UTF-8">
  <title>$title - ${settings.schoolName}</title>
  <style>
    @media print {
      .no-print { display: none; }
      body { margin: 0; padding: 1.5cm; }
    }
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      line-height: 1.6;
      color: #0f172a;
      background-color: #ffffff;
      padding: 30px;
      max-width: 850px;
      margin: 0 auto;
    }
    .kop-surat {
      text-align: center;
      border-bottom: 3px double #1e3a8a;
      padding-bottom: 12px;
      margin-bottom: 24px;
    }
    .kop-surat h2 { margin: 0; font-size: 18pt; color: #1e3a8a; }
    .kop-surat h3 { margin: 2px 0; font-size: 14pt; }
    .kop-surat p { margin: 2px 0; font-size: 10pt; color: #64748b; }
    h1 { color: #1e3a8a; font-size: 18pt; margin-top: 20px; border-bottom: 1px solid #e2e8f0; padding-bottom: 6px; }
    h2 { color: #2563eb; font-size: 14pt; margin-top: 18px; }
    h3 { color: #334155; font-size: 12pt; }
    table { width: 100%; border-collapse: collapse; margin: 16px 0; }
    th, td { border: 1px solid #cbd5e1; padding: 8px 12px; text-align: left; }
    th { background-color: #f1f5f9; color: #1e3a8a; font-weight: bold; }
    .btn-print {
      background-color: #1e3a8a;
      color: white;
      border: none;
      padding: 10px 20px;
      border-radius: 6px;
      cursor: pointer;
      font-size: 14px;
      font-weight: 600;
      margin-bottom: 20px;
    }
    .footer-ttd {
      margin-top: 40px;
      display: flex;
      justify-content: space-between;
    }
  </style>
</head>
<body>
  <div class="no-print">
    <button class="btn-print" onclick="window.print()">🖨️ Cetak / Simpan sebagai PDF</button>
  </div>
  <div class="kop-surat">
    <h2>PEMERINTAH KABUPATEN BANDUNG</h2>
    <h3>DINAS PENDIDIKAN — ${settings.schoolName.uppercase()}</h3>
    <p>${settings.schoolAddress} | Email: ${settings.teacherEmail}</p>
  </div>
  <div class="content">
    $formattedBody
  </div>
</body>
</html>
    """.trimIndent()
  }

  fun exportProjectsToJson(projects: List<ProjectEntity>): String {
    val arr = JSONArray()
    for (p in projects) {
      val obj = JSONObject().apply {
        put("id", p.id)
        put("title", p.title)
        put("generatorType", p.generatorType)
        put("subject", p.subject)
        put("grade", p.grade)
        put("phase", p.phase)
        put("topic", p.topic)
        put("contentJson", p.contentJson)
        put("status", p.status)
        put("createdAt", p.createdAt)
        put("updatedAt", p.updatedAt)
      }
      arr.put(obj)
    }
    return arr.toString(2)
  }

  fun importProjectsFromJson(jsonStr: String): List<ProjectEntity> {
    val result = mutableListOf<ProjectEntity>()
    val arr = JSONArray(jsonStr)
    for (i in 0 until arr.length()) {
      val obj = arr.getJSONObject(i)
      result.add(
        ProjectEntity(
          id = obj.optString("id", java.util.UUID.randomUUID().toString()),
          title = obj.getString("title"),
          generatorType = obj.getString("generatorType"),
          subject = obj.optString("subject", "IPAS"),
          grade = obj.optString("grade", "Kelas 4"),
          phase = obj.optString("phase", "Fase B"),
          topic = obj.optString("topic", ""),
          contentJson = obj.getString("contentJson"),
          status = obj.optString("status", "Selesai"),
          createdAt = obj.optLong("createdAt", System.currentTimeMillis()),
          updatedAt = obj.optLong("updatedAt", System.currentTimeMillis())
        )
      )
    }
    return result
  }
}
