package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "projects")
data class ProjectEntity(
  @PrimaryKey val id: String = UUID.randomUUID().toString(),
  val title: String,
  val generatorType: String, // LEARNING_DESIGNER, WORKSHEET_STUDIO, ASSESSMENT_STUDIO, GAME_STUDIO, VISUAL_STUDIO, PRESENTATION_STUDIO
  val subject: String,
  val grade: String,
  val phase: String,
  val topic: String,
  val contentJson: String,
  val status: String = "Selesai",
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis()
)

object GeneratorTypes {
  const val LEARNING_DESIGNER = "learning_designer"
  const val WORKSHEET_STUDIO = "worksheet_studio"
  const val ASSESSMENT_STUDIO = "assessment_studio"
  const val GAME_STUDIO = "game_studio"
  const val VISUAL_STUDIO = "visual_studio"
  const val PRESENTATION_STUDIO = "presentation_studio"

  fun getDisplayName(type: String): String = when (type) {
    LEARNING_DESIGNER -> "Learning Designer"
    WORKSHEET_STUDIO -> "Worksheet Studio"
    ASSESSMENT_STUDIO -> "Assessment Studio"
    GAME_STUDIO -> "Game Studio"
    VISUAL_STUDIO -> "Visual Studio"
    PRESENTATION_STUDIO -> "Presentation Studio"
    else -> "Proyek Pembelajaran"
  }
}
