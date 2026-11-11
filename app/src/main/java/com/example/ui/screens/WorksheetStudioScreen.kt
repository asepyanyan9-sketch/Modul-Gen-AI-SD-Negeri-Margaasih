package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GeneratorTypes
import com.example.data.model.UserSettings
import com.example.service.CurriculumKnowledgeBase
import com.example.ui.components.DocumentViewer
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoyalBlue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun WorksheetStudioScreen(
  userSettings: UserSettings,
  isGenerating: Boolean,
  outputContent: String?,
  statusMessage: String?,
  onGenerate: (subject: String, grade: String, topic: String, worksheetType: String, count: Int) -> Unit,
  onSave: (title: String, type: String, subject: String, grade: String, phase: String, topic: String, content: String) -> Unit
) {
  var selectedSubject by remember { mutableStateOf(CurriculumKnowledgeBase.SUBJECTS[0]) }
  var selectedGrade by remember { mutableStateOf("Kelas 4") }
  val currentPhase = CurriculumKnowledgeBase.getPhaseForGrade(selectedGrade)
  var topicText by remember { mutableStateOf("Bagian Tubuh Tumbuhan dan Fungsinya") }
  var selectedType by remember { mutableStateOf("LKPD 1 Halaman Ceria") }
  var questionCount by remember { mutableIntStateOf(5) }

  val worksheetTypes = listOf(
    "LKPD 1 Halaman Ceria",
    "LKPD Tematik Terpadu",
    "LKPD Eksperimen Sains / IPAS",
    "LKPD Berbasis Proyek (PjBL)",
    "LKPD Observasi Lingkungan Sekolah"
  )

  var isSubjectDropdownOpen by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
      .testTag("worksheet_studio_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header Banner
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = RoyalBlue)
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
          Icon(Icons.Default.Assignment, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text("Worksheet Studio AI", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
          Text("Generator Lembar Kerja Peserta Didik (LKPD) Siap Cetak", fontSize = 12.sp, color = Color(0xFFDBEAFE))
        }
      }
    }

    // Input Form Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Konfigurasi LKPD", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = RoyalBlue)

        // Mata Pelajaran
        ExposedDropdownMenuBox(
          expanded = isSubjectDropdownOpen,
          onExpandedChange = { isSubjectDropdownOpen = it }
        ) {
          OutlinedTextField(
            value = selectedSubject,
            onValueChange = {},
            readOnly = true,
            label = { Text("Mata Pelajaran") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSubjectDropdownOpen) },
            modifier = Modifier
              .fillMaxWidth()
              .menuAnchor(MenuAnchorType.PrimaryNotEditable)
          )
          ExposedDropdownMenu(
            expanded = isSubjectDropdownOpen,
            onDismissRequest = { isSubjectDropdownOpen = false }
          ) {
            CurriculumKnowledgeBase.SUBJECTS.forEach { subject ->
              DropdownMenuItem(
                text = { Text(subject) },
                onClick = {
                  selectedSubject = subject
                  isSubjectDropdownOpen = false
                }
              )
            }
          }
        }

        // Kelas
        Text("Sasaran Kelas:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          CurriculumKnowledgeBase.GRADES.forEach { grade ->
            FilterChip(
              selected = selectedGrade == grade,
              onClick = { selectedGrade = grade },
              label = { Text(grade, fontSize = 12.sp) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = RoyalBlue,
                selectedLabelColor = Color.White
              )
            )
          }
        }

        // Topik
        OutlinedTextField(
          value = topicText,
          onValueChange = { topicText = it },
          label = { Text("Topik LKPD") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_worksheet_topic")
        )

        // Tipe LKPD
        Text("Pilihan Tipe LKPD:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
        worksheetTypes.forEach { type ->
          FilterChip(
            selected = selectedType == type,
            onClick = { selectedType = type },
            label = { Text(type, fontSize = 12.sp) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = Color(0xFFEFF6FF),
              selectedLabelColor = RoyalBlue
            ),
            modifier = Modifier.fillMaxWidth()
          )
        }

        // Jumlah Soal
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("Jumlah Soal:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
          Spacer(modifier = Modifier.width(12.dp))
          listOf(3, 5, 8, 10).forEach { cnt ->
            FilterChip(
              selected = questionCount == cnt,
              onClick = { questionCount = cnt },
              label = { Text("$cnt Soal", fontSize = 11.sp) },
              modifier = Modifier.padding(horizontal = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Generate Button
        Button(
          onClick = {
            onGenerate(selectedSubject, selectedGrade, topicText, selectedType, questionCount)
          },
          enabled = !isGenerating && topicText.isNotBlank(),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("btn_generate_worksheet"),
          colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
          shape = RoundedCornerShape(8.dp)
        ) {
          if (isGenerating) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Text("AI Sedang Menyusun LKPD...")
          } else {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Generate LKPD Interaktif", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Results Section
    if (!outputContent.isNullOrBlank()) {
      DocumentViewer(
        title = "LKPD $selectedSubject - $selectedGrade ($topicText)",
        initialContent = outputContent,
        generatorType = GeneratorTypes.WORKSHEET_STUDIO,
        statusMessage = statusMessage,
        userSettings = userSettings,
        onSave = { savedContent ->
          onSave(
            "LKPD $selectedSubject $selectedGrade — $topicText",
            GeneratorTypes.WORKSHEET_STUDIO,
            selectedSubject,
            selectedGrade,
            currentPhase,
            topicText,
            savedContent
          )
        }
      )
    }
  }
}
