package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsManager(context: Context) {
  private val prefs: SharedPreferences = context.getSharedPreferences("edugen_settings", Context.MODE_PRIVATE)

  private val _settings = MutableStateFlow(loadSettings())
  val settings: StateFlow<UserSettings> = _settings.asStateFlow()

  fun loadSettings(): UserSettings {
    return UserSettings(
      teacherName = prefs.getString("teacher_name", "Asep Yanyan, S.Pd.") ?: "Asep Yanyan, S.Pd.",
      teacherEmail = prefs.getString("teacher_email", "asep.yanyan9@guru.sd.belajar.id") ?: "asep.yanyan9@guru.sd.belajar.id",
      schoolName = prefs.getString("school_name", "SD Negeri Margaasih") ?: "SD Negeri Margaasih",
      schoolAddress = prefs.getString("school_address", "Kec. Margaasih, Kab. Bandung") ?: "Kec. Margaasih, Kab. Bandung",
      nip = prefs.getString("teacher_nip", "19870512 201001 1 008") ?: "19870512 201001 1 008",
      principalName = prefs.getString("principal_name", "Hj. Ratnaningsih, M.M.Pd.") ?: "Hj. Ratnaningsih, M.M.Pd.",
      principalNip = prefs.getString("principal_nip", "19700315 199303 2 004") ?: "19700315 199303 2 004",
      semester = prefs.getString("semester", "Semester 1 (Ganjil)") ?: "Semester 1 (Ganjil)",
      academicYear = prefs.getString("academic_year", "2024/2025") ?: "2024/2025",
      customApiKey = prefs.getString("custom_api_key", "") ?: ""
    )
  }

  fun updateSettings(newSettings: UserSettings) {
    prefs.edit()
      .putString("teacher_name", newSettings.teacherName)
      .putString("teacher_email", newSettings.teacherEmail)
      .putString("school_name", newSettings.schoolName)
      .putString("school_address", newSettings.schoolAddress)
      .putString("teacher_nip", newSettings.nip)
      .putString("principal_name", newSettings.principalName)
      .putString("principal_nip", newSettings.principalNip)
      .putString("semester", newSettings.semester)
      .putString("academic_year", newSettings.academicYear)
      .putString("custom_api_key", newSettings.customApiKey)
      .apply()
    _settings.value = newSettings
  }
}
