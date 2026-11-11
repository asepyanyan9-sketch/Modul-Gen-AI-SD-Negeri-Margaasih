package com.example.service.generators

import com.example.data.model.UserSettings

object PresentationGenerator {
  fun generatePresentation(
    subject: String,
    grade: String,
    topic: String,
    slideCount: Int,
    settings: UserSettings
  ): String {
    return """
# SLIDE DECK PRESENTASI PEMBELAJARAN
**${settings.schoolName.uppercase()} — KURIKULUM MERDEKA**

* **Mata Pelajaran:** $subject | **Kelas:** $grade
* **Topik Utama:** $topic
* **Pembuat:** ${settings.teacherName} (NIP. ${settings.nip})
* **Jumlah Slide:** ${if (slideCount > 0) slideCount else 5} Slide Interaktif

---

## 📽️ SLIDE 1: JUDUL & PEMBUKA
* **Judul Slide:** PETUALANGAN SERU: $topic
* **Subjudul:** Belajar Bermakna dan Menyenangkan Bersama Kelas $grade
* **Identitas:** ${settings.schoolName} • Tahun Ajaran ${settings.academicYear}
* **Konten Layar:** Ilustrasi siswa SD bersemangat dengan ikon buku dan alam terbuka.
* **Catatan Presenter (Speaker Notes Guru):**
  > "Buka kelas dengan senyuman dan sapaan ceria. Ajak siswa menyanyikan yel-yel kelas atau bernapas sejenak (Mindful Breathing) sebelum memulai."

---

## 📽️ SLIDE 2: PERTANYAAN PEMANTIK
* **Judul Slide:** APA YANG KAMU AMATI HARI INI?
* **Poin Kunci:**
  1. Pernahkah kalian memperhatikan $topic di sekitar lingkungan sekolah kita?
  2. Mengapa hal ini sangat menarik untuk kita pelajari bersama?
  3. Siapa yang siap menjadi detektif sains/pengetahuan hari ini?
* **Catatan Presenter (Speaker Notes Guru):**
  > "Berikan waktu 1-2 menit kepada murid untuk saling berbisik dengan teman sebangkunya, lalu tunjuk 2 siswa untuk membagikan pendapatnya."

---

## 📽️ SLIDE 3: KONSEP INTI PEMBELAJARAN
* **Judul Slide:** MENGENAL LEBIH DEKAT $topic
* **Poin Kunci:**
  1. **Pengertian:** Konsep fundamental yang membentuk keteraturan materi.
  2. **Ciri-Ciri Utama:** Dapat diamati, memiliki karakteristik khusus, dan bermanfaat.
  3. **Relevansi Nyata:** Menjadi bagian tak terpisahkan dari ekosistem kita di Margaasih.
* **Catatan Presenter (Speaker Notes Guru):**
  > "Gunakan media konkret atau benda nyata yang ada di atas meja untuk memperagakan konsep ini secara visual agar murid lebih mudah memahami."

---

## 📽️ SLIDE 4: MISI TANTANGAN KELOMPOK
* **Judul Slide:** MISI INVESTIGASI BERSAMA!
* **Poin Kunci:**
  1. Berkumpul bersama teman kelompokmu dengan kompak.
  2. Buka Lembar Kerja Peserta Didik (LKPD) yang telah dibagikan.
  3. Diskusikan dan temukan jawaban terbaik secara bergotong royong.
* **Catatan Presenter (Speaker Notes Guru):**
  > "Kelilingi setiap meja kelompok untuk memberikan fasilitasi dan bimbingan diferensiasi bagi kelompok yang membutuhkan dukungan tambahan."

---

## 📽️ SLIDE 5: KESIMPULAN & REFLEKSI CERIA
* **Judul Slide:** APA YANG KITA PELAJARI HARI INI?
* **Poin Kunci:**
  1. Kita telah berhasil memahami $topic dengan luar biasa!
  2. Bersyukur atas ilmu baru dan ciptaan Tuhan Yang Maha Esa.
  3. Siap menerapkan kebaikan ini di rumah dan di sekolah.
* **Catatan Presenter (Speaker Notes Guru):**
  > "Tanyakan kepada murid: 'Emotikon apa yang menggambarkan perasaan kalian setelah belajar hari ini?'. Berikan tepuk tangan meriah untuk seluruh kelas."
    """.trimIndent()
  }
}
