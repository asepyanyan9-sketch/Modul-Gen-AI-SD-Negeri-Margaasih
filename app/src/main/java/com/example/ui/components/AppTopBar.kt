package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NavyPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
  title: String,
  onOpenDrawer: () -> Unit,
  onOpenLibrary: () -> Unit
) {
  TopAppBar(
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = title,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
          modifier = Modifier
            .background(Color(0xFFEFF6FF), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
          Text(
            text = "SDN Margaasih",
            color = NavyPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    },
    navigationIcon = {
      IconButton(
        onClick = onOpenDrawer,
        modifier = Modifier.testTag("btn_menu_drawer")
      ) {
        Icon(
          imageVector = Icons.Default.Menu,
          contentDescription = "Buka Menu",
          tint = Color.White
        )
      }
    },
    actions = {
      IconButton(
        onClick = onOpenLibrary,
        modifier = Modifier.testTag("btn_quick_library")
      ) {
        Icon(
          imageVector = Icons.Default.Folder,
          contentDescription = "Buka My Library",
          tint = Color.White
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = NavyPrimary,
      titleContentColor = Color.White,
      actionIconContentColor = Color.White
    )
  )
}
