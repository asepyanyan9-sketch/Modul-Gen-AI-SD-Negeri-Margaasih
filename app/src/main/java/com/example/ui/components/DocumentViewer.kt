package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.service.DocumentExportHelper
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoyalBlue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DocumentViewer(
  title: String,
  initialContent: String,
  generatorType: String,
  statusMessage: String?,
  userSettings: UserSettings,
  onSave: (String) -> Unit,
  onPlayGame: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var contentText by remember(initialContent) { mutableStateOf(initialContent) }
  var isEditing by remember { mutableStateOf(false) }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("document_viewer_card"),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Header & Edit Switch
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Hasil Generator Pembelajaran",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = NavyPrimary
          )
          if (!statusMessage.isNullOrBlank()) {
            Text(
              text = statusMessage,
              fontSize = 12.sp,
              color = Color(0xFF64748B),
              lineHeight = 16.sp
            )
          }
        }

        Row {
          IconButton(
            onClick = { isEditing = !isEditing },
            modifier = Modifier.testTag("btn_toggle_edit")
          ) {
            Icon(
              imageVector = Icons.Default.Edit,
              contentDescription = "Edit Konten",
              tint = if (isEditing) RoyalBlue else Color(0xFF64748B)
            )
          }
          IconButton(
            onClick = {
              val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
              val clip = ClipData.newPlainText("Dokumen EduGen", contentText)
              clipboard.setPrimaryClip(clip)
              Toast.makeText(context, "Konten disalin ke papan klip!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.testTag("btn_copy_content")
          ) {
            Icon(
              imageVector = Icons.Default.ContentCopy,
              contentDescription = "Salin Teks",
              tint = Color(0xFF64748B)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Content Box
      Surface(
        color = Color(0xFFF8FAFC),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(380.dp)
      ) {
        if (isEditing) {
          OutlinedTextField(
            value = contentText,
            onValueChange = { contentText = it },
            modifier = Modifier
              .padding(8.dp)
              .testTag("input_edit_content"),
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            placeholder = { Text("Tulis atau ubah dokumen...") }
          )
        } else {
          SelectionContainer {
            Text(
              text = contentText,
              modifier = Modifier
                .padding(14.dp)
                .verticalScroll(rememberScrollState()),
              fontSize = 13.sp,
              lineHeight = 20.sp,
              color = Color(0xFF1E293B)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Action Buttons Row
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        if (onPlayGame != null) {
          Button(
            onClick = onPlayGame,
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
            modifier = Modifier.testTag("btn_play_game")
          ) {
            Icon(Icons.Default.SportsEsports, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Mainkan Game!", fontWeight = FontWeight.Bold)
          }
        }

        Button(
          onClick = { onSave(contentText) },
          colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
          modifier = Modifier.testTag("btn_save_project")
        ) {
          Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Simpan ke Library")
        }

        OutlinedButton(
          onClick = {
            DocumentExportHelper.shareAsPlainText(context, title, contentText)
          },
          modifier = Modifier.testTag("btn_share_project")
        ) {
          Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Bagikan")
        }

        OutlinedButton(
          onClick = {
            val html = DocumentExportHelper.exportToPrintableHtml(title, contentText, userSettings)
            DocumentExportHelper.shareAsPlainText(context, "Cetak - $title.html", html)
            Toast.makeText(context, "Format cetak HTML siap dibagikan/dibuka!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier.testTag("btn_print_html")
        ) {
          Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Format Cetak")
        }
      }
    }
  }
}
