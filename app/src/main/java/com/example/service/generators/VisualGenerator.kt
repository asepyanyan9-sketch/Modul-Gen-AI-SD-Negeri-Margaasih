package com.example.service.generators

import com.example.data.model.UserSettings

object VisualGenerator {
  fun generateVisualMedia(
    subject: String,
    grade: String,
    topic: String,
    visualType: String, // Poster, Infografis, Flashcard, Mind Map
    settings: UserSettings
  ): String {
    return """
# DESAIN MEDIA PEMBELAJARAN VISUAL
**${settings.schoolName.uppercase()} — KURIKULUM MERDEKA**

* **Mata Pelajaran:** $subject
* **Sasaran:** $grade (SD)
* **Topik:** $topic
* **Format Media:** $visualType
* **Penyusun:** ${settings.teacherName}

---

## 🎨 BLUEPRINT DESAIN VISUAL

### 1. Judul & Header Menarik
* **Judul Utama:** JELAJAH VISUAL: $topic
* **Sub-judul:** Belajar Mengasyikkan & Penuh Makna Bersama SD Negeri Margaasih
* **Nuansa Warna:** Biru Navy (#1E3A8A), Kuning Hangat (#F59E0B), dan Hijau Alami (#10B981)

---

### 2. KARTU FLASHCARD & KONTEN VISUAL INTI

#### 📇 Flashcard 1: Konsep Dasar
* **Sisi Depan (Pertanyaan/Kata Kunci):** APA ITU $topic?
* **Sisi Belakang (Makna & Penjelasan Singkat):** Merupakan konsep mendasar yang menjelaskan keteraturan dan manfaat fenomena di lingkungan kita.
* **Ikon / Simbol Visual Rekomendasi:** Buku Terbuka, Daun Hijau, Lampu Ide Berpijar.

#### 📇 Flashcard 2: Fakta Menarik
* **Sisi Depan (Pemicu Rasa Ingin Tahu):** TAHUKAH KAMU?
* **Sisi Belakang:** Di sekitar wilayah Margaasih dan Kabupaten Bandung, kita dapat mengamati dampak langsung dari $topic setiap hari!
* **Ikon / Simbol Visual Rekomendasi:** Kaca Pembesar, Peta Jawa Barat, Kompas Petualang.

#### 📇 Flashcard 3: Aksi Nyata Siswa
* **Sisi Depan:** APA YANG BISA KITA LAKUKAN?
* **Sisi Belakang:** Selalu menjaga sikap peduli, aktif bertanya di kelas, dan mengamalkan nilai Profil Pelajar Pancasila.
* **Ikon / Simbol Visual Rekomendasi:** Tangan Berjabat, Hati Peduli, Bintang Prestasi.

---

### 3. PETA KONSEP (MIND MAP HIERARKI)
* **Topik Sentral:** 🌟 **$topic**
  ├── 🔹 **Cabang 1: Pengertian & Karakteristik**
  │   ├── Ciri khas fisik / konseptual
  │   └── Hubungan dengan materi sebelumnya
  ├── 🔹 **Cabang 2: Manfaat & Relevansi**
  │   ├── Untuk diri sendiri (pengetahuan & karakter)
  │   └── Untuk lingkungan sekolah SD Negeri Margaasih
  └── 🔹 **Cabang 3: Penerapan Nyata (Project Action)**
      ├── Kegiatan penyelidikan kelompok
      └── Karya inovatif reflektif

---

### 4. PANDUAN PROMPT UNTUK GENERATOR GAMBAR (IMAGE GENERATION)
```text
Educational illustration poster for elementary school children about '$topic', colorful flat vector art, clean composition, cheerful Indonesian elementary students in red-and-white school uniform exploring nature and classroom, joyful atmosphere, high resolution, minimalist modern style, bright daylight lighting.
```
    """.trimIndent()
  }
}
