package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GeneratorTypes
import com.example.data.model.ProjectEntity
import com.example.data.model.UserSettings
import com.example.service.DocumentExportHelper
import com.example.service.generators.GameGenerator
import com.example.ui.components.DocumentViewer
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoseDanger
import com.example.ui.theme.RoyalBlue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LibraryScreen(
  projects: List<ProjectEntity>,
  userSettings: UserSettings,
  onOpenProject: (ProjectEntity) -> Unit,
  onUpdateProject: (ProjectEntity, newContent: String, newTitle: String) -> Unit,
  onDuplicateProject: (ProjectEntity) -> Unit,
  onDeleteProject: (String) -> Unit,
  onExportJson: () -> String,
  onImportJson: (String) -> Pair<Boolean, String>,
  onPlayGame: ((com.example.service.generators.GameData) -> Unit)? = null
) {
  val context = LocalContext.current
  var searchQuery by remember { mutableStateOf("") }
  var selectedFilterType by remember { mutableStateOf<String?>(null) }

  var activeViewingProject by remember { mutableStateOf<ProjectEntity?>(null) }
  var projectToDelete by remember { mutableStateOf<ProjectEntity?>(null) }
  var showImportDialog by remember { mutableStateOf(false) }
  var importJsonInput by remember { mutableStateOf("") }

  val filterCategories = listOf(
    Pair(null, "Semua"),
    Pair(GeneratorTypes.LEARNING_DESIGNER, "Modul Ajar"),
    Pair(GeneratorTypes.WORKSHEET_STUDIO, "LKPD"),
    Pair(GeneratorTypes.ASSESSMENT_STUDIO, "Asesmen"),
    Pair(GeneratorTypes.GAME_STUDIO, "Game Edukasi"),
    Pair(GeneratorTypes.VISUAL_STUDIO, "Media Visual"),
    Pair(GeneratorTypes.PRESENTATION_STUDIO, "Slide")
  )

  val filteredProjects = projects.filter { project ->
    val matchesSearch = searchQuery.isBlank() ||
        project.title.contains(searchQuery, ignoreCase = true) ||
        project.subject.contains(searchQuery, ignoreCase = true) ||
        project.topic.contains(searchQuery, ignoreCase = true)
    val matchesFilter = selectedFilterType == null || project.generatorType == selectedFilterType
    matchesSearch && matchesFilter
  }

  // Delete Confirmation Dialog
  if (projectToDelete != null) {
    val target = projectToDelete!!
    AlertDialog(
      onDismissRequest = { projectToDelete = null },
      title = { Text("Konfirmasi Hapus Proyek", fontWeight = FontWeight.Bold) },
      text = { Text("Apakah Anda yakin ingin menghapus '${target.title}'? Tindakan ini tidak dapat dibatalkan.") },
      confirmButton = {
        Button(
          onClick = {
            onDeleteProject(target.id)
            if (activeViewingProject?.id == target.id) activeViewingProject = null
            projectToDelete = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = RoseDanger)
        ) {
          Text("Ya, Hapus")
        }
      },
      dismissButton = {
        TextButton(onClick = { projectToDelete = null }) {
          Text("Batal")
        }
      }
    )
  }

  // Import JSON Dialog
  if (showImportDialog) {
    AlertDialog(
      onDismissRequest = { showImportDialog = false },
      title = { Text("Impor Proyek dari JSON", fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("Tempelkan (paste) teks JSON cadangan proyek di bawah ini:", fontSize = 12.sp, color = Color(0xFF64748B))
          OutlinedTextField(
            value = importJsonInput,
            onValueChange = { importJsonInput = it },
            modifier = Modifier
              .fillMaxWidth()
              .height(180.dp),
            placeholder = { Text("[{\"title\": \"...\"}]") },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val (success, msg) = onImportJson(importJsonInput)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            if (success) {
              showImportDialog = false
              importJsonInput = ""
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
        ) {
          Text("Impor Data")
        }
      },
      dismissButton = {
        TextButton(onClick = { showImportDialog = false }) {
          Text("Batal")
        }
      }
    )
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(16.dp)
      .testTag("my_library_screen"),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header Banner
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavyPrimary)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(40.dp)
                  .background(Color(0x33FFFFFF), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.Folder, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text("My Library", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                Text("${projects.size} Dokumen Pembelajaran Tersimpan", fontSize = 12.sp, color = Color(0xFFDBEAFE))
              }
            }

            Row {
              IconButton(
                onClick = {
                  val json = onExportJson()
                  DocumentExportHelper.shareAsPlainText(context, "Backup_Proyek_EduGen.json", json)
                },
                modifier = Modifier.testTag("btn_export_all_json")
              ) {
                Icon(Icons.Default.Download, contentDescription = "Ekspor JSON", tint = Color.White)
              }
              IconButton(
                onClick = { showImportDialog = true },
                modifier = Modifier.testTag("btn_import_json")
              ) {
                Icon(Icons.Default.Upload, contentDescription = "Impor JSON", tint = Color.White)
              }
            }
          }
        }
      }
    }

    // Search Bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Cari judul modul, LKPD, mata pelajaran...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF64748B)) },
        trailingIcon = {
          if (searchQuery.isNotBlank()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(Icons.Default.Clear, contentDescription = "Bersihkan")
            }
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("search_library_input"),
        shape = RoundedCornerShape(10.dp)
      )
    }

    // Filter Chips
    item {
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        filterCategories.forEach { (catType, catLabel) ->
          FilterChip(
            selected = selectedFilterType == catType,
            onClick = { selectedFilterType = catType },
            label = { Text(catLabel, fontSize = 11.sp) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = RoyalBlue,
              selectedLabelColor = Color.White
            )
          )
        }
      }
    }

    // Active Project Viewer Modal Card
    if (activeViewingProject != null) {
      item {
        val proj = activeViewingProject!!
        DocumentViewer(
          title = proj.title,
          initialContent = proj.contentJson,
          generatorType = proj.generatorType,
          statusMessage = "Tersimpan di My Library • Terakhir diubah: ${SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID")).format(Date(proj.updatedAt))}",
          userSettings = userSettings,
          onSave = { updatedText ->
            onUpdateProject(proj, updatedText, proj.title)
            activeViewingProject = proj.copy(contentJson = updatedText, updatedAt = System.currentTimeMillis())
            Toast.makeText(context, "Perubahan berhasil disimpan ke database!", Toast.LENGTH_SHORT).show()
          },
          onPlayGame = if (proj.generatorType == GeneratorTypes.GAME_STUDIO && onPlayGame != null) {
            { onPlayGame(GameGenerator.fromJson(proj.contentJson)) }
          } else null
        )
      }
    }

    // Projects List
    if (filteredProjects.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          shape = RoundedCornerShape(12.dp)
        ) {
          Column(
            modifier = Modifier
              .padding(30.dp)
              .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "Tidak Ada Proyek yang Cocok",
              fontWeight = FontWeight.Bold,
              color = Color(0xFF475569),
              fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Coba ubah kata kunci pencarian atau buat proyek baru melalui Studio.",
              fontSize = 12.sp,
              color = Color(0xFF64748B)
            )
          }
        }
      }
    } else {
      items(filteredProjects) { project ->
        ProjectCardDetailed(
          project = project,
          onView = { activeViewingProject = project },
          onDuplicate = { onDuplicateProject(project) },
          onDelete = { projectToDelete = project }
        )
      }
    }
  }
}

@Composable
fun ProjectCardDetailed(
  project: ProjectEntity,
  onView: () -> Unit,
  onDuplicate: () -> Unit,
  onDelete: () -> Unit
) {
  val dateStr = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID")).format(Date(project.updatedAt))

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onView)
      .testTag("library_item_${project.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFFEFF6FF)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = when (project.generatorType) {
                GeneratorTypes.LEARNING_DESIGNER -> Icons.Default.AutoStories
                GeneratorTypes.WORKSHEET_STUDIO -> Icons.Default.Assignment
                GeneratorTypes.ASSESSMENT_STUDIO -> Icons.Default.FactCheck
                GeneratorTypes.GAME_STUDIO -> Icons.Default.SportsEsports
                GeneratorTypes.VISUAL_STUDIO -> Icons.Default.Palette
                GeneratorTypes.PRESENTATION_STUDIO -> Icons.Default.Slideshow
                else -> Icons.Default.Folder
              },
              contentDescription = null,
              tint = NavyPrimary,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = project.title,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = Color(0xFF0F172A),
              maxLines = 1
            )
            Text(
              text = "${GeneratorTypes.getDisplayName(project.generatorType)} • ${project.subject} (${project.grade})",
              fontSize = 11.sp,
              color = Color(0xFF64748B)
            )
          }
        }

        Surface(
          color = Color(0xFFECFDF5),
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = project.status,
            color = EmeraldSuccess,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "Diperbarui: $dateStr", fontSize = 10.sp, color = Color(0xFF94A3B8))

        Row {
          IconButton(onClick = onDuplicate, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.ContentCopy, contentDescription = "Duplikasi", tint = Color(0xFF64748B), modifier = Modifier.size(16.dp))
          }
          IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = RoseDanger, modifier = Modifier.size(16.dp))
          }
        }
      }
    }
  }
}
