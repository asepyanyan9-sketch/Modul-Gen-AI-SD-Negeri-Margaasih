package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserSettings
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyPrimary

@Composable
fun SettingsScreen(
  currentSettings: UserSettings,
  onSaveSettings: (UserSettings) -> Unit
) {
  val context = LocalContext.current

  var teacherName by remember(currentSettings) { mutableStateOf(currentSettings.teacherName) }
  var teacherEmail by remember(currentSettings) { mutableStateOf(currentSettings.teacherEmail) }
  var schoolName by remember(currentSettings) { mutableStateOf(currentSettings.schoolName) }
  var schoolAddress by remember(currentSettings) { mutableStateOf(currentSettings.schoolAddress) }
  var nip by remember(currentSettings) { mutableStateOf(currentSettings.nip) }
  var principalName by remember(currentSettings) { mutableStateOf(currentSettings.principalName) }
  var principalNip by remember(currentSettings) { mutableStateOf(currentSettings.principalNip) }
  var semester by remember(currentSettings) { mutableStateOf(currentSettings.semester) }
  var academicYear by remember(currentSettings) { mutableStateOf(currentSettings.academicYear) }
  var apiKey by remember(currentSettings) { mutableStateOf(currentSettings.customApiKey) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
      .testTag("settings_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header Banner
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = NavyPrimary)
    ) {
      Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .background(Color(0x33FFFFFF), RoundedCornerShape(10.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text("Pengaturan Profil & Sistem", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
          Text("Data Identitas Sekolah & Kredensial AI EDU GEN AI", fontSize = 12.sp, color = Color(0xFFDBEAFE))
        }
      }
    }

    // Profil Sekolah & Guru Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.School, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("Profil Pendidik & Satuan Pendidikan", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyPrimary)
        }

        OutlinedTextField(
          value = teacherName,
          onValueChange = { teacherName = it },
          label = { Text("Nama Lengkap Pendidik") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_teacher_name")
        )

        OutlinedTextField(
          value = nip,
          onValueChange = { nip = it },
          label = { Text("NIP Guru") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = teacherEmail,
          onValueChange = { teacherEmail = it },
          label = { Text("Email Pendidik (belajar.id)") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = schoolName,
          onValueChange = { schoolName = it },
          label = { Text("Nama Sekolah") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = schoolAddress,
          onValueChange = { schoolAddress = it },
          label = { Text("Alamat / Kecamatan Sekolah") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = principalName,
          onValueChange = { principalName = it },
          label = { Text("Nama Kepala Sekolah") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = principalNip,
          onValueChange = { principalNip = it },
          label = { Text("NIP Kepala Sekolah") },
          modifier = Modifier.fillMaxWidth()
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedTextField(
            value = semester,
            onValueChange = { semester = it },
            label = { Text("Semester") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = academicYear,
            onValueChange = { academicYear = it },
            label = { Text("Tahun Ajaran") },
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // AI & Gemini Engine Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Key, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("Integrasi Google Gemini API", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyPrimary)
        }

        Text(
          text = "Aplikasi telah terintegrasi dengan Kurikulum Merdeka Engine SD Negeri Margaasih. Anda juga dapat memasukkan Gemini API Key pribadi untuk respons berbasis AI langsung:",
          fontSize = 12.sp,
          color = Color(0xFF64748B)
        )

        OutlinedTextField(
          value = apiKey,
          onValueChange = { apiKey = it },
          label = { Text("Google Gemini API Key (Opsional)") },
          placeholder = { Text("AIzaSy...") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_api_key")
        )

        Surface(
          color = Color(0xFFECFDF5),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "✓ Aman: Kunci API disimpan secara lokal di perangkat dan tidak pernah dibagikan ke server publik.",
            color = EmeraldSuccess,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(10.dp)
          )
        }
      }
    }

    // Save Button
    Button(
      onClick = {
        val updated = currentSettings.copy(
          teacherName = teacherName,
          teacherEmail = teacherEmail,
          schoolName = schoolName,
          schoolAddress = schoolAddress,
          nip = nip,
          principalName = principalName,
          principalNip = principalNip,
          semester = semester,
          academicYear = academicYear,
          customApiKey = apiKey
        )
        onSaveSettings(updated)
        Toast.makeText(context, "Profil & Pengaturan berhasil disimpan!", Toast.LENGTH_SHORT).show()
      },
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("btn_save_settings"),
      colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
      shape = RoundedCornerShape(8.dp)
    ) {
      Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
      Spacer(modifier = Modifier.width(8.dp))
      Text("Simpan Seluruh Pengaturan", fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}
