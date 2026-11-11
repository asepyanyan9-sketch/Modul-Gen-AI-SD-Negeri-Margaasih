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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SportsEsports
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
import com.example.service.generators.GameData
import com.example.ui.components.DocumentViewer
import com.example.ui.theme.EmeraldSuccess

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GameStudioScreen(
  userSettings: UserSettings,
  isGenerating: Boolean,
  outputContent: String?,
  activeGameData: GameData?,
  statusMessage: String?,
  onGenerate: (subject: String, grade: String, topic: String, gameType: String) -> Unit,
  onPlayGame: () -> Unit,
  onSave: (title: String, type: String, subject: String, grade: String, phase: String, topic: String, content: String) -> Unit
) {
  var selectedSubject by remember { mutableStateOf(CurriculumKnowledgeBase.SUBJECTS[0]) }
  var selectedGrade by remember { mutableStateOf("Kelas 4") }
  val currentPhase = CurriculumKnowledgeBase.getPhaseForGrade(selectedGrade)
  var topicText by remember { mutableStateOf("Bagian Tubuh Tumbuhan dan Fungsinya") }
  var selectedGameType by remember { mutableStateOf("QUIZ") }

  val gameTypes = listOf(
    Pair("QUIZ", "Quiz Challenge Interaktif"),
    Pair("MATCHING", "Matching Pairs (Menjodohkan)"),
    Pair("TRUE_FALSE", "True or False Sprint"),
    Pair("MATH_ADVENTURE", "Petualangan Matematika SD")
  )

  var isSubjectDropdownOpen by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
      .testTag("game_studio_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header Banner
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFE11D48))
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
          Icon(Icons.Default.SportsEsports, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text("Game Studio AI (Interaktif)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
          Text("Permainan Edukatif yang Benar-Benar Dapat Dimainkan Langsung!", fontSize = 12.sp, color = Color(0xFFFFE4E6))
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
        Text("Konfigurasi Game Edukasi", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFE11D48))

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
                selectedContainerColor = Color(0xFFE11D48),
                selectedLabelColor = Color.White
              )
            )
          }
        }

        // Topik
        OutlinedTextField(
          value = topicText,
          onValueChange = { topicText = it },
          label = { Text("Materi / Topik Permainan") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_game_topic")
        )

        // Tipe Game
        Text("Jenis Permainan Interaktif:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF475569))
        gameTypes.forEach { (typeKey, typeLabel) ->
          FilterChip(
            selected = selectedGameType == typeKey,
            onClick = { selectedGameType = typeKey },
            label = { Text(typeLabel, fontSize = 12.sp) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = Color(0xFFFFE4E6),
              selectedLabelColor = Color(0xFFE11D48)
            ),
            modifier = Modifier.fillMaxWidth()
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Generate Button
        Button(
          onClick = {
            onGenerate(selectedSubject, selectedGrade, topicText, selectedGameType)
          },
          enabled = !isGenerating && topicText.isNotBlank(),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("btn_generate_game"),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48)),
          shape = RoundedCornerShape(8.dp)
        ) {
          if (isGenerating) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Text("AI Sedang Merancang Game...")
          } else {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Generate Game Edukasi", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Play Card Highlight
    if (activeGameData != null) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
        border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldSuccess)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.SportsEsports, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = activeGameData.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF065F46)
              )
              Text(
                text = "${activeGameData.subject} • ${activeGameData.grade}",
                fontSize = 12.sp,
                color = Color(0xFF047857)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = activeGameData.instructions, fontSize = 12.sp, color = Color(0xFF065F46))
          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = onPlayGame,
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("btn_launch_gameplay")
          ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Mainkan Sekarang (Interactive Game)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }
        }
      }
    }

    // Results Section
    if (!outputContent.isNullOrBlank()) {
      DocumentViewer(
        title = "Game Edukasi $selectedSubject - $selectedGrade ($topicText)",
        initialContent = outputContent,
        generatorType = GeneratorTypes.GAME_STUDIO,
        statusMessage = statusMessage,
        userSettings = userSettings,
        onPlayGame = if (activeGameData != null) onPlayGame else null,
        onSave = { savedContent ->
          onSave(
            "Game Edukasi $selectedSubject $selectedGrade — $topicText",
            GeneratorTypes.GAME_STUDIO,
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
