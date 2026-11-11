package com.example.service.generators

import com.example.data.model.UserSettings

object LearningDesignerGenerator {
  fun generateModulAjar(
    subject: String,
    grade: String,
    phase: String,
    topic: String,
    timeAllocation: String,
    learningModel: String,
    deepLearningPillar: String,
    settings: UserSettings
  ): String {
    return """
# MODUL AJAR KURIKULUM MERDEKA
**SD NEGERI MARGAASIH — KABUPATEN BANDUNG**

---

## I. INFORMASI UMUM
* **Nama Penyusun:** ${settings.teacherName}
* **Satuan Pendidikan:** ${settings.schoolName}
* **Tahun Ajaran:** ${settings.academicYear}
* **Semester:** ${settings.semester}
* **Jenjang Sekolah:** Sekolah Dasar (SD)
* **Fase / Kelas:** $phase / $grade
* **Mata Pelajaran:** $subject
* **Topik / Bab:** $topic
* **Alokasi Waktu:** ${if (timeAllocation.isNotBlank()) timeAllocation else "2 x 35 Menit (1 Pertemuan)"}
* **Model Pembelajaran:** $learningModel
* **Pendekatan Pedagogis:** Pembelajaran Mendalam (Deep Learning) — $deepLearningPillar

---

## II. KOMPONEN INTI

### A. Capaian Pembelajaran (CP)
Peserta didik mampu memahami dan menganalisis konsep dasar tentang **$topic**, mengaitkannya dengan fenomena nyata di lingkungan sekitar sekolah dan tempat tinggal di Margaasih, serta menerapkan pemikiran kritis dalam menyelesaikan permasalahan sehari-hari.

### B. Tujuan Pembelajaran (TP) & Alur (ATP)
1. Melalui pengamatan dan eksplorasi lingkungan, peserta didik mampu mengidentifikasi karakteristik utama **$topic** secara tepat.
2. Melalui diskusi kelompok terbimbing, peserta didik dapat mengomunikasikan pemahamannya mengenai **$topic** dengan percaya diri.
3. Melalui penugasan reflektif (*Joyful Learning*), peserta didik mampu merumuskan solusi atau karya kreatif terkait materi.

### C. Pemahaman Bermakna (Meaningful Learning)
Konsep **$topic** bukan sekadar hafalan teori di kelas, melainkan bagian penting dari kehidupan nyata siswa di lingkungan SD Negeri Margaasih. Memahami materi ini membantu peserta didik menjadi pribadi yang mandiri, peduli lingkungan, dan mampu bernalar kritis.

### D. Pertanyaan Pemantik (Mindful Curiosity)
1. "Pernahkah anak-anak mengamati hal menarik seputar $topic di sekitar rumah atau perjalanan ke sekolah?"
2. "Menurut kalian, mengapa memahami $topic sangat bermanfaat bagi kehidupan kita sehari-hari?"
3. "Bagaimana jika kita tidak mempelajari $topic ini, apa dampaknya?"

### E. Profil Pelajar Pancasila
* **Bernalar Kritis:** Mengolah informasi dan menganalisis pertanyaan pemantik secara mendalam.
* **Bergotong Royong:** Berkolaborasi aktif dalam diskusi kelompok dan kegiatan eksplorasi.
* **Mandiri:** Bertanggung jawab atas tugas individu dan refleksi diri.
* **Kreatif:** Menghasilkan ide dan karya solusi inovatif.

### F. Sarana dan Prasarana
* Media Konkret / Benda Nyata seputar lingkungan SD Negeri Margaasih.
* Papan tulis interaktif, proyektor, lembar kerja peserta didik (LKPD).
* Buku Panduan Guru dan Buku Siswa Kurikulum Merdeka Kemendikbudristek.

---

## III. KEGIATAN PEMBELAJARAN

### 1. Kegiatan Awal (10 Menit) — *Mindful & Welcoming Environment*
* Guru membuka pelajaran dengan salam hangat, sapaan ramah, dan berdoa bersama.
* Guru memeriksa kehadiran siswa dan menanyakan kabar perasaan (*Social-Emotional Check-in*).
* Apersepsi ceria: Guru mengaitkan materi sebelumnya dengan topik **$topic**.
* Guru menyampaikan tujuan pembelajaran, kegiatan yang akan dilakukan, dan kesepakatan kelas yang menyenangkan.

### 2. Kegiatan Inti (50 Menit) — *Meaningful & Joyful Learning*
* **Fase 1: Orientasi Siswa pada Masalah / Stimulasi Visual**
  Siswa mengamati tayangan visual atau benda nyata mengenai $topic yang dipandu oleh guru.
* **Fase 2: Pengorganisasian Belajar & Diferensiasi**
  Siswa dibagi menjadi kelompok kecil heterogen (3-4 siswa) dengan pembagian peran yang adil. Guru memberikan diferensiasi proses sesuai kesiapan belajar murid.
* **Fase 3: Eksplorasi Terbimbing & Penyelidikan Bersama**
  Setiap kelompok mendiskusikan LKPD, memverifikasi hipotesis, dan saling bertukar gagasan mengenai $topic.
* **Fase 4: Presentasi & Unjuk Karya Ceria**
  Perwakilan kelompok mempresentasikan hasil diskusi secara kreatif (bisa menggunakan mind map, presentasi lisan, atau demonstrasi). Kelompok lain memberikan apresiasi tepuk tangan.
* **Fase 5: Penguatan Konsep & Refleksi Mendalam**
  Guru meluruskan miskonsepsi dan memberikan konfirmasi positif terhadap jawaban siswa.

### 3. Kegiatan Penutup (10 Menit) — *Reflective Closure*
* Peserta didik bersama guru menyimpulkan inti pembelajaran tentang **$topic**.
* Refleksi Murid: "Bagian kegiatan mana yang paling menyenangkan hari ini?"
* Guru memberikan umpan balik positif dan apresiasi atas kerja sama seluruh siswa.
* Doa penutup dan salam perpisahan.

---

## IV. ASESMEN & PENILAIAN
1. **Asesmen Diagnostik:** Pengamatan kesiapan belajar siswa melalui pertanyaan pemantik di awal.
2. **Asesmen Formatif:** Observasi keaktifan diskusi kelompok, lembar kerja siswa (LKPD), dan unjuk kerja presentasi.
3. **Asesmen Sumatif:** Tes tertulis singkat pemahaman konsep $topic di akhir pembelajaran.

---

## V. PENGESAHAN DOKUMEN

Mengetahui,
**Kepala SD Negeri Margaasih**

ttd.

**${settings.principalName}**
NIP. ${settings.principalNip}

Margaasih, ${java.text.SimpleDateFormat("dd MMMM yyyy", java.util.Locale("id", "ID")).format(java.util.Date())}
**Guru Kelas / Mata Pelajaran**

ttd.

**${settings.teacherName}**
NIP. ${settings.nip}
    """.trimIndent()
  }
}
