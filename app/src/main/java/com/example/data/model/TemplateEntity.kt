package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "templates")
data class TemplateEntity(
  @PrimaryKey val id: String = UUID.randomUUID().toString(),
  val title: String,
  val description: String,
  val generatorType: String,
  val subject: String,
  val grade: String,
  val phase: String,
  val contentJson: String,
  val isBuiltIn: Boolean = true,
  val createdAt: Long = System.currentTimeMillis()
)

data class UserSettings(
  val teacherName: String = "Asep Yanyan, S.Pd.",
  val teacherEmail: String = "asep.yanyan9@guru.sd.belajar.id",
  val schoolName: String = "SD Negeri Margaasih",
  val schoolAddress: String = "Kec. Margaasih, Kab. Bandung",
  val nip: String = "19870512 201001 1 008",
  val principalName: String = "Hj. Ratnaningsih, M.M.Pd.",
  val principalNip: String = "19700315 199303 2 004",
  val semester: String = "Semester 1 (Ganjil)",
  val academicYear: String = "2024/2025",
  val customApiKey: String = ""
)
