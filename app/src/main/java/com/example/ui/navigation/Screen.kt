package com.example.ui.navigation

sealed class Screen(val route: String, val title: String) {
  data object Dashboard : Screen("dashboard", "Dashboard")
  data object LearningDesigner : Screen("learning_designer", "Learning Designer")
  data object WorksheetStudio : Screen("worksheet_studio", "Worksheet Studio")
  data object AssessmentStudio : Screen("assessment_studio", "Assessment Studio")
  data object GameStudio : Screen("game_studio", "Game Studio")
  data object VisualStudio : Screen("visual_studio", "Visual Studio")
  data object PresentationStudio : Screen("presentation_studio", "Presentation Studio")
  data object MyLibrary : Screen("my_library", "My Library")
  data object Templates : Screen("templates", "Templates")
  data object Settings : Screen("settings", "Pengaturan")
  data object GamePlay : Screen("game_play", "Main Game")
}
