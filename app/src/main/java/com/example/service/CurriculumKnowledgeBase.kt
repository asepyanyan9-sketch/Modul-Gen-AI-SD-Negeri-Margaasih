package com.example.service

object CurriculumKnowledgeBase {
  val SUBJECTS = listOf(
    "IPAS (Ilmu Pengetahuan Alam & Sosial)",
    "Matematika",
    "Bahasa Indonesia",
    "Pendidikan Pancasila",
    "Seni Budaya",
    "PJOK",
    "Bahasa Sunda (Muatan Lokal)",
    "Bahasa Inggris",
    "Pendidikan Agama Islam & Budi Pekerti"
  )

  val GRADES = listOf("Kelas 1", "Kelas 2", "Kelas 3", "Kelas 4", "Kelas 5", "Kelas 6")

  val PHASES = listOf(
    "Fase A (Kelas 1 - 2)",
    "Fase B (Kelas 3 - 4)",
    "Fase C (Kelas 5 - 6)"
  )

  fun getPhaseForGrade(grade: String): String = when {
    grade.contains("1") || grade.contains("2") -> "Fase A"
    grade.contains("3") || grade.contains("4") -> "Fase B"
    grade.contains("5") || grade.contains("6") -> "Fase C"
    else -> "Fase B"
  }

  val MODELS = listOf(
    "Problem-Based Learning (PBL)",
    "Project-Based Learning (PjBL)",
    "Discovery Learning",
    "Inquiry Learning",
    "Kooperatif (Jigsaw/TGT)",
    "Diferensiasi Konten, Proses & Produk"
  )

  val DEEP_LEARNING_PILLARS = listOf(
    "Mindful Learning (Fokus, Reflektif, Kesadaran Penuh)",
    "Meaningful Learning (Kontekstual, Aplikatif Lingkungan Sekitar)",
    "Joyful Learning (Interaktif, Menyenangkan, Berbasis Tantangan)"
  )

  val PANCASILA_DIMENSIONS = listOf(
    "Beriman, Bertakwa kepada Tuhan YME, dan Berakhlak Mulia",
    "Berkebinekaan Global",
    "Bergotong Royong",
    "Mandiri",
    "Bernalar Kritis",
    "Kreatif"
  )

  val SAMPLE_TOPICS_BY_SUBJECT = mapOf(
    "IPAS (Ilmu Pengetahuan Alam & Sosial)" to listOf(
      "Bagian Tubuh Tumbuhan dan Fungsinya",
      "Wujud Zat dan Perubahannya",
      "Gaya dan Gerak di Sekitar Kita",
      "Ekosistem dan Rantai Makanan",
      "Siklus Air dan Kelestarian Alam",
      "Peta dan Kenampakan Alam Daerahku",
      "Sumber Energi Alternatif Ramah Lingkungan",
      "Kearifan Lokal dan Budaya Masyarakat Sunda"
    ),
    "Matematika" to listOf(
      "Operasi Penjumlahan dan Pengurangan Bilangan Cacah",
      "Perkalian dan Pembagian Konseptual",
      "Pecahan Senilai dan Operasi Hitung",
      "Pengukuran Panjang, Berat, dan Waktu",
      "Geometri Bangun Datar dan Keliling",
      "Luas dan Volume Bangun Ruang",
      "Penyajian Data dalam Diagram Gambar dan Batang"
    ),
    "Bahasa Indonesia" to listOf(
      "Menyimak dan Menemukan Ide Pokok Teks Narasi",
      "Menulis Teks Deskripsi Lingkungan Sekolah SD Negeri Margaasih",
      "Membuat Teks Petunjuk dan Prosedur Sederhana",
      "Membaca Puisi dengan Intonasi dan Penghayatan",
      "Kosakata Baru dan Kalimat Efektif",
      "Wawancara Sederhana dengan Warga Sekolah"
    ),
    "Pendidikan Pancasila" to listOf(
      "Makna dan Penerapan Sila-Sila Pancasila di Sekolah",
      "Aturan dan Norma dalam Kehidupan Bermasyarakat",
      "Menghargai Keberagaman Suku, Agama, dan Budaya",
      "Hak dan Kewajiban sebagai Siswa dan Anak",
      "Musyawarah untuk Mufakat di Kelas"
    )
  )
}
