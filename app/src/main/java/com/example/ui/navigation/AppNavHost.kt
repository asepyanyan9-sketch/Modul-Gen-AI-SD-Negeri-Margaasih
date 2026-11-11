package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.service.generators.GameGenerator
import com.example.ui.components.AppDrawerContent
import com.example.ui.components.AppTopBar
import com.example.ui.screens.AssessmentStudioScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.GamePlayScreen
import com.example.ui.screens.GameStudioScreen
import com.example.ui.screens.LearningDesignerScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.PresentationStudioScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.TemplatesScreen
import com.example.ui.screens.VisualStudioScreen
import com.example.ui.screens.WorksheetStudioScreen
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
  viewModel: MainViewModel
) {
  val userSettings by viewModel.settings.collectAsState()
  val projects by viewModel.allProjects.collectAsState()
  val templates by viewModel.allTemplates.collectAsState()
  val isGenerating by viewModel.isGenerating.collectAsState()
  val activeOutput by viewModel.activeOutput.collectAsState()
  val activeGameData by viewModel.activeGameData.collectAsState()
  val statusMessage by viewModel.statusMessage.collectAsState()

  var currentScreen by remember { mutableStateOf<Screen>(Screen.Dashboard) }
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  // BackHandler to return to Dashboard from any sub-screen
  if (currentScreen != Screen.Dashboard) {
    BackHandler {
      if (currentScreen == Screen.GamePlay) {
        currentScreen = Screen.GameStudio
      } else {
        currentScreen = Screen.Dashboard
      }
    }
  }

  if (currentScreen == Screen.GamePlay && activeGameData != null) {
    GamePlayScreen(
      gameData = activeGameData!!,
      onBack = { currentScreen = Screen.GameStudio }
    )
    return
  }

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      AppDrawerContent(
        currentRoute = currentScreen.route,
        userSettings = userSettings,
        onNavigate = { targetScreen ->
          viewModel.clearActiveOutput()
          currentScreen = targetScreen
        },
        onCloseDrawer = {
          scope.launch { drawerState.close() }
        }
      )
    }
  ) {
    Scaffold(
      topBar = {
        AppTopBar(
          title = currentScreen.title,
          onOpenDrawer = {
            scope.launch { drawerState.open() }
          },
          onOpenLibrary = {
            currentScreen = Screen.MyLibrary
          }
        )
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        when (currentScreen) {
          Screen.Dashboard -> {
            DashboardScreen(
              userSettings = userSettings,
              projects = projects,
              onNavigate = { targetScreen ->
                viewModel.clearActiveOutput()
                currentScreen = targetScreen
              },
              onOpenProject = { project ->
                currentScreen = Screen.MyLibrary
              }
            )
          }

          Screen.LearningDesigner -> {
            LearningDesignerScreen(
              userSettings = userSettings,
              isGenerating = isGenerating,
              outputContent = activeOutput,
              statusMessage = statusMessage,
              onGenerate = { subject, grade, phase, topic, time, model, deepPillar ->
                viewModel.generateModulAjar(subject, grade, phase, topic, time, model, deepPillar)
              },
              onSave = { title, type, subject, grade, phase, topic, content ->
                viewModel.saveCurrentProject(title, type, subject, grade, phase, topic, content)
              }
            )
          }

          Screen.WorksheetStudio -> {
            WorksheetStudioScreen(
              userSettings = userSettings,
              isGenerating = isGenerating,
              outputContent = activeOutput,
              statusMessage = statusMessage,
              onGenerate = { subject, grade, topic, worksheetType, count ->
                viewModel.generateWorksheet(subject, grade, topic, worksheetType, count)
              },
              onSave = { title, type, subject, grade, phase, topic, content ->
                viewModel.saveCurrentProject(title, type, subject, grade, phase, topic, content)
              }
            )
          }

          Screen.AssessmentStudio -> {
            AssessmentStudioScreen(
              userSettings = userSettings,
              isGenerating = isGenerating,
              outputContent = activeOutput,
              statusMessage = statusMessage,
              onGenerate = { subject, grade, topic, assessmentType ->
                viewModel.generateAssessment(subject, grade, topic, assessmentType)
              },
              onSave = { title, type, subject, grade, phase, topic, content ->
                viewModel.saveCurrentProject(title, type, subject, grade, phase, topic, content)
              }
            )
          }

          Screen.GameStudio -> {
            GameStudioScreen(
              userSettings = userSettings,
              isGenerating = isGenerating,
              outputContent = activeOutput,
              activeGameData = activeGameData,
              statusMessage = statusMessage,
              onGenerate = { subject, grade, topic, gameType ->
                viewModel.generateGame(subject, grade, topic, gameType)
              },
              onPlayGame = {
                currentScreen = Screen.GamePlay
              },
              onSave = { title, type, subject, grade, phase, topic, content ->
                viewModel.saveCurrentProject(title, type, subject, grade, phase, topic, content)
              }
            )
          }

          Screen.VisualStudio -> {
            VisualStudioScreen(
              userSettings = userSettings,
              isGenerating = isGenerating,
              outputContent = activeOutput,
              statusMessage = statusMessage,
              onGenerate = { subject, grade, topic, visualType ->
                viewModel.generateVisual(subject, grade, topic, visualType)
              },
              onSave = { title, type, subject, grade, phase, topic, content ->
                viewModel.saveCurrentProject(title, type, subject, grade, phase, topic, content)
              }
            )
          }

          Screen.PresentationStudio -> {
            PresentationStudioScreen(
              userSettings = userSettings,
              isGenerating = isGenerating,
              outputContent = activeOutput,
              statusMessage = statusMessage,
              onGenerate = { subject, grade, topic, slideCount ->
                viewModel.generatePresentation(subject, grade, topic, slideCount)
              },
              onSave = { title, type, subject, grade, phase, topic, content ->
                viewModel.saveCurrentProject(title, type, subject, grade, phase, topic, content)
              }
            )
          }

          Screen.MyLibrary -> {
            LibraryScreen(
              projects = projects,
              userSettings = userSettings,
              onOpenProject = { project ->
                // Handled in LibraryScreen
              },
              onUpdateProject = { project, newContent, newTitle ->
                viewModel.updateProject(project, newContent, newTitle)
              },
              onDuplicateProject = { project ->
                viewModel.duplicateProject(project)
              },
              onDeleteProject = { projectId ->
                viewModel.deleteProject(projectId)
              },
              onExportJson = {
                viewModel.exportAllProjectsToJson()
              },
              onImportJson = { jsonStr ->
                viewModel.importProjectsFromJson(jsonStr)
              },
              onPlayGame = { gameData ->
                viewModel.setPlayableGame(gameData)
                currentScreen = Screen.GamePlay
              }
            )
          }

          Screen.Templates -> {
            TemplatesScreen(
              templates = templates,
              userSettings = userSettings,
              onUseTemplateAsProject = { template ->
                viewModel.saveCurrentProject(
                  title = template.title,
                  generatorType = template.generatorType,
                  subject = template.subject,
                  grade = template.grade,
                  phase = template.phase,
                  topic = template.title,
                  content = template.contentJson
                )
              }
            )
          }

          Screen.Settings -> {
            SettingsScreen(
              currentSettings = userSettings,
              onSaveSettings = { updated ->
                viewModel.updateSettings(updated)
              }
            )
          }

          Screen.GamePlay -> {
            // Handled above
          }
        }
      }
    }
  }
}
