package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.SettingsManager
import com.example.data.model.GeneratorTypes
import com.example.data.model.ProjectEntity
import com.example.data.model.TemplateEntity
import com.example.data.model.UserSettings
import com.example.service.AiEngineRepository
import com.example.service.DocumentExportHelper
import com.example.service.generators.AssessmentGenerator
import com.example.service.generators.GameData
import com.example.service.generators.GameGenerator
import com.example.service.generators.LearningDesignerGenerator
import com.example.service.generators.PresentationGenerator
import com.example.service.generators.VisualGenerator
import com.example.service.generators.WorksheetGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {
  private val db = AppDatabase.getDatabase(application)
  private val projectDao = db.projectDao()
  private val templateDao = db.templateDao()
  private val settingsManager = SettingsManager(application)
  private val aiEngine = AiEngineRepository()

  val settings: StateFlow<UserSettings> = settingsManager.settings

  val allProjects: StateFlow<List<ProjectEntity>> = projectDao.getAllProjects()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allTemplates: StateFlow<List<TemplateEntity>> = templateDao.getAllTemplates()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  private val _isGenerating = MutableStateFlow(false)
  val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

  private val _activeOutput = MutableStateFlow<String?>(null)
  val activeOutput: StateFlow<String?> = _activeOutput.asStateFlow()

  private val _activeGameData = MutableStateFlow<GameData?>(null)
  val activeGameData: StateFlow<GameData?> = _activeGameData.asStateFlow()

  private val _statusMessage = MutableStateFlow<String?>(null)
  val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

  init {
    seedInitialTemplatesIfNeeded()
  }

  fun clearActiveOutput() {
    _activeOutput.value = null
    _activeGameData.value = null
    _statusMessage.value = null
  }

  fun updateSettings(newSettings: UserSettings) {
    settingsManager.updateSettings(newSettings)
    _statusMessage.value = "Pengaturan berhasil diperbarui."
  }

  fun generateModulAjar(
    subject: String,
    grade: String,
    phase: String,
    topic: String,
    timeAllocation: String,
    model: String,
    deepPillar: String
  ) {
    viewModelScope.launch {
      _isGenerating.value = true
      _statusMessage.value = "Sedang merancang Modul Ajar Kurikulum Merdeka..."
      val curSettings = settings.value

      val prompt = "Buatkan Modul Ajar Kurikulum Merdeka lengkap untuk SD Negeri Margaasih. " +
          "Mata Pelajaran: $subject, Kelas: $grade ($phase), Topik: $topic, Model: $model, Alokasi Waktu: $timeAllocation, " +
          "Pendekatan Pembelajaran Mendalam: $deepPillar. Sertakan identitas sekolah, tujuan pembelajaran, pemahaman bermakna, " +
          "pertanyaan pemantik, langkah kegiatan (Awal, Inti, Penutup), asesmen, dan refleksi."

      val result = aiEngine.generateContent(
        prompt = prompt,
        systemInstruction = "Anda adalah Pakar Kurikulum Merdeka dan Pengembang Pembelajaran untuk Sekolah Dasar di Indonesia.",
        userSettings = curSettings,
        fallbackGenerator = {
          LearningDesignerGenerator.generateModulAjar(
            subject = subject,
            grade = grade,
            phase = phase,
            topic = topic,
            timeAllocation = timeAllocation,
            learningModel = model,
            deepLearningPillar = deepPillar,
            settings = curSettings
          )
        }
      )

      _activeOutput.value = result.content
      _statusMessage.value = result.message
      _isGenerating.value = false
    }
  }

  fun generateWorksheet(
    subject: String,
    grade: String,
    topic: String,
    worksheetType: String,
    questionCount: Int
  ) {
    viewModelScope.launch {
      _isGenerating.value = true
      _statusMessage.value = "Sedang menyusun Lembar Kerja Peserta Didik (LKPD)..."
      val curSettings = settings.value

      val prompt = "Buatkan Lembar Kerja Peserta Didik (LKPD) lengkap untuk $subject Kelas $grade di ${curSettings.schoolName}. " +
          "Topik: $topic, Tipe: $worksheetType, Jumlah Soal: $questionCount. Sertakan petunjuk, identitas siswa, soal pilihan ganda, " +
          "menjodohkan, isian singkat, dan kunci jawaban dengan rubrik penilaian."

      val result = aiEngine.generateContent(
        prompt = prompt,
        systemInstruction = "Anda adalah Pengembang LKPD Inovatif untuk Sekolah Dasar.",
        userSettings = curSettings,
        fallbackGenerator = {
          WorksheetGenerator.generateWorksheet(
            subject = subject,
            grade = grade,
            topic = topic,
            worksheetType = worksheetType,
            questionCount = questionCount,
            settings = curSettings
          )
        }
      )

      _activeOutput.value = result.content
      _statusMessage.value = result.message
      _isGenerating.value = false
    }
  }

  fun generateAssessment(
    subject: String,
    grade: String,
    topic: String,
    assessmentType: String
  ) {
    viewModelScope.launch {
      _isGenerating.value = true
      _statusMessage.value = "Sedang menyusun Asesmen dan Kisi-kisi Soal..."
      val curSettings = settings.value

      val prompt = "Buatkan instrumen asesmen lengkap ($assessmentType) untuk $subject Kelas $grade topik $topic. " +
          "Sertakan kisi-kisi soal dengan level kognitif Taksonomi Bloom (C1-C6), naskah soal, kunci jawaban, dan rubrik KKTP."

      val result = aiEngine.generateContent(
        prompt = prompt,
        systemInstruction = "Anda adalah Pakar Asesmen Pendidikan Sekolah Dasar Kurikulum Merdeka.",
        userSettings = curSettings,
        fallbackGenerator = {
          AssessmentGenerator.generateAssessment(
            subject = subject,
            grade = grade,
            topic = topic,
            assessmentType = assessmentType,
            settings = curSettings
          )
        }
      )

      _activeOutput.value = result.content
      _statusMessage.value = result.message
      _isGenerating.value = false
    }
  }

  fun generateGame(
    subject: String,
    grade: String,
    topic: String,
    gameType: String
  ) {
    viewModelScope.launch {
      _isGenerating.value = true
      _statusMessage.value = "Sedang merancang Game Edukasi Interaktif..."
      val curSettings = settings.value

      val gameData = GameGenerator.generateGameData(
        subject = subject,
        grade = grade,
        topic = topic,
        gameType = gameType,
        settings = curSettings
      )

      _activeGameData.value = gameData
      _activeOutput.value = GameGenerator.toJson(gameData)
      _statusMessage.value = "Game edukasi interaktif berhasil disiapkan! Ketuk 'Mainkan Game' untuk langsung bermain."
      _isGenerating.value = false
    }
  }

  fun setPlayableGame(gameData: GameData) {
    _activeGameData.value = gameData
  }

  fun generateVisual(
    subject: String,
    grade: String,
    topic: String,
    visualType: String
  ) {
    viewModelScope.launch {
      _isGenerating.value = true
      _statusMessage.value = "Sedang menyusun Media Pembelajaran Visual..."
      val curSettings = settings.value

      val prompt = "Buatkan rancangan media visual ($visualType) untuk mata pelajaran $subject kelas $grade topik $topic. " +
          "Sertakan blueprint desain, konten flashcard, mind map hierarkis, dan rekomendasi prompt gambar edukasi."

      val result = aiEngine.generateContent(
        prompt = prompt,
        systemInstruction = "Anda adalah Desainer Media Pembelajaran Visual Sekolah Dasar.",
        userSettings = curSettings,
        fallbackGenerator = {
          VisualGenerator.generateVisualMedia(
            subject = subject,
            grade = grade,
            topic = topic,
            visualType = visualType,
            settings = curSettings
          )
        }
      )

      _activeOutput.value = result.content
      _statusMessage.value = result.message
      _isGenerating.value = false
    }
  }

  fun generatePresentation(
    subject: String,
    grade: String,
    topic: String,
    slideCount: Int
  ) {
    viewModelScope.launch {
      _isGenerating.value = true
      _statusMessage.value = "Sedang menyusun Slide Deck Presentasi..."
      val curSettings = settings.value

      val prompt = "Buatkan outline dan isi slide presentasi pembelajaran ($slideCount slide) untuk $subject kelas $grade topik $topic. " +
          "Sertakan judul slide, poin kunci, saran visual, dan speaker notes (catatan guru) untuk setiap slide."

      val result = aiEngine.generateContent(
        prompt = prompt,
        systemInstruction = "Anda adalah Desainer Slide Presentasi Edukasi Kelas SD.",
        userSettings = curSettings,
        fallbackGenerator = {
          PresentationGenerator.generatePresentation(
            subject = subject,
            grade = grade,
            topic = topic,
            slideCount = slideCount,
            settings = curSettings
          )
        }
      )

      _activeOutput.value = result.content
      _statusMessage.value = result.message
      _isGenerating.value = false
    }
  }

  fun saveCurrentProject(
    title: String,
    generatorType: String,
    subject: String,
    grade: String,
    phase: String,
    topic: String,
    content: String
  ) {
    viewModelScope.launch {
      val project = ProjectEntity(
        id = UUID.randomUUID().toString(),
        title = if (title.isNotBlank()) title else "Proyek $topic",
        generatorType = generatorType,
        subject = subject,
        grade = grade,
        phase = phase,
        topic = topic,
        contentJson = content,
        status = "Selesai",
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis()
      )
      projectDao.insertProject(project)
      _statusMessage.value = "Proyek berhasil disimpan di My Library!"
    }
  }

  fun updateProject(project: ProjectEntity, newContent: String, newTitle: String) {
    viewModelScope.launch {
      val updated = project.copy(
        title = newTitle,
        contentJson = newContent,
        updatedAt = System.currentTimeMillis()
      )
      projectDao.updateProject(updated)
      _statusMessage.value = "Perubahan proyek berhasil disimpan."
    }
  }

  fun duplicateProject(project: ProjectEntity) {
    viewModelScope.launch {
      val duplicated = project.copy(
        id = UUID.randomUUID().toString(),
        title = "${project.title} (Salinan)",
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis()
      )
      projectDao.insertProject(duplicated)
      _statusMessage.value = "Proyek berhasil diduplikasi."
    }
  }

  fun deleteProject(projectId: String) {
    viewModelScope.launch {
      projectDao.deleteById(projectId)
      _statusMessage.value = "Proyek berhasil dihapus dari My Library."
    }
  }

  fun exportAllProjectsToJson(): String {
    return DocumentExportHelper.exportProjectsToJson(allProjects.value)
  }

  fun importProjectsFromJson(jsonStr: String): Pair<Boolean, String> {
    return try {
      val imported = DocumentExportHelper.importProjectsFromJson(jsonStr)
      if (imported.isEmpty()) {
        Pair(false, "Tidak ada data proyek valid yang ditemukan dalam file JSON.")
      } else {
        viewModelScope.launch {
          projectDao.insertAll(imported)
        }
        Pair(true, "Berhasil mengimpor ${imported.size} proyek ke My Library.")
      }
    } catch (e: Exception) {
      Pair(false, "Gagal mengimpor file JSON: ${e.localizedMessage}")
    }
  }

  private fun seedInitialTemplatesIfNeeded() {
    viewModelScope.launch {
      val curSettings = settings.value
      val sampleTemplates = listOf(
        TemplateEntity(
          title = "Modul Ajar IPAS Kelas 4 — Bagian Tumbuhan",
          description = "Template Modul Ajar Fase B dengan pendekatan Joyful Learning dan PBL.",
          generatorType = GeneratorTypes.LEARNING_DESIGNER,
          subject = "IPAS (Ilmu Pengetahuan Alam & Sosial)",
          grade = "Kelas 4",
          phase = "Fase B",
          contentJson = LearningDesignerGenerator.generateModulAjar(
            subject = "IPAS (Ilmu Pengetahuan Alam & Sosial)",
            grade = "Kelas 4",
            phase = "Fase B",
            topic = "Bagian Tubuh Tumbuhan dan Fungsinya",
            timeAllocation = "2 x 35 Menit",
            learningModel = "Problem-Based Learning (PBL)",
            deepLearningPillar = "Meaningful Learning (Kontekstual)",
            settings = curSettings
          )
        ),
        TemplateEntity(
          title = "LKPD Matematika Kelas 2 — Penjumlahan Bilangan",
          description = "Lembar Kerja Siswa Fase A dengan latihan soal bergambar ceria.",
          generatorType = GeneratorTypes.WORKSHEET_STUDIO,
          subject = "Matematika",
          grade = "Kelas 2",
          phase = "Fase A",
          contentJson = WorksheetGenerator.generateWorksheet(
            subject = "Matematika",
            grade = "Kelas 2",
            topic = "Penjumlahan Bilangan Cacah sampai 50",
            worksheetType = "LKPD Bergambar Ceria",
            questionCount = 5,
            settings = curSettings
          )
        ),
        TemplateEntity(
          title = "Asesmen Sumatif Bahasa Indonesia Kelas 5 — Teks Deskripsi",
          description = "Kisi-kisi dan naskah asesmen formatif C1-C6 Kurikulum Merdeka.",
          generatorType = GeneratorTypes.ASSESSMENT_STUDIO,
          subject = "Bahasa Indonesia",
          grade = "Kelas 5",
          phase = "Fase C",
          contentJson = AssessmentGenerator.generateAssessment(
            subject = "Bahasa Indonesia",
            grade = "Kelas 5",
            topic = "Menulis Teks Deskripsi Lingkungan Sekolah SD Negeri Margaasih",
            assessmentType = "Asesmen Sumatif Lingkup Materi",
            settings = curSettings
          )
        )
      )
      templateDao.insertAll(sampleTemplates)
    }
  }
}
