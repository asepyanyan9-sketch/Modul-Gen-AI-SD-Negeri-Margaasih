package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GeneratorTypes
import com.example.data.model.ProjectEntity
import com.example.data.model.UserSettings
import com.example.ui.navigation.Screen
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.SoftIndigo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
  userSettings: UserSettings,
  projects: List<ProjectEntity>,
  onNavigate: (Screen) -> Unit,
  onOpenProject: (ProjectEntity) -> Unit
) {
  val totalProjects = projects.size
  val countModul = projects.count { it.generatorType == GeneratorTypes.LEARNING_DESIGNER }
  val countLkpd = projects.count { it.generatorType == GeneratorTypes.WORKSHEET_STUDIO }
  val countAsesmen = projects.count { it.generatorType == GeneratorTypes.ASSESSMENT_STUDIO }
  val countGame = projects.count { it.generatorType == GeneratorTypes.GAME_STUDIO }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .testTag("dashboard_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Welcome Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavyPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Selamat Datang,",
                color = Color(0xFF93C5FD),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = userSettings.teacherName,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "${userSettings.schoolName} • ${userSettings.academicYear}",
                color = Color(0xFFE2E8F0),
                fontSize = 12.sp
              )
            }
            Box(
              modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color(0x33FFFFFF)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
              onClick = { onNavigate(Screen.LearningDesigner) },
              colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("btn_create_new_modul")
            ) {
              Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Buat Modul Ajar", fontSize = 13.sp)
            }

            Button(
              onClick = { onNavigate(Screen.GameStudio) },
              colors = ButtonDefaults.buttonColors(containerColor = SoftIndigo),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("btn_create_new_game")
            ) {
              Icon(Icons.Default.SportsEsports, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Buat Game", fontSize = 13.sp)
            }
          }
        }
      }
    }

    // 2. Statistics Grid
    item {
      Text(
        text = "STATISTIK PEMBELAJARAN",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF64748B),
        letterSpacing = 1.sp
      )
      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        StatCard(
          title = "Total Proyek",
          count = totalProjects.toString(),
          icon = Icons.Default.Folder,
          color = NavyPrimary,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = "Modul Ajar",
          count = countModul.toString(),
          icon = Icons.Default.AutoStories,
          color = RoyalBlue,
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        StatCard(
          title = "LKPD Siswa",
          count = countLkpd.toString(),
          icon = Icons.Default.Assignment,
          color = SoftIndigo,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = "Asesmen",
          count = countAsesmen.toString(),
          icon = Icons.Default.FactCheck,
          color = EmeraldSuccess,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = "Game Edukasi",
          count = countGame.toString(),
          icon = Icons.Default.SportsEsports,
          color = Color(0xFFE11D48),
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. Quick Access Studios
    item {
      Text(
        text = "QUICK ACCESS STUDIO",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF64748B),
        letterSpacing = 1.sp
      )
      Spacer(modifier = Modifier.height(8.dp))

      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          QuickAccessTile(
            title = "Learning Designer",
            subtitle = "Modul Ajar & RPP",
            icon = Icons.Default.AutoStories,
            color = NavyPrimary,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Screen.LearningDesigner) }
          )
          QuickAccessTile(
            title = "Worksheet Studio",
            subtitle = "LKPD Interaktif",
            icon = Icons.Default.Assignment,
            color = RoyalBlue,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Screen.WorksheetStudio) }
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          QuickAccessTile(
            title = "Assessment Studio",
            subtitle = "Kisi-kisi & Soal",
            icon = Icons.Default.FactCheck,
            color = SoftIndigo,
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Screen.AssessmentStudio) }
          )
          QuickAccessTile(
            title = "Game Studio",
            subtitle = "Mini-Game Interaktif",
            icon = Icons.Default.SportsEsports,
            color = Color(0xFFE11D48),
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Screen.GameStudio) }
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          QuickAccessTile(
            title = "Visual Studio",
            subtitle = "Poster & Flashcard",
            icon = Icons.Default.Palette,
            color = Color(0xFF0D9488),
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Screen.VisualStudio) }
          )
          QuickAccessTile(
            title = "Presentation Studio",
            subtitle = "Slide Deck Edukasi",
            icon = Icons.Default.Slideshow,
            color = Color(0xFFD97706),
            modifier = Modifier.weight(1f),
            onClick = { onNavigate(Screen.PresentationStudio) }
          )
        }
      }
    }

    // 4. Recent Projects Section
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "PROYEK TERBARU",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF64748B),
          letterSpacing = 1.sp
        )
        Text(
          text = "Lihat Semua (${projects.size})",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = RoyalBlue,
          modifier = Modifier
            .clickable { onNavigate(Screen.MyLibrary) }
            .testTag("btn_see_all_projects")
        )
      }
    }

    if (projects.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          shape = RoundedCornerShape(12.dp)
        ) {
          Column(
            modifier = Modifier
              .padding(24.dp)
              .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.AutoStories,
              contentDescription = null,
              tint = Color(0xFFCBD5E1),
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Belum Ada Proyek Pembelajaran",
              fontWeight = FontWeight.Bold,
              color = Color(0xFF334155),
              fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Pilih salah satu studio di atas untuk membuat Modul Ajar, LKPD, Asesmen, atau Game pertama Anda!",
              fontSize = 12.sp,
              color = Color(0xFF64748B),
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
          }
        }
      }
    } else {
      items(projects.take(4)) { project ->
        ProjectListItem(
          project = project,
          onClick = { onOpenProject(project) }
        )
      }
    }
  }
}

@Composable
fun StatCard(
  title: String,
  count: String,
  icon: ImageVector,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(color.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(text = count, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = NavyDark)
        Text(text = title, fontSize = 11.sp, color = Color(0xFF64748B), maxLines = 1)
      }
    }
  }
}

@Composable
fun QuickAccessTile(
  title: String,
  subtitle: String,
  icon: ImageVector,
  color: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    modifier = modifier
      .clickable(onClick = onClick)
      .testTag("quick_tile_$title"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(color.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF1E293B))
        Text(text = subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
      }
    }
  }
}

@Composable
fun ProjectListItem(
  project: ProjectEntity,
  onClick: () -> Unit
) {
  val dateStr = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID")).format(Date(project.updatedAt))

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("project_item_${project.id}"),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
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
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
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
        Text(
          text = dateStr,
          fontSize = 10.sp,
          color = Color(0xFF94A3B8)
        )
      }

      Surface(
        color = Color(0xFFECFDF5),
        shape = RoundedCornerShape(6.dp)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(12.dp))
          Spacer(modifier = Modifier.width(3.dp))
          Text(text = project.status, color = EmeraldSuccess, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
