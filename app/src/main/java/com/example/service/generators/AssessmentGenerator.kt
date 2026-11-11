package com.example.service.generators

import com.example.data.model.UserSettings

object AssessmentGenerator {
  fun generateAssessment(
    subject: String,
    grade: String,
    topic: String,
    assessmentType: String, // Diagnostik, Formatif, Sumatif
    settings: UserSettings
  ): String {
    return """
# INSTRUMEN ASESMEN & KISI-KISI SOAL KURIKULUM MERDEKA
**${settings.schoolName.uppercase()} — KABUPATEN BANDUNG**

* **Mata Pelajaran:** $subject
* **Kelas / Semester:** $grade / ${settings.semester}
* **Tahun Ajaran:** ${settings.academicYear}
* **Jenis Asesmen:** $assessmentType
* **Materi Pokok:** $topic
* **Penyusun:** ${settings.teacherName} (NIP. ${settings.nip})

---

## I. KISI-KISI PENULISAN SOAL (TAKSONOMI BLOOM C1 - C6)

| No | Capaian Pembelajaran / Indikator Soal | Materi | Level Kognitif | Bentuk Soal | No. Soal |
|:--:|:---|:---|:---:|:---:|:---:|
| 1 | Siswa mampu mengingat kembali definisi dan komponen dasar materi | $topic | C1 (Mengingat) | Pilihan Ganda | 1 |
| 2 | Siswa mampu memahami hubungan sebab-akibat fenomena materi di lingkungan | $topic | C2 (Memahami) | Pilihan Ganda | 2 |
| 3 | Siswa mampu menerapkan pengetahuan konsep materi dalam situasi nyata | $topic | C3 (Menerapkan) | Pilihan Ganda | 3 |
| 4 | Siswa mampu menganalisis permasalahan atau studi kasus sederhana | $topic | C4 (Menganalisis) | Uraian | 4 |
| 5 | Siswa mampu mengevaluasi tindakan atau merancang solusi kreatif | $topic | C5/C6 (Mencipta) | Uraian Proyek | 5 |

---

## II. NASKAH SOAL ASESMEN

### Bagian A: Pilihan Ganda (Bobot Skor: 60)
**Soal 1 (C1 - Mengingat)**
Unsur atau komponen yang paling mendasar dari konsep **$topic** adalah...
A. Benda tiruan yang tidak memiliki fungsi nyata.
B. Komponen alamiah yang bekerja saling melengkapi dan teratur.
C. Bagian yang tidak berhubungan dengan ekosistem sekitar.
D. Sesuatu yang hanya ada pada zaman dahulu.

**Soal 2 (C2 - Memahami)**
Apabila salah satu bagian dari **$topic** mengalami gangguan atau kerusakan, maka yang akan terjadi pada lingkungan sekitar adalah...
A. Tidak ada perubahan sama sekali karena dapat pulih secara instan.
B. Keseimbangan sistem terganggu sehingga memengaruhi kehidupan makhluk lain.
C. Keadaan lingkungan menjadi jauh lebih baik dan terkendali.
D. Hanya berpengaruh pada orang dewasa saja.

**Soal 3 (C3 - Menerapkan)**
Seorang siswa kelas $grade di SD Negeri Margaasih ingin membantu menjaga kelestarian atau kebermanfaatan **$topic** di lingkungan sekolah. Tindakan paling tepat yang dapat dilakukannya adalah...
A. Menunggu disuruh oleh guru atau penjaga sekolah terlebih dahulu.
B. Mengabaikan jika melihat ada teman yang merusak fasilitas lingkungan.
C. Melakukan aksi nyata mulai dari diri sendiri seperti merawat dan menjaga kebersihan.
D. Mencatat kerusakan tanpa melakukan tindakan perbaikan apa pun.

### Bagian B: Soal Uraian Bernalar Kritis (Bobot Skor: 40)
**Soal 4 (C4 - Menganalisis)**
Amati lingkungan di sekitar tempat tinggalmu di Margaasih! Ceritakan satu permasalahan nyata yang berkaitan dengan **$topic**, lalu analisislah apa penyebab utama permasalahan tersebut timbul!

**Soal 5 (C5/C6 - Mengevaluasi & Mencipta Solusi)**
Sebagai peserta didik yang memiliki Profil Pelajar Pancasila, rancanglah satu ide kegiatan sederhana bersama teman sekelasmu untuk mengoptimalkan pemanfaatan atau pelestarian **$topic** di SD Negeri Margaasih!

---

## III. KUNCI JAWABAN & PEMBAHASAN PEDAGOGIS

* **Kunci Soal 1:** B (Komponen alamiah yang bekerja saling melengkapi dan teratur).
  * *Pembahasan:* Pada hakikatnya konsep $topic tersusun atas keteraturan sistem yang saling terikat.
* **Kunci Soal 2:** B (Keseimbangan sistem terganggu).
  * *Pembahasan:* Dalam Kurikulum Merdeka, pemahaman sistemik menekankan bahwa setiap komponen saling memengaruhi.
* **Kunci Soal 3:** C (Aksi nyata mandiri dan bertanggung jawab).
  * *Pembahasan:* Mencerminkan dimensi Mandiri dan Bergotong Royong pada Profil Pelajar Pancasila.

---

## IV. KRITERIA KETERCAPAIAN TUJUAN PEMBELAJARAN (KKTP)

* **Rentang 0 - 69%:** Perlu Bimbingan (Intervensi khusus oleh guru)
* **Rentang 70 - 79%:** Cukup (Mencapai ketuntasan dasar)
* **Rentang 80 - 89%:** Baik (Mencapai ketuntasan dengan pemahaman mandiri)
* **Rentang 90 - 100%:** Sangat Baik (Perlu pengayaan / tutor sebaya)
    """.trimIndent()
  }
}
