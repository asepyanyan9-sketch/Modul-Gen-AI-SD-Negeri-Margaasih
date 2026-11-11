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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.data.model.GeneratorTypes
import com.example.data.model.TemplateEntity
import com.example.data.model.UserSettings
import com.example.ui.components.DocumentViewer
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoyalBlue

@Composable
fun TemplatesScreen(
  templates: List<TemplateEntity>,
  userSettings: UserSettings,
  onUseTemplateAsProject: (TemplateEntity) -> Unit
) {
  val context = LocalContext.current
  var activeTemplatePreview by remember { mutableStateOf<TemplateEntity?>(null) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(16.dp)
      .testTag("templates_screen"),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header Banner
    item {
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
            Icon(Icons.Default.Bookmark, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text("Templates Kurikulum Merdeka", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            Text("Format Terstandar Khusus SD Negeri Margaasih", fontSize = 12.sp, color = Color(0xFFDBEAFE))
          }
        }
      }
    }

    if (activeTemplatePreview != null) {
      item {
        val tmpl = activeTemplatePreview!!
        DocumentViewer(
          title = tmpl.title,
          initialContent = tmpl.contentJson,
          generatorType = tmpl.generatorType,
          statusMessage = "Pratinjau Template Standar SD Negeri Margaasih",
          userSettings = userSettings,
          onSave = {
            onUseTemplateAsProject(tmpl)
            Toast.makeText(context, "Template disalin ke My Library!", Toast.LENGTH_SHORT).show()
          }
        )
      }
    }

    items(templates) { template ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("template_item_${template.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .background(Color(0xFFEFF6FF), RoundedCornerShape(8.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = when (template.generatorType) {
                  GeneratorTypes.LEARNING_DESIGNER -> Icons.Default.AutoStories
                  GeneratorTypes.WORKSHEET_STUDIO -> Icons.Default.Assignment
                  GeneratorTypes.ASSESSMENT_STUDIO -> Icons.Default.FactCheck
                  else -> Icons.Default.Folder
                },
                contentDescription = null,
                tint = RoyalBlue,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = template.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF0F172A)
              )
              Text(
                text = "${template.subject} • ${template.grade} (${template.phase})",
                fontSize = 11.sp,
                color = Color(0xFF64748B)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(text = template.description, fontSize = 12.sp, color = Color(0xFF475569))
          Spacer(modifier = Modifier.height(12.dp))

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
              onClick = { activeTemplatePreview = template },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("Lihat Pratinjau", color = Color(0xFF334155), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }

            Button(
              onClick = {
                onUseTemplateAsProject(template)
                Toast.makeText(context, "Template berhasil disimpan ke My Library Anda!", Toast.LENGTH_SHORT).show()
              },
              colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Gunakan Template", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}
