package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.model.GeneratorTypes
import com.example.data.model.ProjectEntity
import com.example.data.model.UserSettings
import com.example.service.generators.LearningDesignerGenerator
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read app name from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("EDU GEN AI", appName)
  }

  @Test
  fun `verify modul ajar generator includes school and teacher identity`() {
    val settings = UserSettings(
      teacherName = "Asep Yanyan, S.Pd.",
      schoolName = "SD Negeri Margaasih"
    )
    val result = LearningDesignerGenerator.generateModulAjar(
      subject = "IPAS",
      grade = "Kelas 4",
      phase = "Fase B",
      topic = "Tumbuhan Sumber Kehidupan",
      timeAllocation = "2 x 35 Menit",
      learningModel = "PBL",
      deepLearningPillar = "Meaningful Learning",
      settings = settings
    )

    assertTrue(result.contains("SD NEGERI MARGAASIH"))
    assertTrue(result.contains("Asep Yanyan, S.Pd."))
    assertTrue(result.contains("Tumbuhan Sumber Kehidupan"))
  }

  @Test
  fun `verify room database project insertion`() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = AppDatabase.getDatabase(context)
    val projectDao = db.projectDao()

    val project = ProjectEntity(
      id = "test_123",
      title = "LKPD Kelas 4",
      generatorType = GeneratorTypes.WORKSHEET_STUDIO,
      subject = "Matematika",
      grade = "Kelas 4",
      phase = "Fase B",
      topic = "Pecahan",
      contentJson = "Konten LKPD"
    )

    projectDao.insertProject(project)
    val retrieved = projectDao.getProjectById("test_123")
    assertNotNull(retrieved)
    assertEquals("LKPD Kelas 4", retrieved?.title)
  }
}
