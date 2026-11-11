package com.example.service.generators

import com.example.data.model.UserSettings
import org.json.JSONArray
import org.json.JSONObject

data class GameQuestion(
  val id: Int,
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String
)

data class MatchingItem(
  val left: String,
  val right: String
)

data class GameData(
  val title: String,
  val gameType: String, // QUIZ, MATCHING, TRUE_FALSE, MATH_ADVENTURE, MEMORY_CARDS, WORD_PUZZLE
  val subject: String,
  val grade: String,
  val topic: String,
  val instructions: String,
  val questions: List<GameQuestion> = emptyList(),
  val matchingItems: List<MatchingItem> = emptyList()
)

object GameGenerator {
  fun generateGameData(
    subject: String,
    grade: String,
    topic: String,
    gameType: String,
    settings: UserSettings
  ): GameData {
    val cleanTopic = if (topic.isBlank()) "Materi Pembelajaran" else topic
    
    return when (gameType) {
      "MATCHING" -> {
        GameData(
          title = "Tantangan Menjodohkan: $cleanTopic",
          gameType = "MATCHING",
          subject = subject,
          grade = grade,
          topic = cleanTopic,
          instructions = "Pilihlah satu kartu konsep di kolom kiri, lalu pasangkan dengan kartu penjelasan yang sesuai di kolom kanan!",
          matchingItems = listOf(
            MatchingItem("Ciri Khas 1", "Karakteristik penting dari $cleanTopic yang mudah diamati"),
            MatchingItem("Fungsi Utama", "Peran penting $cleanTopic dalam menjaga keseimbangan alam/lingkungan"),
            MatchingItem("Contoh Nyata", "Bentuk penerapan nyata di lingkungan SD Negeri Margaasih"),
            MatchingItem("Sikap Pelajar", "Tindakan bijak dan peduli terhadap $cleanTopic")
          )
        )
      }
      "TRUE_FALSE" -> {
        GameData(
          title = "Benar atau Salah: $cleanTopic",
          gameType = "TRUE_FALSE",
          subject = subject,
          grade = grade,
          topic = cleanTopic,
          instructions = "Tentukan apakah pernyataan mengenai materi berikut BENAR atau SALAH secara cepat dan tepat!",
          questions = listOf(
            GameQuestion(
              id = 1,
              question = "Mempelajari $cleanTopic sangat bermanfaat bagi kehidupan sehari-hari siswa di SD Negeri Margaasih.",
              options = listOf("Benar", "Salah"),
              correctIndex = 0,
              explanation = "Tepat sekali! Materi $cleanTopic memiliki relevansi langsung dengan lingkungan siswa."
            ),
            GameQuestion(
              id = 2,
              question = "$cleanTopic hanya bisa dipahami oleh ilmuwan dan tidak ada di sekitar rumah kita.",
              options = listOf("Benar", "Salah"),
              correctIndex = 1,
              explanation = "Salah. $cleanTopic ada di sekitar kita dan mudah diamati dalam kehidupan nyata."
            ),
            GameQuestion(
              id = 3,
              question = "Menjaga dan memanfaatkan $cleanTopic secara bertanggung jawab adalah wujud Profil Pelajar Pancasila.",
              options = listOf("Benar", "Salah"),
              correctIndex = 0,
              explanation = "Benar! Ini mencerminkan akhlak kepada alam dan sikap mandiri serta bernalar kritis."
            )
          )
        )
      }
      else -> { // QUIZ CHALLENGE (Default & Most Popular)
        GameData(
          title = "Kuis Petualang: $cleanTopic",
          gameType = "QUIZ",
          subject = subject,
          grade = grade,
          topic = cleanTopic,
          instructions = "Jawablah setiap pertanyaan tantangan seputar $cleanTopic untuk mengumpulkan skor tertinggi!",
          questions = listOf(
            GameQuestion(
              id = 1,
              question = "Apa inti pembelajaran terpenting dari topik '$cleanTopic'?",
              options = listOf(
                "Memahami konsep dan menerapkannya dalam kehidupan sehari-hari",
                "Menghafal tanpa memahami maknanya",
                "Mencatat di buku tanpa dibaca kembali",
                "Menunggu jawaban dari teman kelompok"
              ),
              correctIndex = 0,
              explanation = "Hebat! Pembelajaran bermakna (Meaningful Learning) selalu mengutamakan pemahaman dan penerapan nyata."
            ),
            GameQuestion(
              id = 2,
              question = "Bagaimana contoh tindakan positif terkait '$cleanTopic' di lingkungan SD Negeri Margaasih?",
              options = listOf(
                "Menjaga kebersihan dan aktif berdiskusi dengan teman",
                "Tidak peduli pada lingkungan sekitar kelas",
                "Menyimpan materi hanya untuk ujian",
                "Meninggalkan tugas sebelum selesai"
              ),
              correctIndex = 0,
              explanation = "Bagus sekali! Sikap peduli dan gotong royong mencerminkan Profil Pelajar Pancasila."
            ),
            GameQuestion(
              id = 3,
              question = "Jika kamu menemui kesulitan saat mempelajari '$cleanTopic', langkah bijak apa yang kamu ambil?",
              options = listOf(
                "Bertanya pada guru dan berdiskusi ceria dengan teman",
                "Menyerah dan berhenti belajar",
                "Menyalahkan materi pelajaran",
                "Mengabaikan kesulitan tersebut"
              ),
              correctIndex = 0,
              explanation = "Keren! Sikap pantang menyerah dan komunikatif adalah kunci keberhasilan belajar."
            )
          )
        )
      }
    }
  }

  fun toJson(gameData: GameData): String {
    val json = JSONObject().apply {
      put("title", gameData.title)
      put("gameType", gameData.gameType)
      put("subject", gameData.subject)
      put("grade", gameData.grade)
      put("topic", gameData.topic)
      put("instructions", gameData.instructions)
      
      val questionsArr = JSONArray()
      gameData.questions.forEach { q ->
        val qObj = JSONObject().apply {
          put("id", q.id)
          put("question", q.question)
          put("options", JSONArray(q.options))
          put("correctIndex", q.correctIndex)
          put("explanation", q.explanation)
        }
        questionsArr.put(qObj)
      }
      put("questions", questionsArr)

      val matchingArr = JSONArray()
      gameData.matchingItems.forEach { m ->
        val mObj = JSONObject().apply {
          put("left", m.left)
          put("right", m.right)
        }
        matchingArr.put(mObj)
      }
      put("matchingItems", matchingArr)
    }
    return json.toString(2)
  }

  fun fromJson(jsonStr: String): GameData {
    return try {
      val json = JSONObject(jsonStr)
      val questions = mutableListOf<GameQuestion>()
      val qArr = json.optJSONArray("questions")
      if (qArr != null) {
        for (i in 0 until qArr.length()) {
          val qObj = qArr.getJSONObject(i)
          val optArr = qObj.getJSONArray("options")
          val opts = mutableListOf<String>()
          for (j in 0 until optArr.length()) {
            opts.add(optArr.getString(j))
          }
          questions.add(
            GameQuestion(
              id = qObj.getInt("id"),
              question = qObj.getString("question"),
              options = opts,
              correctIndex = qObj.getInt("correctIndex"),
              explanation = qObj.optString("explanation", "")
            )
          )
        }
      }

      val matchingItems = mutableListOf<MatchingItem>()
      val mArr = json.optJSONArray("matchingItems")
      if (mArr != null) {
        for (i in 0 until mArr.length()) {
          val mObj = mArr.getJSONObject(i)
          matchingItems.add(
            MatchingItem(
              left = mObj.getString("left"),
              right = mObj.getString("right")
            )
          )
        }
      }

      GameData(
        title = json.optString("title", "Game Pembelajaran"),
        gameType = json.optString("gameType", "QUIZ"),
        subject = json.optString("subject", "Pelajaran"),
        grade = json.optString("grade", "Kelas"),
        topic = json.optString("topic", "Materi"),
        instructions = json.optString("instructions", "Petunjuk permainan"),
        questions = questions,
        matchingItems = matchingItems
      )
    } catch (e: Exception) {
      GameData(
        title = "Game Edukasi",
        gameType = "QUIZ",
        subject = "IPAS",
        grade = "Kelas 4",
        topic = "Materi Pokok",
        instructions = "Silakan mulai bermain!",
        questions = emptyList()
      )
    }
  }
}
