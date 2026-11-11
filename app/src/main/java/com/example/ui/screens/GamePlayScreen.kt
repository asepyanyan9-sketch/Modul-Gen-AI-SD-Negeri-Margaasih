package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.service.generators.GameData
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoseDanger
import com.example.ui.theme.RoyalBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePlayScreen(
  gameData: GameData,
  onBack: () -> Unit
) {
  var currentQuestionIndex by remember { mutableIntStateOf(0) }
  var score by remember { mutableIntStateOf(0) }
  var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
  var isAnswerSubmitted by remember { mutableStateOf(false) }
  var isGameFinished by remember { mutableStateOf(false) }

  // Matching game state
  var selectedLeftIndex by remember { mutableStateOf<Int?>(null) }
  val matchedPairs = remember { mutableStateListOf<Int>() }

  val questions = gameData.questions
  val isMatchingMode = gameData.gameType == "MATCHING" && gameData.matchingItems.isNotEmpty()

  fun resetGame() {
    currentQuestionIndex = 0
    score = 0
    selectedOptionIndex = null
    isAnswerSubmitted = false
    isGameFinished = false
    selectedLeftIndex = null
    matchedPairs.clear()
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(gameData.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 1)
            Text("${gameData.subject} • ${gameData.grade}", fontSize = 11.sp, color = Color(0xFFDBEAFE))
          }
        },
        navigationIcon = {
          IconButton(onClick = onBack, modifier = Modifier.testTag("btn_back_gameplay")) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Color.White)
          }
        },
        actions = {
          Box(
            modifier = Modifier
              .padding(end = 12.dp)
              .background(Color(0x33FFFFFF), RoundedCornerShape(16.dp))
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFDE047), modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Skor: $score", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = NavyPrimary, titleContentColor = Color.White)
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(Color(0xFFF8FAFC))
        .padding(16.dp)
    ) {
      if (isGameFinished) {
        // Celebration Screen
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
          Column(
            modifier = Modifier
              .padding(24.dp)
              .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color(0xFFFEF08A)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFCA8A04), modifier = Modifier.size(44.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Luar Biasa, Siswa Hebat!", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = NavyDark)
            Text("SD Negeri Margaasih", fontSize = 14.sp, color = RoyalBlue, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Kamu telah menyelesaikan tantangan game ini dengan skor akhir:",
              fontSize = 13.sp,
              color = Color(0xFF64748B),
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))
            Surface(
              color = Color(0xFFEFF6FF),
              shape = RoundedCornerShape(12.dp)
            ) {
              Text(
                text = "$score Poin",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = NavyPrimary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
              )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
              Button(
                onClick = { resetGame() },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("btn_play_again")
              ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Main Lagi")
              }

              Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Selesai")
              }
            }
          }
        }
      } else if (isMatchingMode) {
        // Matching Game Screen
        val items = gameData.matchingItems
        Column(modifier = Modifier.fillMaxSize()) {
          Text(
            text = "Pasangkan Konsep dengan Penjelasan yang Tepat:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = NavyPrimary
          )
          Spacer(modifier = Modifier.height(12.dp))

          items.forEachIndexed { index, item ->
            val isMatched = matchedPairs.contains(index)
            val isSelected = selectedLeftIndex == index

            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(enabled = !isMatched) {
                  if (selectedLeftIndex == null) {
                    selectedLeftIndex = index
                  } else if (selectedLeftIndex == index) {
                    selectedLeftIndex = null
                  } else {
                    // Try to match or select another
                    selectedLeftIndex = index
                  }
                },
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(
                containerColor = when {
                  isMatched -> Color(0xFFECFDF5)
                  isSelected -> Color(0xFFEFF6FF)
                  else -> Color.White
                }
              ),
              border = androidx.compose.foundation.BorderStroke(
                width = if (isSelected || isMatched) 2.dp else 1.dp,
                color = when {
                  isMatched -> EmeraldSuccess
                  isSelected -> RoyalBlue
                  else -> Color(0xFFE2E8F0)
                }
              )
            ) {
              Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${index + 1}. ${item.left}",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = if (isMatched) EmeraldSuccess else NavyPrimary,
                  modifier = Modifier.weight(1f)
                )

                if (isMatched) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSuccess)
                } else if (isSelected) {
                  Button(
                    onClick = {
                      matchedPairs.add(index)
                      score += 25
                      selectedLeftIndex = null
                      if (matchedPairs.size == items.size) {
                        isGameFinished = true
                      }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                    shape = RoundedCornerShape(6.dp)
                  ) {
                    Text("Cocokkan!", fontSize = 11.sp)
                  }
                }
              }
              if (isSelected || isMatched) {
                Text(
                  text = "👉 ${item.right}",
                  fontSize = 12.sp,
                  color = Color(0xFF475569),
                  modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 10.dp)
                )
              }
            }
          }
        }
      } else if (questions.isNotEmpty()) {
        // Quiz / True False Question Mode
        val currentQ = questions[currentQuestionIndex]
        val progress = (currentQuestionIndex + 1).toFloat() / questions.size.toFloat()

        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // Progress Bar
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Tantangan ${currentQuestionIndex + 1} dari ${questions.size}",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF64748B)
            )
            Text(
              text = "${(progress * 100).toInt()}%",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = RoyalBlue
            )
          }

          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = RoyalBlue,
            trackColor = Color(0xFFE2E8F0)
          )

          // Question Card
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Text(
                text = currentQ.question,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = NavyDark,
                lineHeight = 24.sp
              )
            }
          }

          // Options List
          currentQ.options.forEachIndexed { optIndex, optionText ->
            val isSelected = selectedOptionIndex == optIndex
            val isCorrect = optIndex == currentQ.correctIndex

            val cardColor = when {
              !isAnswerSubmitted -> if (isSelected) Color(0xFFEFF6FF) else Color.White
              isCorrect -> Color(0xFFECFDF5)
              isSelected -> Color(0xFFFEF2F2)
              else -> Color.White
            }

            val borderColor = when {
              !isAnswerSubmitted -> if (isSelected) RoyalBlue else Color(0xFFE2E8F0)
              isCorrect -> EmeraldSuccess
              isSelected -> RoseDanger
              else -> Color(0xFFE2E8F0)
            }

            Card(
              modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !isAnswerSubmitted) {
                  selectedOptionIndex = optIndex
                  isAnswerSubmitted = true
                  if (optIndex == currentQ.correctIndex) {
                    score += 100 / questions.size
                  }
                }
                .testTag("option_$optIndex"),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = cardColor),
              border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor)
            ) {
              Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(borderColor.copy(alpha = 0.2f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "${('A'.code + optIndex).toChar()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (isAnswerSubmitted && isCorrect) EmeraldSuccess else NavyPrimary
                  )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                  text = optionText,
                  fontSize = 14.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = Color(0xFF1E293B),
                  modifier = Modifier.weight(1f)
                )

                if (isAnswerSubmitted) {
                  if (isCorrect) {
                    Icon(Icons.Default.Check, contentDescription = "Benar", tint = EmeraldSuccess)
                  } else if (isSelected) {
                    Icon(Icons.Default.Close, contentDescription = "Kurang Tepat", tint = RoseDanger)
                  }
                }
              }
            }
          }

          // Feedback & Next Button
          if (isAnswerSubmitted) {
            val wasCorrect = selectedOptionIndex == currentQ.correctIndex
            Surface(
              color = if (wasCorrect) Color(0xFFECFDF5) else Color(0xFFFFFBEB),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = if (wasCorrect) "🎉 Jawaban Benar!" else "💡 Penjelasan Pedagogis:",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = if (wasCorrect) EmeraldSuccess else Color(0xFFB45309)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = currentQ.explanation,
                  fontSize = 12.sp,
                  color = Color(0xFF334155),
                  lineHeight = 18.sp
                )
              }
            }

            Button(
              onClick = {
                if (currentQuestionIndex < questions.size - 1) {
                  currentQuestionIndex++
                  selectedOptionIndex = null
                  isAnswerSubmitted = false
                } else {
                  isGameFinished = true
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_next_question")
            ) {
              Text(
                text = if (currentQuestionIndex < questions.size - 1) "Pertanyaan Selanjutnya ➡️" else "Lihat Hasil Skor 🏆",
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}
