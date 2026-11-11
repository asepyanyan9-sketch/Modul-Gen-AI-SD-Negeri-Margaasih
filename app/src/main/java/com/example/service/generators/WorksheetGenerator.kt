package com.example.service.generators

import com.example.data.model.UserSettings

object WorksheetGenerator {
  fun generateWorksheet(
    subject: String,
    grade: String,
    topic: String,
    worksheetType: String,
    questionCount: Int,
    settings: UserSettings
  ): String {
    return """
# LEMBAR KERJA PESERTA DIDIK (LKPD)
**${settings.schoolName.uppercase()} — TAHUN AJARAN ${settings.academicYear}**

* **Mata Pelajaran:** $subject
* **Kelas / Semester:** $grade / ${settings.semester}
* **Topik Pembelajaran:** $topic
* **Tipe LKPD:** $worksheetType
* **Guru Pembimbing:** ${settings.teacherName}

---

### IDENTITAS PESERTA DIDIK
* **Nama Lengkap:** ___________________________
* **Nomor Absen:** ___________
* **Kelompok / Meja:** ___________
* **Tanggal Pengerjaan:** ___________________________

---

### PETUNJUK PENGERJAAN
1. Berdoalah sebelum memulai mengerjakan lembar kerja ini.
2. Bacalah setiap instruksi dan pertanyaan dengan cermat dan teliti.
3. Kerjakan secara mandiri atau diskusikan bersama teman kelompokmu dengan gembira dan tertib.
4. Tanyakan kepada guru jika ada instruksi yang belum kamu pahami.
5. Periksa kembali seluruh jawabanmu sebelum diserahkan kepada guru.

---

### BAGIAN I: PILIHAN GANDA
*Pilihlah salah satu jawaban (A, B, C, atau D) yang paling tepat dengan memberi tanda silang (X)!*

**1.** Berdasarkan materi tentang **$topic**, pernyataan manakah di bawah ini yang paling benar?
   A. $topic hanya dapat ditemukan di laboratorium tertutup.
   B. $topic memiliki peran penting dalam kehidupan sehari-hari siswa di lingkungan sekitar.
   C. $topic tidak berpengaruh terhadap lingkungan sekitar kita.
   D. $topic hanya perlu dipelajari oleh orang dewasa.

**2.** Di lingkungan sekolah kita di SD Negeri Margaasih, contoh nyata yang berhubungan dengan **$topic** adalah...
   A. Kegiatan kebersihan kelas dan pemanfaatan sumber daya alam sekitar.
   B. Menutup mata dari peristiwa alam yang terjadi di sekitar.
   C. Membuang sampah tanpa memperhatikan jenis dan pemilahannya.
   D. Mengabaikan kerja sama antarteman dalam belajar kelompok.

**3.** Apa manfaat utama kita mempelajari konsep **$topic** dengan sungguh-sungguh?
   A. Supaya mendapat pujian dari orang lain saja.
   B. Menumbuhkan rasa syukur, kepedulian lingkungan, dan nalar kritis.
   C. Agar tidak perlu mengikuti kegiatan praktik lagi.
   D. Mempercepat waktu pulang sekolah.

---

### BAGIAN II: MENJODOHKAN (MATCHING)
*Tariklah garis hubung antara Konsep di Kolom Kiri dengan Penjelasan yang sesuai di Kolom Kanan!*

| Konsep Kunci | Pasangan Hubung | Penjelasan Tepat |
| :--- | :---: | :--- |
| 1. Karakteristik Utama | [ ...... ] | A. Sikap peduli dan bertanggung jawab atas kelestarian materi ini |
| 2. Peran Bagi Manusia | [ ...... ] | B. Ciri khas penting yang membedakan $topic dengan hal lainnya |
| 3. Tindakan Pelestarian | [ ...... ] | C. Manfaat langsung yang dirasakan oleh kita sehari-hari |

---

### BAGIAN III: AKTIVITAS EKSPLORASI & ISIAN SINGKAT
**4.** Tuliskan 2 (dua) contoh nyata penerapan atau fenomena **$topic** yang pernah kamu jumpai di sekitar rumah atau sekolahmu!
   * Jawaban 1: __________________________________________________________________
   * Jawaban 2: __________________________________________________________________

**5.** Jelaskan dengan kalimatmu sendiri, mengapa kita harus bersikap bijak dan peduli terhadap hal-hal yang berkaitan dengan **$topic**!
   * Jawaban:
   _____________________________________________________________________________
   _____________________________________________________________________________
   _____________________________________________________________________________

---

### RUBRIK PENILAIAN & KUNCI JAWABAN (UNTUK GURU)

* **Kunci Bagian I:** 1. B | 2. A | 3. B
* **Kunci Bagian II:** 1 - B | 2 - C | 3 - A
* **Kriteria Penilaian Uraian (Bagian III):**
  * Skor 4 (Sangat Baik): Penjelasan tepat, runtut, menghubungkan materi dengan contoh nyata.
  * Skor 3 (Baik): Penjelasan tepat namun contoh masih bersifat umum.
  * Skor 2 (Cukup): Penjelasan belum tuntas atau contoh kurang relevan.
  * Skor 1 (Perlu Bimbingan): Belum mampu menjelaskan konsep.

* **Nilai Akhir:** (Total Skor Diperoleh / Total Skor Maksimal) x 100
    """.trimIndent()
  }
}
