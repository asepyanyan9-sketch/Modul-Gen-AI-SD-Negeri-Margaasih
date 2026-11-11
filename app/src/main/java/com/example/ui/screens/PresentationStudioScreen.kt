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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Slideshow
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PresentationStudioScreen(
  userSettings: UserSettings,
  isGenerating: Boolean,
  outputContent: String?,
  statusMessage: String?,
  onGenerate: (subject: String, grade: String, topic: String, slideCount: Int) -> Unit,
  onSave: (title: String, type: String, subject: String, grade: String, phase: String, topic: String, content: String) -> Unit
) {
  var selectedSubject by remember { mutableStateOf(CurriculumKnowledgeBase.SUBJECTS[0]) }
  var selectedGrade by remember { mutableStateOf("Kelas 4") }
  val currentPhase = CurriculumKnowledgeBase.getPhaseForGrade(selectedGrade)
  var topicText by remember { mutableStateOf("Bagian Tubuh Tumbuhan dan Fungsinya") }
  var slideCount by remember { mutableIntStateOf(5) }

  var isSubjectDropdownOpen by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
      .testTag("presentation_studio_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header Banner
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFD97706))
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
          Icon(Icons.Default.Slideshow, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text("Presentation Studio AI", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
          Text("Generator Slide Presentasi Interaktif dengan Catatan Presenter Guru", fontSize = 12.sp, color = Color(0xFFFEF3C7))
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
        Text("Konfigurasi Slide Pembelajaran", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFD97706))

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
        Text("Tingkat Kelas:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
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
                selectedContainerColor = Color(0xFFD97706),
                selectedLabelColor = Color.White
              )
            )
          }
        }

        // Topik
        OutlinedTextField(
          value = topicText,
          onValueChange = { topicText = it },
          label = { Text("Topik Slide Presentasi") },
          modifier = Modifier.fillMaxWidth()
        )

        // Jumlah Slide
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("Target Slide:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
          Spacer(modifier = Modifier.width(12.dp))
          listOf(3, 5, 8).forEach { cnt ->
            FilterChip(
              selected = slideCount == cnt,
              onClick = { slideCount = cnt },
              label = { Text("$cnt Slide", fontSize = 11.sp) },
              modifier = Modifier.padding(horizontal = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Generate Button
        Button(
          onClick = {
            onGenerate(selectedSubject, selectedGrade, topicText, slideCount)
          },
          enabled = !isGenerating && topicText.isNotBlank(),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("btn_generate_presentation"),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
          shape = RoundedCornerShape(8.dp)
        ) {
          if (isGenerating) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Text("AI Sedang Menyusun Slide...")
          } else {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Generate Slide Presentasi", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Results Section
    if (!outputContent.isNullOrBlank()) {
      DocumentViewer(
        title = "Presentasi $selectedSubject - $selectedGrade ($topicText)",
        initialContent = outputContent,
        generatorType = GeneratorTypes.PRESENTATION_STUDIO,
        statusMessage = statusMessage,
        userSettings = userSettings,
        onSave = { savedContent ->
          onSave(
            "Slide Presentasi $selectedSubject $selectedGrade — $topicText",
            GeneratorTypes.PRESENTATION_STUDIO,
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
