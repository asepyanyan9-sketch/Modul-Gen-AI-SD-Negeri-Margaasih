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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
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
import com.example.ui.theme.SoftIndigo

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LearningDesignerScreen(
  userSettings: UserSettings,
  isGenerating: Boolean,
  outputContent: String?,
  statusMessage: String?,
  onGenerate: (subject: String, grade: String, phase: String, topic: String, time: String, model: String, deepPillar: String) -> Unit,
  onSave: (title: String, type: String, subject: String, grade: String, phase: String, topic: String, content: String) -> Unit
) {
  var selectedSubject by remember { mutableStateOf(CurriculumKnowledgeBase.SUBJECTS[0]) }
  var selectedGrade by remember { mutableStateOf("Kelas 4") }
  val currentPhase = CurriculumKnowledgeBase.getPhaseForGrade(selectedGrade)
  var topicText by remember { mutableStateOf("Bagian Tubuh Tumbuhan dan Fungsinya") }
  var timeAllocation by remember { mutableStateOf("2 x 35 Menit (1 Pertemuan)") }
  var selectedModel by remember { mutableStateOf(CurriculumKnowledgeBase.MODELS[0]) }
  var selectedPillar by remember { mutableStateOf(CurriculumKnowledgeBase.DEEP_LEARNING_PILLARS[0]) }

  var isSubjectDropdownOpen by remember { mutableStateOf(false) }
  var isModelDropdownOpen by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
      .testTag("learning_designer_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Studio Header Banner
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
          Icon(Icons.Default.AutoStories, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text("Learning Designer AI", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
          Text("Generator Modul Ajar, RPP, & Pembelajaran Mendalam Kurikulum Merdeka", fontSize = 12.sp, color = Color(0xFFDBEAFE))
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
        Text("Konfigurasi Modul Ajar SD", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyPrimary)

        // Mata Pelajaran Dropdown
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
              .testTag("dropdown_subject")
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
                  val sample = CurriculumKnowledgeBase.SAMPLE_TOPICS_BY_SUBJECT[subject]?.firstOrNull()
                  if (!sample.isNullOrBlank()) topicText = sample
                  isSubjectDropdownOpen = false
                }
              )
            }
          }
        }

        // Kelas & Fase Chips
        Text("Pilih Kelas (${currentPhase}):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
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
              ),
              modifier = Modifier.testTag("chip_grade_$grade")
            )
          }
        }

        // Topik Pembelajaran
        OutlinedTextField(
          value = topicText,
          onValueChange = { topicText = it },
          label = { Text("Topik / Materi / Bab") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_topic"),
          placeholder = { Text("Contoh: Wujud Zat dan Perubahannya") }
        )

        // Suggestion Chips for Topic
        val suggestions = CurriculumKnowledgeBase.SAMPLE_TOPICS_BY_SUBJECT[selectedSubject] ?: emptyList()
        if (suggestions.isNotEmpty()) {
          Text("Topik Populer SD Negeri Margaasih:", fontSize = 11.sp, color = Color(0xFF64748B))
          FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            suggestions.take(3).forEach { s ->
              Surface(
                color = Color(0xFFF1F5F9),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.padding(2.dp)
              ) {
                Text(
                  text = s,
                  fontSize = 10.sp,
                  color = RoyalBlue,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 3.dp)
                )
              }
            }
          }
        }

        // Alokasi Waktu & Model Pembelajaran
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedTextField(
            value = timeAllocation,
            onValueChange = { timeAllocation = it },
            label = { Text("Alokasi Waktu") },
            modifier = Modifier.weight(1f)
          )

          ExposedDropdownMenuBox(
            expanded = isModelDropdownOpen,
            onExpandedChange = { isModelDropdownOpen = it },
            modifier = Modifier.weight(1f)
          ) {
            OutlinedTextField(
              value = selectedModel.split(" ").firstOrNull() ?: selectedModel,
              onValueChange = {},
              readOnly = true,
              label = { Text("Model Belajar") },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isModelDropdownOpen) },
              modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
              expanded = isModelDropdownOpen,
              onDismissRequest = { isModelDropdownOpen = false }
            ) {
              CurriculumKnowledgeBase.MODELS.forEach { model ->
                DropdownMenuItem(
                  text = { Text(model, fontSize = 12.sp) },
                  onClick = {
                    selectedModel = model
                    isModelDropdownOpen = false
                  }
                )
              }
            }
          }
        }

        // Pendekatan Pembelajaran Mendalam (Deep Learning)
        Text("Pilar Pembelajaran Mendalam (Deep Learning):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
        CurriculumKnowledgeBase.DEEP_LEARNING_PILLARS.forEach { pillar ->
          FilterChip(
            selected = selectedPillar == pillar,
            onClick = { selectedPillar = pillar },
            label = { Text(pillar, fontSize = 11.sp) },
            leadingIcon = {
              Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(14.dp))
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SoftIndigo,
              selectedLabelColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Generate Button
        Button(
          onClick = {
            onGenerate(
              selectedSubject,
              selectedGrade,
              currentPhase,
              topicText,
              timeAllocation,
              selectedModel,
              selectedPillar
            )
          },
          enabled = !isGenerating && topicText.isNotBlank(),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("btn_generate_modul_ajar"),
          colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
          shape = RoundedCornerShape(8.dp)
        ) {
          if (isGenerating) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Text("AI Sedang Merancang Modul...", fontSize = 14.sp)
          } else {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Generate Modul Ajar Sekarang", fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }
        }
      }
    }

    // Results Section
    if (!outputContent.isNullOrBlank()) {
      DocumentViewer(
        title = "Modul Ajar $selectedSubject - $selectedGrade ($topicText)",
        initialContent = outputContent,
        generatorType = GeneratorTypes.LEARNING_DESIGNER,
        statusMessage = statusMessage,
        userSettings = userSettings,
        onSave = { savedContent ->
          onSave(
            "Modul Ajar $selectedSubject $selectedGrade — $topicText",
            GeneratorTypes.LEARNING_DESIGNER,
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
