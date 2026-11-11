package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import com.example.data.model.UserSettings
import com.example.ui.navigation.Screen
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoyalBlue

data class NavItem(
  val screen: Screen,
  val icon: ImageVector,
  val label: String
)

val NAV_ITEMS = listOf(
  NavItem(Screen.Dashboard, Icons.Default.Dashboard, "Dashboard"),
  NavItem(Screen.LearningDesigner, Icons.Default.AutoStories, "Learning Designer"),
  NavItem(Screen.WorksheetStudio, Icons.Default.Assignment, "Worksheet Studio"),
  NavItem(Screen.AssessmentStudio, Icons.Default.FactCheck, "Assessment Studio"),
  NavItem(Screen.GameStudio, Icons.Default.SportsEsports, "Game Studio"),
  NavItem(Screen.VisualStudio, Icons.Default.Palette, "Visual Studio"),
  NavItem(Screen.PresentationStudio, Icons.Default.Slideshow, "Presentation Studio"),
  NavItem(Screen.MyLibrary, Icons.Default.Folder, "My Library"),
  NavItem(Screen.Templates, Icons.Default.Bookmark, "Templates"),
  NavItem(Screen.Settings, Icons.Default.Settings, "Settings")
)

@Composable
fun AppDrawerContent(
  currentRoute: String,
  userSettings: UserSettings,
  onNavigate: (Screen) -> Unit,
  onCloseDrawer: () -> Unit
) {
  ModalDrawerSheet(
    modifier = Modifier
      .width(310.dp)
      .testTag("app_drawer"),
    drawerContainerColor = Color.White
  ) {
    Column(
      modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(bottom = 24.dp)
    ) {
      // Header Section
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(NavyPrimary)
          .padding(20.dp)
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(RoyalBlue),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.School,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "EDU GEN AI",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                letterSpacing = 1.sp
              )
              Text(
                text = "Learning Studio SD",
                color = Color(0xFF93C5FD),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Surface(
            color = Color(0x22FFFFFF),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(
                text = userSettings.teacherName,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
              )
              Text(
                text = userSettings.schoolName,
                color = Color(0xFFE2E8F0),
                fontSize = 12.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "STUDIO & NAVIGASI",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF64748B),
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
      )

      NAV_ITEMS.forEach { item ->
        val isSelected = currentRoute == item.screen.route
        NavigationDrawerItem(
          icon = {
            Icon(
              imageVector = item.icon,
              contentDescription = item.label,
              tint = if (isSelected) NavyPrimary else Color(0xFF64748B)
            )
          },
          label = {
            Text(
              text = item.label,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) NavyPrimary else Color(0xFF1E293B)
            )
          },
          selected = isSelected,
          onClick = {
            onCloseDrawer()
            onNavigate(item.screen)
          },
          modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .testTag("nav_item_${item.screen.route}"),
          colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color(0xFFEFF6FF),
            unselectedContainerColor = Color.Transparent
          ),
          shape = RoundedCornerShape(10.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))
      HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = DividerDefaults.color
      )
      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Kurikulum Merdeka 2024 • Kab. Bandung",
        fontSize = 11.sp,
        color = Color(0xFF94A3B8),
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
      )
    }
  }
}
